package com.winbits.api.clients.orders

interface OrdersClient {

  Map showForAdmin(Long id, String apiToken)
  Map cartItemsFromNomicka(List cartCommands, String apiToken)
  Map paymentOrder(Map request, String apiToken)

}