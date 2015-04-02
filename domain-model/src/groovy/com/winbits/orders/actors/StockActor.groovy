package com.winbits.orders.actors

import groovyx.gpars.actor.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class StockActor extends DefaultActor{

  private Logger log = LoggerFactory.getLogger(StockActor)

  protected void act(){
    log.debug 'inicia acting'
  }

  private void afterStart(){
    log.debug 'inicia'
  }
}
