package com.Group7.SpringStep.ui;

import java.awt.*;

import javax.swing.border.*;

/** Border that adds internal padding to a control */
// I probably should have named this PaddingBorder or something.
public class MarginBorder extends EmptyBorder
{
    ///////////////////////////////////////////////// CONSTRUCTORS /////////////////////////////////////////////////
    public MarginBorder (int top, int left, int bottom, int right) { super(top, left, bottom, right); }

    ///////////////////////////////////////////////// OVERRIDES /////////////////////////////////////////////////
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) 
    {
        // Yes, we don't do anything here
    }
}
