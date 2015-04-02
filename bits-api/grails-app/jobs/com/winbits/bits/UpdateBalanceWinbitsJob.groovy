package com.winbits.bits

/**
 * Created with IntelliJ IDEA.
 * User: hgmiguel
 * Date: 5/3/13
 * Time: 5:44 PM
 * To change this template use File | Settings | File Templates.
 */
class UpdateBalanceWinbitsJob {
  def accountService
    static triggers = {
//        cron name: "updateBalanceWinbits", cronExpression: "0 0/5 * * * ?"
        cron name: "updateBalanceWinbits", cronExpression: "59 59 23 * * ?"
    }

    def execute() {
        def resultado = accountService.updateBalance()
        log.debug "resultado del cron: ${resultado}"
    }
}
