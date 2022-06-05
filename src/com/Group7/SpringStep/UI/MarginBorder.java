package com.Group7.SpringStep.ui;

import java.awt.*;

import javax.swing.border.*;

public class MarginBorder extends EmptyBorder
{
    public MarginBorder (int top, int left, int bottom, int right)
    {
        super(top, left, bottom, right);
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) 
    {
        // Yes, we don't do anything here
    }
}
