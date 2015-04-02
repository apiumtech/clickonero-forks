package com.winbits.domain.catalog

import com.winbits.domain.PersistentEnum

public enum CreditCardTypeEnum implements PersistentEnum<CreditCardType> {

    VISA(1L),
    MASTER_CARD(2L),
    BANAMEX(3L)

    final Serializable id;
    
    CreditCardTypeEnum(Serializable id){
        this.id = id
    }

    @Override
    CreditCardType getDomain(){
        CreditCardType.load(id)
    }

    @Override
    boolean equals(CreditCardType domainInstance){
        id == domainInstance?.getId()
    }

}
