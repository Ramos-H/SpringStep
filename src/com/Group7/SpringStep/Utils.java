package com.Group7.SpringStep;

import java.time.*;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

import javax.swing.*;
import javax.swing.border.*;

import com.Group7.SpringStep.ui.MarginBorder;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.resizers.configurations.*;

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

    public static void scaleByPercentage(Component comp, Dimension basis, float widthPercent, float heightPercent)
    {
        widthPercent = Math.min(100f, Math.max(widthPercent, 0f));
        heightPercent = Math.min(100f, Math.max(heightPercent, 0f));
        int basisWidth = (int) basis.getWidth();
        int basisHeight = (int) basis.getHeight();

        int width = Math.round(basisWidth * (widthPercent / 100f));
        int height = Math.round(basisHeight * (heightPercent / 100f));
        comp.setSize(width, height);
        comp.revalidate();
        comp.repaint();
    }

    public static void centerByRect(Component comp, int basisWidth, int basisHeight) 
    {
        int x = Math.round(basisWidth / 2 - comp.getWidth() / 2);
        int y = Math.round(basisHeight / 2 - comp.getHeight() / 2);
        comp.setLocation(x, y);
        comp.revalidate();
        comp.repaint();
    }

    public static Image getScaledImage(URL path, double scale)
    {
        Image scaledImage = null;
        try 
        {
            scaledImage = Thumbnails.of(path)
                                    .alphaInterpolation(AlphaInterpolation.QUALITY)
                                    .antialiasing(Antialiasing.ON)
                                    .rendering(Rendering.QUALITY)
                                    .scalingMode(ScalingMode.PROGRESSIVE_BILINEAR)
                                    .scale(0.10f)
                                    .asBufferedImage();
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
        return scaledImage;
    }

    public static boolean isTextEmpty(String text) 
    {
        return text == null || text.trim().equals("");
    }
}
