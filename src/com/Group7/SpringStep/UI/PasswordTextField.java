package com.Group7.SpringStep.ui;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.Group7.SpringStep.App;

import net.coobird.thumbnailator.Thumbnails;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class PasswordTextField extends JPanel implements ActionListener
{
    private boolean passwordIsVisible = false;
    private JPasswordField passwordField;
    private JButton passwordVisibilityButton;
    private ImageIcon passwordHiddenIcon;
    private ImageIcon passwordShownIcon;

    public PasswordTextField()
    {
        
        Image passwordHiddenSourceImage = null;
        Image passwordShownSourceImage = null;
        try 
        {
            passwordHiddenSourceImage = Thumbnails.of(App.resources.get("Closed_Eye_Icon_256.png"))
                                                        .size(32, 32)
                                                        .asBufferedImage();
            passwordShownSourceImage = Thumbnails.of(App.resources.get("Opened_Eye_Icon_256.png"))
                                                        .size(32, 32)
                                                        .asBufferedImage();
        } catch (IOException e) 
        {
            e.printStackTrace();
        }

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
