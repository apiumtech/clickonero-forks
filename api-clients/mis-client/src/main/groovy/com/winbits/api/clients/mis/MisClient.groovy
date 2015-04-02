package com.winbits.api.clients.mis

interface MisClient<T extends MisBase> {
  void setMisManagerSender(MisManagerSender misManagerSender)

  MisManagerSender getMisManagerSender()

  boolean logMessage(T obj)
}
