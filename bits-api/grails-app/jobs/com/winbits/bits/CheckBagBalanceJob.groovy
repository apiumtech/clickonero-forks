package com.winbits.bits

/**
 * Created by winbits on 2/5/15.
 */
class CheckBagBalanceJob {

  static triggers = {
    cron name: "checkBagBalance", cronExpression: "0 0/5 * * * ?"
  }

  def bagService

  def execute() {
    log.info("Execute checkBagBalance job ...")
    bagService.checkBalances()
  }
}
