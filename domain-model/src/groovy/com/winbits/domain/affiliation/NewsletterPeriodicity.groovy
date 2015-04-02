package com.winbits.domain.affiliation

/**
 * Created with IntelliJ IDEA.
 * User: theo
 * Date: 4/19/13
 * Time: 7:27 PM
 * To change this template use File | Settings | File Templates.
 */
public enum NewsletterPeriodicity {
  daily, weekly

  static NewsletterPeriodicity defaultPeriodicity() {
    weekly
  }
}