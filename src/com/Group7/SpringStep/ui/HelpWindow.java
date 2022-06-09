package com.Group7.SpringStep.ui;

import javax.swing.*;

import java.awt.*;
import java.io.IOException;

import com.Group7.SpringStep.App;
import com.Group7.SpringStep.Utils;

public class HelpWindow extends JDialog
{
    public HelpWindow()
    {
        Dimension screenSize = getToolkit().getScreenSize();
        Utils.scaleByPercentage(this, screenSize, 40, 40);
        Utils.centerByRect(this, (int) screenSize.getWidth(), (int) screenSize.getHeight());
        setLayout(new GridLayout(1, 1));
        setTitle("Help Page");
        setIconImage(App.springStepImage);
        {
            JScrollPane scrollPane = new JScrollPane();
            try 
            {
                JEditorPane editorPane = new JEditorPane(App.resources.get("Help_Page_Content.html"));
                editorPane.setEditable(false);
                scrollPane.setViewportView(editorPane);
            } catch (IOException e) 
            {
                e.printStackTrace();
            }
            add(scrollPane);
        }
    }
}
