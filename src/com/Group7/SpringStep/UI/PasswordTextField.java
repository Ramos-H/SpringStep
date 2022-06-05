package com.Group7.SpringStep.ui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import com.Group7.SpringStep.*;

public class PasswordTextField extends JPanel implements ActionListener
{
    private boolean passwordIsVisible = false;
    private JPasswordField passwordField;
    private JButton passwordVisibilityButton;
    private ImageIcon passwordHiddenIcon;
    private ImageIcon passwordShownIcon;

    public PasswordTextField()
    {
        
        Image passwordHiddenSourceImage = Utils.getScaledImage(App.resources.get("Closed_Eye_Icon_256.png"), 12.5f);
        Image passwordShownSourceImage = Utils.getScaledImage(App.resources.get("Opened_Eye_Icon_256.png"), 12.5f);

        if (passwordHiddenSourceImage != null)
        {
            passwordHiddenIcon = new ImageIcon(passwordHiddenSourceImage);
        }

        if (passwordShownSourceImage != null)
        {
            passwordShownIcon = new ImageIcon(passwordShownSourceImage);
        }

        setLayout(new BorderLayout());
        {
            passwordField = new JPasswordField();
            passwordField.setEchoChar('*');
            passwordVisibilityButton = new JButton(passwordHiddenIcon);
            passwordVisibilityButton.setContentAreaFilled(false);
            passwordVisibilityButton.setBorderPainted(false);
            passwordVisibilityButton.setMargin(new Insets(0, 0, 0, 0));
            passwordVisibilityButton.addActionListener(this);
            add(passwordField, BorderLayout.CENTER);
            add(passwordVisibilityButton, BorderLayout.LINE_END);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (!passwordIsVisible) 
        {
            passwordField.setEchoChar((char) 0);
            passwordVisibilityButton.setIcon(passwordShownIcon);
        } else 
        {
            passwordField.setEchoChar('*');
            passwordVisibilityButton.setIcon(passwordHiddenIcon);
        }
        passwordIsVisible = !passwordIsVisible;
    }
    
    public void setText(String text)
    {
        passwordField.setText(text);
    }

    public String getText()
    {
        return new String(passwordField.getPassword());
    }
}
