package com.winbits.domain.common

/**
 * Created with IntelliJ IDEA.
 * User: jluis
 * Date: 2/12/13
 * Time: 09:44 AM
 * To change this template use File | Settings | File Templates.
 */
public interface WinbitsCronExpression {

  String WINBITS_CRON_SIX_O_CLOCK = '0 0 6 1/1 * ? *'
  String WINBITS_CRON_TWELVE_O_CLOCK = '0 0 12 1/1 * ? *'
  String WINBITS_CRON__MIDDLE_HOUR = '0 0/30 * 1/1 * ? *'
  List<String> GENERATE_OUTCOME_REQUEST_JOB_CRON_EXPRESSIONS = [WINBITS_CRON_SIX_O_CLOCK, WINBITS_CRON_TWELVE_O_CLOCK]
}