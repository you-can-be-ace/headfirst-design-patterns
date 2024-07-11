package com.ace.example.chapter07.adapter.enumerationiterator;

import java.util.Enumeration;
import java.util.Iterator;

public class EnumerationIterator implements Iterator<Object> {

  Enumeration<?> enumeration;

  public EnumerationIterator(Enumeration<?> enumeration) {
    this.enumeration = enumeration;
  }

  @Override
  public boolean hasNext() {
    return enumeration.hasMoreElements();
  }

  @Override
  public Object next() {
    return enumeration.nextElement();
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException(); //Iterator의 remove() 메소드는 지원되지 않는다.
  }
}
