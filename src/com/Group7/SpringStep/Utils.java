package com.Group7.SpringStep;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

public class Utils 
{
    private static boolean layoutDebugMode = true;

    public static void setDebugVisible(JComponent targetComponent, Color backgroundColor)
    {
        if (layoutDebugMode) {
            targetComponent.setOpaque(true);
            targetComponent.setBackground(backgroundColor);
        }
    }
    
    public static void padJComponent (JComponent targetComponent, int top, int left, int bottom, int right)
    {
        Border outerBorder = BorderFactory.createEmptyBorder(top, left, bottom, right);
        Border insideBorder = targetComponent.getBorder();
        targetComponent.setBorder(new CompoundBorder(outerBorder, insideBorder));
    }
}
