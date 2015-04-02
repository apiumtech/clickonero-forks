package com.winbits.domain.common

import com.winbits.domain.PersistentEnum

public enum DepartmentEnum implements PersistentEnum<Department>{
  DEPTO_SYSTEM(1L),
  DEPTO_SALES(2L),
  DEPTO_SAC(3L),
  DEPTO_LOGISTIC(4L)

  final Serializable id

  DepartmentEnum(Serializable id){
    this.id=id
  }

  @Override
  Department getDomain(){
    Department.load(id)
  }

  @Override
  boolean equals(Department domainInstance){
    id == domainInstance?.getId()
  }
}
