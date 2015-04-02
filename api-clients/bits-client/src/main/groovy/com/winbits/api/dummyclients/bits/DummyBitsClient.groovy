package com.winbits.api.dummyclients.bits

import com.winbits.api.clients.ApiClientsValidator
import com.winbits.api.clients.bits.*
import org.joda.time.DateTime
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.util.Assert

import javax.validation.ValidationException

@Component('bitsClient')
class DummyBitsClient implements BitsClient {

  @Autowired
  ApiClientsValidator apiClientsValidator
  
  private static Logger log = LoggerFactory.getLogger(DummyBitsClient)

  @Override
  Map createBitsAccount(BitsAccountType type, BitsAccountStatus status) {
    log.info('Creating new bits account [type: {}, status: {}]', type, status)
    Assert.notNull(type, 'Please, specify the bits account type!')
    Assert.notNull(status, 'Please, specify the bits account status!')
    [id: new Random().nextInt(), balance: 0.00]
  }

  @Override
  BigDecimal getBitsBalance(Long bitsAccountId) {
    log.info('Getting bits balance of account {}', bitsAccountId)
    Assert.notNull(bitsAccountId, 'Please, specify the bits account id!')
    50.00
  }

  @Override
  Map getBitsAccountHistory(Long accountId, Map params = [:]) {
    log.info('Getting history of bits account {} [from: {}, to: {}]', accountId, params.from, params.to)
    Assert.notNull(accountId, 'Please, specify the bits account id!')
    [
        balance: 50.00,
        transactions: [
            [
                amount: 100.00,
                balance: 100.00,
                concept: 'Regalo de crédito clickOnero',
                dateCreated: DateTime.parse('2013-01-01T00:00:00'),
                activationDate: null,
                expirationDate: DateTime.parse('2013-02-01T00:00:00')
            ],
            [
                amount: -50.00,
                balance: 50.00,
                concept: 'Compra de la orden 123--121212',
                dateCreated: DateTime.parse('2013-01-01T00:00:00')
            ]
        ]
    ]
  }

  @Override
  Map depositBits(BitsDepositRequest request) {
    log.info('Deposit bits using request {}', request)
    Assert.notNull(request, 'Please, specify a valid bits deposit request!')
    def errors = apiClientsValidator.validate(request)
    if (errors.hasErrors()) {
      throw new ValidationException(errors.dump())
    }
    [balance: request.amount, expirationDate: DateTime.now().plusDays(7).toDateMidnight()]
  }

  @Override
  Map withdrawBits(Long bitsAccountId, BigDecimal amount, String concept) {
    log.info('Withdraw bits from account {} [amount] and concept [concept]', bitsAccountId, amount, concept)
    Assert.notNull(bitsAccountId, 'Please specify the bits account id!')
    Assert.notNull(amount, 'Please specify the withdrawal amount!')
    Assert.notNull(concept, 'Please specify the concept for withdraw')
    Assert.isTrue(amount > 0.00)
    [balance: amount, expirationDate: DateTime.now().plusDays(7).toDateMidnight(), transactionId: 1L]
  }

  @Override
  Map transferBits(BitsTransferRequest request) {
    log.info('Transfer bits using request {}', request)
    Assert.notNull(request, 'Please, specify a valid bits transfer request!')
    def errors = apiClientsValidator.validate(request)
    if (errors.hasErrors()) {
      throw new ValidationException(errors.dump())
    }
    [sourceBalance: 300.00, targetBalance: 500.00, expirationDate: null]
  }

  @Override
  Map refundBits(Long bitsAccountId, Long referenceOrderPayment){
    log.info('Release bits using request {} bitsAccountId [bitsAccountId], and referenceOrderPayment [referenceOrderPayment]', bitsAccountId, referenceOrderPayment)
    Assert.notNull(bitsAccountId, 'Please specify the bits account id!')
    Assert.notNull(referenceOrderPayment, 'Please specify the order payment reference!')
    [balance: 50.0, expirationDate: DateTime.now().plusDays(7).toDateMidnight(), transactionId: 1L]
  }

  @Override
  Map rewardBitsRegister(BitsRewardConfigRequest request) {
    log.info("Register the rewarded ${request.amount} bits from order detail ${request.orderDetailId} to account ${request.accountId} by agent ${request.awarder}")
    Assert.notNull(request, 'Please, specify a valid bits reward config request!')
    [response: 'OK']
  }

  @Override
  Map getRewardRegister(Long orderDetailId) {
    log.info("Get the reward registers from the order detail ${orderDetailId}")
    Assert.notNull(orderDetailId, 'Please, specify a valid order detail id request!')
    [awarder: "Pinks Juarez", awardedDate: "2099-12-31 00:00:00", amount: "1000000", concept: "Abono chingon", justification: "Soy el patron", category: ""]
  }

  @Override
  Map giftsRegister(GiftsRewardConfigRequest request) {
    log.info("Register the rewarded gifts to account ${request.accountId} by ${request.awarder}")
    Assert.notNull(request, 'Please, specify a valid gifts config request!')
    [response: 'OK']
  }
}
