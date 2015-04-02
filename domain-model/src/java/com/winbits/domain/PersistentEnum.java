package com.winbits.domain;

import java.io.Serializable;

public interface PersistentEnum<T extends Enumable> {
  public Serializable getId();

  public T getDomain();

  public boolean equals(T domainInstance);
}
