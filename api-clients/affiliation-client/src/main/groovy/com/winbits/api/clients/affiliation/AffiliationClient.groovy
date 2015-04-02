package com.winbits.api.clients.affiliation

interface AffiliationClient {
  Map register(String email, String password)
  Map login (String email, String password)
  Map couponList(Long orderDetailId, String apiToken)
  Map couponId(Long couponId,Long orderDetailId,String format, String apiToken)
}
