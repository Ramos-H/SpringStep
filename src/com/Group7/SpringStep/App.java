package com.Group7.SpringStep;

import java.awt.*;

import javax.swing.*;

import com.Group7.SpringStep.ui.*;

public class App 
{
    public static Resources resources;
    public static Image springStepImage;
    public static void main(String[] args) 
    {
        resources = new Resources(); // Instantiate resource class that can be accessed anywhere

        // Import app logo that can be accessed anywhere
        springStepImage = Utils.getScaledImage(App.resources.get("SpringStep_Logo.png"), 1f);

        // Turn text anti-aliasing on
        System.setProperty("awt.useSystemAAFontSettings","on");
        System.setProperty("swing.aatext", "true");
        
        JFrame frame;
        frame = new LoginWindow();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
