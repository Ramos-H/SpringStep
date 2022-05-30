package com.Group7.SpringStep.ui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PasswordTextField extends JPanel implements ActionListener
{
    private boolean passwordIsVisible = false;
    private JPasswordField passwordField;
    private JButton passwordVisibilityButton;

    public PasswordTextField()
    {
        setLayout(new BorderLayout());
        {
            passwordField = new JPasswordField();
            passwordField.setEchoChar('*');
            passwordVisibilityButton = new JButton("Show");
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
            passwordVisibilityButton.setText("Hide");
        } else 
        {
            passwordField.setEchoChar('*');
            passwordVisibilityButton.setText("Show");
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
