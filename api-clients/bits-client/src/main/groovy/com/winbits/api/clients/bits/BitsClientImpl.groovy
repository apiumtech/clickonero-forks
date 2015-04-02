package com.winbits.api.clients.bits

import com.winbits.api.clients.ApiClientsValidator
import com.winbits.api.dummyclients.bits.DummyBitsClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.util.Assert
import wslite.rest.ContentType
import wslite.rest.RESTClient
import wslite.rest.ResponseBuilder

import javax.annotation.PostConstruct

/**
 * Client to consume Winbits' bits API. Delegates to dummy implementation by default
 */
@Component('bitsClient')
class BitsClientImpl implements BitsClient {

  private static Logger log = LoggerFactory.getLogger(BitsClientImpl)

  @Autowired
  ApiClientsValidator apiClientsValidator

  @Autowired
  RESTClient bitsRestClient

  private DummyBitsClient dummy

  @PostConstruct
  private void init() {
    dummy = new DummyBitsClient(apiClientsValidator: apiClientsValidator)
  }

  @Override
  Map createBitsAccount(BitsAccountType accountType, BitsAccountStatus accountStatus) {
    log.info('Creating new bits account [type: {}, status: {}]', accountType, accountStatus)
    def response = bitsRestClient.post(path: '/accounts.json') {
      type ContentType.JSON
      json type: accountType.name(), status: accountStatus.name()
    }

    if (response.statusCode >= 400) {
      log.error('Error while creating new bits account')
      throw new RuntimeException(response.json.meta.message)
    }

    def jsonResponse = response.json.response

    [id: jsonResponse.id, balance: jsonResponse.balance]
  }

  @Override
  BigDecimal getBitsBalance(Long bitsAccountId) {
    log.info('Getting bits balance of account {}', bitsAccountId)
    Assert.notNull(bitsAccountId, 'Please, specify the bits account id!')
    def response = bitsRestClient.get(path: "/accounts/${bitsAccountId}/balance.json")

    if (response.statusCode >= 400) {
      log.error('Error while getting bits account balance')
      throw new RuntimeException(response.json.meta.message)
    }

    response.json.response.balance.toString().toBigDecimal()
  }

  @Override
  Map getBitsAccountHistory(Long accountId, Map params = [:]) {
    log.info "entra $accountId"
   
    Assert.notNull(accountId, 'Please, specify the bits account id!')
    def response 
    try { 
    response = bitsRestClient.get(path: "/accounts/${accountId}/history.json", query: params)
    } catch(e) {
        response = new ResponseBuilder().build(e.request, e.response)
    }

    //  type ContentType.JSON
//      json id: accountId
    
    log.info "respuesta bits history"
    if (response.statusCode >= 400) {
      log.error('Error while creating new bits account')
      throw new RuntimeException(response.json.meta.message)
    }
    log.info "All json to received -> ${response.json}"

    response.json
  }

  @Override
  Map depositBits(BitsDepositRequest request) {
    dummy.depositBits(request)
  }

  @Override
  Map withdrawBits(Long bitsAccountId, BigDecimal amount, String concept, String bagName="") {
    log.info "From account ${bitsAccountId} withdrawBits ${amount} for concept : ${concept}"
    Assert.notNull(bitsAccountId, 'Please, specify the bits account id!')
    Assert.notNull(amount, 'Please, specify the amount to withdraw!')
    Assert.notNull(concept, 'Please, specify the concept!')

    def response = bitsRestClient.post(path: "/accounts/${bitsAccountId}/withdraw.json"){
      json amount:amount, concept:concept, bagName:bagName    
    }

    response.json.response
  }

  @Override
  Map transferBits(BitsTransferRequest request) {
    log.info('Transferring {} bits from account {} to account {}', request.amount, request.bagName,
        request.targetAccount)
    Map transferData = request.properties.subMap(['bagName', 'targetAccount', 'amount', 'concept', 'duration'])
    if (request.activationDate) {
      transferData.activationDate = request.activationDate.toString('yyyy-MM-dd') + 'T00:00:00'
    }
    if (request.validity) {
      transferData.validity = request.validity.name()
    }

    def response = bitsRestClient.post(path: "/accounts/transfer.json"){
      json(transferData)
    }

    response.json.response
  }

  @Override
  Map refundBits(Long bitsAccountId, Long referenceOrderPayment){
    log.info('Refund bits account [account: {}, order payment: {}]', bitsAccountId, referenceOrderPayment)
    def params = [transactionId: referenceOrderPayment, concept: 'Refund']
    def response = bitsRestClient.post(path: "/accounts/${bitsAccountId}/rollback.json") {
      type ContentType.JSON
      json params
    }

    if (response.statusCode >= 400) {
      log.error('Error while refund bits')
      throw new RuntimeException(response.json.meta.message)
    }

    def jsonResponse = response.json.response

    [balance: jsonResponse.balance]
  }

  @Override
  Map rewardBitsRegister(BitsRewardConfigRequest request) {
    log.info("Register the rewarded ${request.amount} bits from order detail ${request.orderDetailId} to account ${request.accountId} by agent ${request.awarder}")
    def params = [
        accountId: request.accountId,
        orderDetailId: request.orderDetailId,
        awarder: request.awarder,
        amount: request.amount,
        concept: request.concept,
        bitsCategoryId: request.bitsCategoryId,
        justification: request.justification
    ]

    def response = bitsRestClient.post(path: '/reward/register') {
      type ContentType.JSON
      json params
    }

    response.json.response
  }

  @Override
  Map getRewardRegister(Long orderDetailId) {
    log.info("Get the reward registers from the order detail ${orderDetailId}")
    def response

    try {
      response = bitsRestClient.get(path: "/find/reward/register/", query: [orderDetailId: orderDetailId])
    } catch (e) {
      log.error("Error trying to get the reward registers", e)
      response = new ResponseBuilder().build(e.request, e.response)
    }

    [response: response.json]
  }

  @Override
  Map giftsRegister(GiftsRewardConfigRequest request) {
    log.info("Register the rewarded gifts to account ${request.accountId} by ${request.awarder}")
    def params = [
        accountId: request.accountId,
        awarder: request.awarder,
        amount: request.amount,
        concept: request.concept,
        bitsCategoryId: request.bitsCategoryId,
        justification: request.justification,
        cknOrderId: request.cknOrderId
    ]

    def response = bitsRestClient.post(path: '/reward/gift') {
      type ContentType.JSON
      json params
    }

    response.json.response
  }
}
