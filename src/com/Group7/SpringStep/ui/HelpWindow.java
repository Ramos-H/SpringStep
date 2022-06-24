package com.Group7.SpringStep.ui;

import javax.swing.*;

import java.awt.*;
import java.io.IOException;

import com.Group7.SpringStep.App;
import com.Group7.SpringStep.Utils;

/** Represents the Help Window */
public class HelpWindow extends JDialog
{
    ///////////////////////////////////////////////// CONSTRUCTORS /////////////////////////////////////////////////
    public HelpWindow()
    {
        Dimension screenSize = getToolkit().getScreenSize();
        // Scale this window by 40% of the width and height of the screen size
        Utils.scaleByPercentage(this, screenSize, 40, 40);
        // Center this window on the screen
        Utils.centerByRect(this, (int) screenSize.getWidth(), (int) screenSize.getHeight());

        setLayout(new GridLayout(1, 1));
        setTitle("Help Page");
        setIconImage(App.springStepImage);
        {
            JScrollPane scrollPane = new JScrollPane(); // Needed to make the content scrollable
            try 
            {
                // Java Swing can interpret HTML, apparently. Import the HTML with the content for the Help Window
                JEditorPane editorPane = new JEditorPane(App.resources.get("Help_Page_Content.html"));
                editorPane.setEditable(false); // Don't allow the user to edit the content
                scrollPane.setViewportView(editorPane);
            } catch (IOException e) 
            {
                e.printStackTrace();
            }
            add(scrollPane);
        }
    }
}
