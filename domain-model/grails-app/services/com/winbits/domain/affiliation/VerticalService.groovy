package com.winbits.domain.affiliation

import com.winbits.domain.exception.VerticalNotFoundException

class VerticalService {

  Vertical byId(Long id) {
    if(!id) {
      throw new VerticalNotFoundException()
    }

    def vertical = Vertical.get(id)
    if (!vertical) {
      throw new VerticalNotFoundException(id)
    }
    vertical
  }

  List getAll() {
    Vertical.all
  }
}
