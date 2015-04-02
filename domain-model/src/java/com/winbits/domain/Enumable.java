package com.winbits.domain;

public interface Enumable<T extends Enum & PersistentEnum> {
  public T getEnum();

  public boolean equals(T enumConstant);
}
