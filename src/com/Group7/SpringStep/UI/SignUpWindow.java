package com.Group7.SpringStep.UI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;

public class SignUpWindow extends JFrame
{
    public SignUpWindow()
    {
        // Set window parameters first
        setTitle("SpringStep - Sign Up");

        Rectangle screenSize = getGraphicsConfiguration().getBounds();
        float widthScale = 40f;
        float heightScale = 80f;
        int width = Math.round(screenSize.width * (widthScale / 100f));
        int height = Math.round(screenSize.height * (heightScale / 100f));
        setSize(width, height);

        int x = Math.round(screenSize.width / 2 - getWidth() / 2);
        int y = Math.round(screenSize.height / 2 - getHeight() / 2);
        setLocation(x, y);
        {
            // Initialize nested components here
            JPanel mainPanel = new JPanel(new FlowLayout());
            mainPanel.setBackground(Color.red);
            {
                // Initialize nested components here
                JLabel label = new JLabel("Hello");
                label.setForeground(Color.WHITE);

                // Put the call to add the nested components here
                mainPanel.add(label);
            }

            // Put the call to add the nested components here
            add(mainPanel);
        }
    }
}