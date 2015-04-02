package com.winbits.api.clients.bits

interface BitsClient {
  Map createBitsAccount(BitsAccountType accountType, BitsAccountStatus accountStatus)

  BigDecimal getBitsBalance(Long bitsAccountId)

  Map getBitsAccountHistory(Long accountId, Map params)

  Map depositBits(BitsDepositRequest request)

  Map withdrawBits(Long bitsAccountId, BigDecimal amount, String concept)

  Map transferBits(BitsTransferRequest request)

  Map refundBits(Long bitsAccountId, Long referenceOrderPayment)

  Map rewardBitsRegister(BitsRewardConfigRequest request)

  Map getRewardRegister(Long orderDetailId)

  Map giftsRegister(GiftsRewardConfigRequest request)
}
