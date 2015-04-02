package com.winbits.bits

import com.winbits.bits.exception.BitsAccountNotFoundException
import com.winbits.bits.exception.InvalidAccountException
import com.winbits.exceptions.api.client.EntityValidationException
import grails.util.GrailsUtil
import groovy.sql.Sql
import org.joda.time.DateTime
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional


class AccountService {
  private static final String DATE_FORMAT = 'yyyy-MM.dd HH:mm:ss'
  static String EXPIRED_OFFSET = "expired_offset"
  def dataSource
  def configurationService
  def publishMessageService
  def finalBags = [BagEnum.SALES.name, BagEnum.CANCELED.name, BagEnum.CASHBACK_REFUNDED.name]

//  @Transactional(propagation = Propagation.REQUIRES_NEW)
  def createAccount(AccountType type, AccountStatus status) {

    Account account = new Account(
        balance: 0,
        status: status,
        type: type
    )

    if (account.save()) {
      account.number = account.id.toString().padLeft(10, "0")
      account.save()
      return [id: account.id, balance: account.balance]
    }

    return null;
  }

  @Transactional(readOnly = true)
  BigDecimal getBalance(Long id) {
    def account = Account.read(id)

    if(!account) {
      throw new BitsAccountNotFoundException(id)
    }

    isValidAccount(account)

    return account.balance
  }

  DateTime getValidity(OperationBitCommand command) {
    DateTime expirationDate
    //TODO: quitar negativos
    switch (command.validity) {
      case AccountValidity.ABSOLUTE:
        expirationDate = command.activationDate.plusDays(command.duration).withTimeAtStartOfDay()
        break
      case AccountValidity.RELATIVE:
        expirationDate = command.activationDate.plusMonths(command.duration).dayOfMonth().withMaximumValue().withTimeAtStartOfDay()
        break
      default:
        null
    }
    expirationDate
  }

  @Transactional(isolation = Isolation.REPEATABLE_READ)
  def addBits(OperationBitCommand command) {
    if (isValidAccount(command) || !command.concept || command.amount < 0) {
      throw new EntityValidationException(command.errors)
    }

    DateTime expirationDate = getValidity(command)

    def balance, depositId
    List params = [command.account.id, command.amount, command.concept, command.activationDate.toString(DATE_FORMAT),
                   expirationDate?.toString(DATE_FORMAT), Sql.DECIMAL, Sql.BIT, Sql.BIGINT]

    callStoreProcedure('{call accountAddBits( ?, ?, ?, ?, ?, ?, ?, ?)}', params) { balanceOut, successOut, depositIdOut ->
      balance = balanceOut
      depositId = depositIdOut
    }

    [balance: balance, expirationDate: expirationDate, depositId: depositId]
  }

  def withdrawBits(WithdrawBitCommand command) {
    def account = command.account

    if (isValidAccount(command) || !command.concept || command.amount < 0 || account.balance < command.amount) {
      throw new EntityValidationException(command.errors)
    }
    
    def bagName = command.bagName ?: BagEnum.SALES.name 
    if( bagName in finalBags ) {
      TransferBitCommand transferCmd = createTransferCommandWithoutExpiration(command, bagName)
      def result = executeTransfer(transferCmd)
      def mapTransfer = [balance: result.sourceBalance,
              transactionId: result.transactionWithdrawId,
              orderNumber: command.properties.orderNumber]
      publishMessageService.publishResponseMessageToOrders(mapTransfer)
      return mapTransfer
    } else {
      command.errors.reject("account.not.available", "No se permiten transacciones hacia la bolsa ${bagName}")
      throw new EntityValidationException(command.errors)
    } 
  }


  private boolean isValidAccount(BitCommand command) throws BitsAccountNotFoundException, InvalidAccountException{
    if(command?.account == null) {
      throw new BitsAccountNotFoundException(command.id)
    }

    if(command?.account?.status != AccountStatus.ACTIVE){
      throw new InvalidAccountException(command.id)
    }

  }

  private boolean isValidAccount(Account account) throws InvalidAccountException {
    def result = !account || account.status != AccountStatus.ACTIVE

    if(result){
      throw new InvalidAccountException(account.id)
    }
    result
  }

  private boolean isValidAccount(BitCommand command, String account) throws InvalidAccountException {
    def result = !command?."$account" || command?."$account"?.status != AccountStatus.ACTIVE

    if(result){
      command.errors.reject("account.not.found", "No se encontró la cuenta de ${account}")
    }
    !result
  }

  def tranferBits(TransferBitCommand command) {
    isValidAccount(command,"sAccount")
    isValidAccount(command,"tAccount")

    log.debug "Account type : ${command?.sAccount?.type}"
    if (command?.sAccount?.type == AccountType.USER) {
      command.errors.reject("USER.accountType", "La cuenta origen es una cuenta de usuario")
    }
    if (command?.sAccount?.balance < command.amount) {
      command.errors.reject("not.balance", "la cuenta origen no tiene los fondos suficiente")
    }
    if (!command.concept) {
      command.errors.reject("not.concept", "falta el concepto")
    }
    if(command.hasErrors()) {
      throw new EntityValidationException(command.errors)
    }
    //TODO:Por ahora no se soporta bits desactivados para las cuentas de usuario,
    // de tal forma que todos los depositos que se realicen seran activos.
    command.activationDate = new DateTime()

    executeTransfer(command)

  }

  private createTransferCommandWithoutExpiration(BitCommand command, String bagTargetName){
    TransferBitCommand transferCmd = new TransferBitCommand()
    transferCmd.sAccount = command.account
    transferCmd.tAccount = Bag.findByName(bagTargetName)?.account
    transferCmd.amount = command.amount
    transferCmd.concept = command.concept
    transferCmd.activationDate = new DateTime() 
    transferCmd.validity = AccountValidity.RELATIVE
    transferCmd.duration = 12000
    transferCmd
  }


  private executeTransfer(command) {
  
    DateTime expirationDate = getValidity(command)
    
    Map responseMap = [expirationDate: expirationDate]

    List params = [
        command.sAccount.id,
        command.tAccount.id,
        command.amount,
        command.concept,
		    command.activationDate.toString(DATE_FORMAT),
		    expirationDate.toString(DATE_FORMAT),
		    Sql.DECIMAL,
		    Sql.DECIMAL,
		    Sql.BIGINT,
		    Sql.BIGINT,
		    Sql.BIT
    ]

    callStoreProcedure('{call accountTransferBits( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}',
       params) { sourceBalance, targetBalance, transactionWithdrawId, transactionDepositId, successOut ->
      if (successOut) {
        responseMap.sourceBalance = sourceBalance
        responseMap.targetBalance = targetBalance
	      responseMap.transactionWithdrawId = transactionWithdrawId
	      responseMap.transactionDepositId = transactionDepositId
      }
    }

    responseMap
  }

  def rollback(RefundBitsCommand command) {
    
    if (!command.concept) {
      command.errors.reject("not.concept", "Falta el concepto.")
    }
    
    Transaction transaction = Transaction.findByIdAndAccount(
    	command.transactionId, command.account)

    if (!transaction) {
      command.errors.reject("not.transaction", "La transacción a revertir no existe.")
    }

    if(command.hasErrors()) {
      throw new EntityValidationException(command.errors)
    }
    
    def responseMap = [:]

    def expiredOffset = configurationService.getConfigValue(
 				EXPIRED_OFFSET, Integer)
    List params = [command.transactionId,
	                command.concept,
			expiredOffset,
			Sql.DECIMAL,
			Sql.BIT]
    callStoreProcedure('{call accountRollback(?,?,?,?,?)}', params) {
	   targetBalance, successOut -> 
      if(successOut) {
	 responseMap.balance = targetBalance
      }
    }

    responseMap
  }

  @Transactional(readOnly = true)
  def history(HistoryBitCommand command) {
    isValidAccount(command)

    log.debug "from ${command.from.toDateTimeAtStartOfDay()}"
    def to = command.to.plusDays(1).toDateTimeAtStartOfDay().minusSeconds(1)
    log.debug "to ${to}"



    def transactions = Transaction.findAllByAccountAndDateCreatedBetween(command.account, command.from.toDateTimeAtStartOfDay(), to,
        [sort: command.sort ?: 'dateCreated', order: command.order ?: 'desc' , offset: command.offset ?: 0, max:command.max])

    def transactionsMap = []
    def transactionBalance = 0

    if(!transactions.isEmpty()) {
      def maxId = transactions.id.max()
      transactionBalance = transactions.find{ it.id == maxId }?.balance
    }

    transactions?.each {
      def transaction = [:]
      if (it.deposits) {
        transaction.amount = it.amount
        transaction.balance = it.balance
        transaction.concept = it.concept
        transaction.dateCreated = it.deposits.first().dateCreated
        transaction.activationDate = it.deposits.first().activationDate
        transaction.expirationDate = it.deposits.first().expirationDate
        transactionsMap << transaction
      } else if (it.withdrawals.size() > 0) {
        transaction.amount = it.amount
        transaction.balance = it.balance
        transaction.concept = it.concept
        transaction.dateCreated = it.dateCreated
        transactionsMap << transaction
      }
    }

    [balance: transactionBalance, transactions: transactionsMap]
  }

  def historyCount(HistoryBitCommand command){
      def countTransactions = Transaction.countByAccount(command.account)
      [totalTransactions: countTransactions]
  }

  def expireBits() {
    def depositos = Deposit.findAllByAmountGreaterThanAndExpirationDateLessThan(0, new DateTime().withTimeAtStartOfDay())
    def expiracionesTotales = depositos.size()
    log.debug "depositos: ${expiracionesTotales}"
    def expiraciones = 0
    depositos.each {
      if( it.account.type == AccountType.USER) {
        def command = new BitCommand(id: it.account.id, amount: it.amount, concept: "Expiración de puntos")
        command.beforeValidate()
        TransferBitCommand transferCmd = createTransferCommandWithoutExpiration(command, BagEnum.CANCELED.name)
        def resultado = executeJobTransfer(it.id, transferCmd)
        if (!resultado) {
          log.error "Fallo al expirar los puntos de cuenta: ${it.transaction.account}, deposito: ${it}"
        }

        expiraciones++
      }
    }
    [totalExpired: expiracionesTotales, actualExipred: expiraciones]
  }


  private executeJobTransfer(Long depositId, command) {
    DateTime expirationDate = getValidity(command)

    Map responseMap = [expirationDate: expirationDate]

    List params = [
        command.sAccount.id,
        command.tAccount.id,
        command.concept,
        depositId,
        command.activationDate.toString(DATE_FORMAT),
        expirationDate.toString(DATE_FORMAT),
        Sql.DECIMAL,
        Sql.DECIMAL,
        Sql.BIGINT,
        Sql.BIGINT,
        Sql.BIT]

    callStoreProcedure('{call accountExpireBits( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}', params) {
      sourceBalance, targetBalance, transactionWithdrawId, transactionDepositId, successOut ->
      if (successOut) {
        responseMap.sourceBalance = sourceBalance
        responseMap.targetBalance = targetBalance
        responseMap.transactionWithdrawId = transactionWithdrawId
        responseMap.transactionDepositId = transactionDepositId
      }
    }

    responseMap
  }

  def updateBalance() {
    callStoreProcedure("{call accountUpdateBalance(?)}", [Sql.BIT]) { successOut -> }
  }

  def registerReward(RewardRegisterCommand command) {
    RewardRegister.withTransaction {
      RewardRegister rewardRegister = new RewardRegister()

      rewardRegister.account = command.account
      rewardRegister.wbOrderDetailId = command.orderDetailId
      rewardRegister.amount = command.amount
      rewardRegister.awarder = command.awarder
      rewardRegister.concept = command.concept
      rewardRegister.justification = command.justification
      rewardRegister.bitsCategory = command.bitsCategory
      rewardRegister.awardedDate = command.awardedDate
      rewardRegister.lastUpdated = command.lastUpdated

      if (!rewardRegister.save(flush: true)) {
        rewardRegister.errors.each {
          log.error(it)
        }
      }
    }
  }

  def getRewardRegister(Long orderDetailId) {
    def register = RewardRegister.findAllByWbOrderDetailId(orderDetailId)

    register
  }

  def registerGift(GiftRegisterCommand command) {
    GiftRegister.withTransaction {
      GiftRegister giftRegister = new GiftRegister()

      giftRegister.account = command.account
      giftRegister.amount = command.amount
      giftRegister.awarder = command.awarder
      giftRegister.concept = command.concept
      giftRegister.justification = command.justification
      giftRegister.bitsCategory = command.bitsCategory
      giftRegister.awardedDate = command.awardedDate
      giftRegister.lastUpdated = command.lastUpdated
      giftRegister.cknOrderId = command.cknOrderId

      if (!giftRegister.save(flush: true)) {
        giftRegister.errors.each {
          log.error(it)
        }
      }
    }
  }

  private callStoreProcedure(String stmt, List params, Closure callback) {
    Sql sql = new Sql(dataSource)

    try {
      sql.call stmt, params, callback
    } catch (e) {
      log.error GrailsUtil.sanitizeRootCause(e)
      throw (e instanceof RuntimeException ? e : new RuntimeException("ERROR on call to $stmt", GrailsUtil.sanitizeRootCause(e)))
    }
  }

  def withdrawBitsByRabbit(WithdrawBitCommand command) {
    if (!command?.account && command?.id) {
      command.account = Account.get(command?.id)
    }
    withdrawBits(command)
  }

  def transferBitsByRabbit(TransferBitCommand command) {
    command.sAccount = Bag.findByName(command?.bagName)?.account
    command.tAccount = Account.get(command?.targetAccount)
    command.activationDate = DateTime.now()

    tranferBits(command)
  }

  def rollbackByRabbit(RefundBitsCommand command) {
    if (!command?.account && command?.id) {
      command.account = Account.get(command?.id)
    }

    def transactionIds = findTranstactionByOrder(command.orderNumber, command.account)
    transactionIds?.each {
      command.transactionId = it
      rollback(command)
    }

  }

  def registerRewardByRabbit(RewardRegisterCommand command) {
    command.account = Account.get(command?.accountId)
    command.bitsCategory = BitsCategory.get(command?.bitsCategoryId)
    command.awardedDate = DateTime.now()
    command.lastUpdated = DateTime.now()

    tranferBits(setTransferBitCommand(command))
    registerReward(command)
  }

  def registerGiftByRabbit(GiftRegisterCommand command) {
    command.account = Account.get(command?.accountId)
    command.bitsCategory = BitsCategory.get(command?.bitsCategoryId)
    command.awardedDate = DateTime.now()
    command.lastUpdated = DateTime.now()

    tranferBits(setTransferBitCommand(command))
    registerGift(command)
  }

  private def findTranstactionByOrder(String orderNumber, Account account) {
    Withdrawal.executeQuery("""
            select t.id from Withdrawal as w left join w.transaction as t
            where w.account = :account and t.concept like :concept and w.refunded = false group by t.id""",
        [account: account, concept: "%Compra de la orden $orderNumber%"])
  }

  private def setTransferBitCommand(command) {
    TransferBitCommand transferBitCommand = new TransferBitCommand()
    transferBitCommand.bagName = command.bagName
    transferBitCommand.targetAccount = command.accountId
    transferBitCommand.sAccount = Bag.findByName(command?.bagName)?.account
    transferBitCommand.tAccount = Account.get(command?.accountId)
    transferBitCommand.activationDate = DateTime.now()
    transferBitCommand.validity = AccountValidity.valueOf(command?.validity)
    transferBitCommand.duration = command.duration
    transferBitCommand.account = command.account
    transferBitCommand.amount = command.amount
    transferBitCommand.concept = command.concept

    transferBitCommand
  }

}
