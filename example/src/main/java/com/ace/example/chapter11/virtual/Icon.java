package com.ace.example.chapter11.virtual;

import java.awt.*;

public interface Icon {
    public int getIconWidth();
    public int getIconHeight();
    public void paintIcon(Component c, Graphics  g, int x, int y);
}
