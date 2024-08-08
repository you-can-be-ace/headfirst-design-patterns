package com.ace.example.chapter09.iterator;

import com.ace.example.chapter09.menu.MenuItem;
import java.util.Iterator;

public class DinerMenuIterator implements Iterator {

  MenuItem[] items;
  int position = 0;

  public DinerMenuIterator(MenuItem[] items) {
    this.items = items;
  }

  @Override
  public boolean hasNext() {
    if (position >= items.length || items[position] == null) {
      return false;
    }

    return true;
  }

  @Override
  public MenuItem next() {
    var item = items[position];
    position++;
    return item;
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException("메뉴 항목은 지우면 안됩니다.");
  }
}
