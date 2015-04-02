package com.winbits.bits

public enum BagEnum {
  CANCELED("CANCELED", "Bolsa para alojar los bits expirados."),
  SALES("SALES", "Bolsa que recibe el consumo de bits por compras."),
  CASHBACK_REFUNDED("CASHBACK_REFUNDED", "Bolsa de bits originados por cashback y cargados por el proceso de reembolso.")

  final String name
  final String description

  BagEnum(String name, String description){
    this.name = name
    this.description = description
  }

}
