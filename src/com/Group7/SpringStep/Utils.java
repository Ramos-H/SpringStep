package com.Group7.SpringStep;

import java.time.*;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import com.Group7.SpringStep.ui.MarginBorder;

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
        Border outerBorder = new MarginBorder(top, left, bottom, right);
        Border insideBorder = targetComponent.getBorder();
        targetComponent.setBorder(new CompoundBorder(outerBorder, insideBorder));
    }

    public static String formatTime(LocalTime time)
    {
        return String.format("%02d:%02d:%02d", time.getHour(), time.getMinute(), time.getSecond());
    }

    public static boolean isMouseOverVisibleRect(Point mouseScreenPosition, JComponent component) 
    {
        Rectangle visibleRect = component.getVisibleRect();
        visibleRect.setLocation(component.getLocationOnScreen());
        boolean insideX = (mouseScreenPosition.x >= visibleRect.x)
                && (mouseScreenPosition.x <= visibleRect.x + visibleRect.width);
        boolean insideY = (mouseScreenPosition.y >= visibleRect.y)
                && (mouseScreenPosition.y <= visibleRect.y + visibleRect.height);
        return insideX && insideY;
    }

    public static void moveToNewWindow(JFrame oldWindow, JFrame newWindow) 
    {
        newWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        newWindow.setVisible(true);
        oldWindow.setVisible(false);
        oldWindow.dispose();
    }

    public static boolean isTextEmpty(String text) 
    {
        return text == null || text.trim().equals("");
    }
}
