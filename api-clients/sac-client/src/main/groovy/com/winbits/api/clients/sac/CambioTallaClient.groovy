package com.winbits.api.clients.sac

interface CambioTallaClient {
  Map listadoCambioTalla (Map params)  
  Map cambioTallaDetalle (Long orderId)
  Map listadoItems (Long idDetalle,Integer cantidad )
  Map cambiaTalla (Integer cantidad, Long idSkuProfile, Long idDetalle)
  Map login (String user, String password)
}
