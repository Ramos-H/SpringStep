package com.Group7.SpringStep;

import java.awt.*;

import javax.swing.*;

public class Utils 
{
    private static boolean layoutDebugMode = true;

    public static void setDebugVisible(JComponent targetComponent, Color backgroundColor)
    {
        if(layoutDebugMode)
        {
            targetComponent.setOpaque(true);
            targetComponent.setBackground(backgroundColor);
        }
    }
}
