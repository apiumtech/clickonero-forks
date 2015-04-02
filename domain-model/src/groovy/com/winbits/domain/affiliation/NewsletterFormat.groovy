package com.winbits.domain.affiliation

/**
 * Created with IntelliJ IDEA.
 * User: theo
 * Date: 4/19/13
 * Time: 7:26 PM
 * To change this template use File | Settings | File Templates.
 */
public enum NewsletterFormat {
  unified, separated

  static NewsletterFormat defaultFormat() {
    unified
  }
}