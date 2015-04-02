package com.winbits.domain.common

import com.winbits.domain.Enumable

class Department implements  Enumable<DepartmentEnum>{

  String name
  String description

  static transients = ['enum']
  static constraints = {
    name unique: true, blank: false, maxSize: 25
    description nullable: true, maxSize: 100
  }

  @Override
  DepartmentEnum getEnum(){
    def theId= getId()
    if(theId){
      DepartmentEnum.find{it.id==theId}
    }
  }

  @Override
  boolean equals(DepartmentEnum enumConstants){
    getId() == enumConstants.id
  }
}
