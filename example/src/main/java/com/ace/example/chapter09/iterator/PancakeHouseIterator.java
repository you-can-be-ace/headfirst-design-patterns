package com.ace.example.chapter09.iterator;

import com.ace.example.chapter09.MenuItem;
import java.util.List;

public class PancakeHouseIterator implements Iterator {

  List<MenuItem> items;
  int position = 0;

  public PancakeHouseIterator(List<MenuItem> items) {
    this.items = items;
  }

  @Override
  public boolean hasNext() {
    if (position >= items.size() || items.get(position) == null) {
      return false;
    }

    return true;
  }

  @Override
  public MenuItem next() {
    MenuItem item = items.get(position);
    position++;
    return item;
  }
}
