package com.winbits.bits

class ExpireWinbitsJob {
    def accountService
    static triggers = {
        cron name: "expireWinbits", cronExpression: "0 0/30 * * * ?"
        //cron name: "expireWinbits", cronExpression: "0 0 0 * * ?"
    }

    def execute() {
        def resultado = accountService.expireBits()
        log.debug "resultado del cron: ${resultado}"
    }
}
