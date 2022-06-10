package com.Group7.SpringStep;

import java.time.*;
import java.util.ArrayList;
import java.awt.*;

import java.io.*;

import java.net.*;

import javax.swing.*;
import javax.swing.border.*;

import com.Group7.SpringStep.ui.*;

import net.coobird.thumbnailator.*;
import net.coobird.thumbnailator.resizers.configurations.*;

/**
 * Class that contains useful methods that are used throughout the codebase
 */
public class Utils 
{
    /** Determines if some panels that are normally invisible be rendered. Used for debugging. */
    private static boolean layoutDebugMode = false;

    /**
     * Sets the target component to render a background color. Used for debugging
     * @param targetComponent The target component
     * @param backgroundColor The background color that it should have
     */
    public static void setDebugVisible(JComponent targetComponent, Color backgroundColor)
    {
        if (layoutDebugMode) {
            targetComponent.setOpaque(true);
            targetComponent.setBackground(backgroundColor);
        }
    }
    
    /**
     * Adds padding to the target component
     * @param targetComponent The component to add padding to
     * @param top The size of the top space
     * @param left The size of the left space
     * @param bottom The size of the bottom space
     * @param right The size of the right space
     */
    public static void padJComponent (JComponent targetComponent, int top, int left, int bottom, int right)
    {
        Border outerBorder = new MarginBorder(top, left, bottom, right);
        Border insideBorder = targetComponent.getBorder();
        targetComponent.setBorder(new CompoundBorder(outerBorder, insideBorder));
    }

    /**
     * Gets a string of a LocalTime formatted to the HH:MM:SS format 
     * @param time The LocalTime object to format
     * @return A {@code String} with the time formatted to HH:MM:SS
     */
    public static String formatTime(LocalTime time)
    {
        return String.format("%02d:%02d:%02d", time.getHour(), time.getMinute(), time.getSecond());
    }

    /**
     * Checks if the mouse is hovering over the visible parts of the target component
     * @param mouseScreenPosition The mouse position on display coordinates
     * @param component The target component
     * @return {@code true} if the mouse is hovering over the visible part of a component. {@code false} if otherwise.
     */
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

    /**
     * Hides the old window, shows the new window, then disposes the old window. Used for window navigation.
     * @param oldWindow The window to move from
     * @param newWindow The window to move to
     */
    public static void moveToNewWindow(JFrame oldWindow, JFrame newWindow) 
    {
        newWindow.setVisible(true);
        oldWindow.setVisible(false);
        oldWindow.dispose();
    }

    /**
     * Scales the target component to a percentage of the basis' size
     * @param comp The target component
     * @param basis The basis for the scaling
     * @param widthPercent The width percentage from 0 - 100
     * @param heightPercent The height percentage from 0 - 100
     */
    public static void scaleByPercentage(Component comp, Dimension basis, float widthPercent, float heightPercent)
    {
        // Clamp the given width and height percentages between 0 and 100
        widthPercent = Math.min(100f, Math.max(widthPercent, 0f));
        heightPercent = Math.min(100f, Math.max(heightPercent, 0f));

        // Get the width and height of the basis
        int basisWidth = (int) basis.getWidth();
        int basisHeight = (int) basis.getHeight();

        // Calculate the new width and height of the target component
        int width = Math.round(basisWidth * (widthPercent / 100f));
        int height = Math.round(basisHeight * (heightPercent / 100f));
        comp.setSize(width, height);

        // Make sure to revalidate and repaint for the changes to display properly
        comp.revalidate();
        comp.repaint();
    }

    /**
     * Sets the icon of the target button and disables the rendering of the button's border and background
     * @param button The target button
     * @param icon The icon that will be placed on the button
     */
    public static void setButtonIcon(JButton button, Icon icon)
    {
        button.setIcon(icon);
        button.setContentAreaFilled(false); // Disable background color rendering
        button.setBorderPainted(false); // Disable border rendering
        button.setFocusPainted(false); // Disable the rendering of the box that shows when the button has focus
    }

    /**
     * Centers the target component based on the basis' dimensions
     * @param comp The target component
     * @param basisWidth The width of the basis
     * @param basisHeight the height of the basis
     */
    public static void centerByRect(Component comp, int basisWidth, int basisHeight) 
    {
        // Calculate the new position of the component
        int x = Math.round(basisWidth / 2 - comp.getWidth() / 2);
        int y = Math.round(basisHeight / 2 - comp.getHeight() / 2);
        comp.setLocation(x, y);

        // Make sure to revalidate and repaint for the changes to display properly
        comp.revalidate();
        comp.repaint();
    }

    /**
     * Imports the specified image and scales it down based on the scale factor
     * @param path The URL path to the image resource
     * @param scale The target scale of the image from 0.0 - 1.0
     * @return The scaled imported image if it exists. {@code null} if it doesn't
     */
    public static Image getScaledImage(URL path, double scale)
    {
        Image scaledImage = null;
        try 
        {
            scaledImage = Thumbnails.of(path)
                                    .alphaInterpolation(AlphaInterpolation.QUALITY)
                                    .antialiasing(Antialiasing.ON)
                                    .rendering(Rendering.QUALITY)
                                    .scalingMode(ScalingMode.BILINEAR)
                                    .scale(scale)
                                    .asBufferedImage();
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
        return scaledImage;
    }

    /**
     * Checks if the passed text is really empty
     * @param text The text to check
     * @return {@code true} if the text is empty. {@code false} otherwise.
     */
    public static boolean isTextEmpty(String text) 
    {
        return text == null || text.trim().equals("");
    }

    /**
     * Formats a string to be to be CSV-friendly by replacing escape characters that could compromise
     * CSV-parsing
     * @param input The string to be formatted
     * @return The CSV-friendly formatted string
     */
    public static String getCsvFriendlyFormat(String input)
    {
        return input.replace("\\", "\\\\")
                .replace(",", "\\,")
                .replace("\n", "\\n")
                .replace("\"", "\\\"");
    }

    /**
     * Converts a CSV-friendly formatted string back to its original form
     * @param input The CSV-friendly formatted string
     * @return The original form of the given string
     */
    public static String parseCsvFriendlyFormat(String input)
    {
        return input.replace("\\\\", "\\")
                .replace("\\,", ",")
                .replace("\\n", "\n")
                .replace("\\\"", "\"");
    }
    
    /**
     * Splits a CSV-friendly formatted string. Because String.split() can still split it improperly
     * @param input The CSV-friendly formatted string
     * @return An Strin array containing the properly split parts of the CSV-friendly formatted string
     */
    public static String[] splitCsv(String input)
    {
        // Put the split parts into an array list first, for easy handling
        ArrayList<String> parts = new ArrayList<>();
        String[] split = input.split(",");
        for (String part : split) { parts.add(part); }

        /* To clarify:
         * "," is a CSV comma. We want to split the input by this character
         * "\," is a formatted/real comma. We want to preserve these
        */

        // If the input ends with a real comma, append a comma to the last split string
        if (input.endsWith("\\,") && parts.size() > 0)
        {
            parts.set(parts.size() - 1, parts.get(parts.size() - 1) + ",");
        }

        /* Combine strings with real/formmatted commas together */
        for (int i = 0; i < parts.size();) 
        {
            String currentPart = parts.get(i);
            if(currentPart.endsWith("\\"))
            {
                if (i + 1 < parts.size()) {
                    parts.set(i, currentPart + "," + parts.get(i + 1));
                    parts.remove(i + 1);
                }
            }
            else { i++; }
        }

        /* Put the processed results in the array before returning it
         * Because unlike C#, Java's ArrayList<T>.toArray() sucks
        */
        String[] result = new String[parts.size()];
        for(int i = 0; i < parts.size(); i++)
        {
            result[i] = parts.get(i);
        }
        return result;
    }
}
