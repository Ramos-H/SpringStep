package com.Group7.SpringStep.ui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import com.Group7.SpringStep.*;

/** A custom text field for getting passwords. Basically a JPasswordField bundled with a show/hide password button */
public class PasswordTextField extends JPanel implements ActionListener
{
    private boolean passwordIsVisible = false;
    private JPasswordField passwordField;
    private JButton passwordVisibilityButton;
    private ImageIcon passwordHiddenIcon;
    private ImageIcon passwordShownIcon;

    ///////////////////////////////////////////////// CONSTRUCTORS /////////////////////////////////////////////////
    public PasswordTextField()
    {
        setOpaque(false);
        Image passwordHiddenSourceImage = Utils.getScaledImage(App.resources.get("Closed_Eye_Icon_256.png"), 0.10f);
        if (passwordHiddenSourceImage != null) { passwordHiddenIcon = new ImageIcon(passwordHiddenSourceImage); }
        
        Image passwordShownSourceImage = Utils.getScaledImage(App.resources.get("Opened_Eye_Icon_256.png"), 0.10f);
        if (passwordShownSourceImage != null) { passwordShownIcon = new ImageIcon(passwordShownSourceImage); }

        setLayout(new BorderLayout());
        {
            passwordField = new JPasswordField();
            passwordField.setEchoChar('*'); // Hide the text by default
            passwordVisibilityButton = new JButton();
            Utils.setButtonIcon(passwordVisibilityButton, passwordHiddenIcon);
            passwordVisibilityButton.setMargin(new Insets(0, 0, 0, 0));
            passwordVisibilityButton.addActionListener(this);
            passwordVisibilityButton.setFocusable(false);

            add(passwordField, BorderLayout.CENTER);
            add(passwordVisibilityButton, BorderLayout.LINE_END);
        }
    }

    ///////////////////////////////////////////////// GETTERS /////////////////////////////////////////////////
    public String getText() { return new String(passwordField.getPassword()); }
    public boolean getEditable() { return passwordField.isEditable(); }

    /**
     * Checks if the password show/hide button is currently visible
     * @return {@code true} if the password show/hide button is visible. {@code false} if otherwise
     */
    public boolean getPassShowButtonVisibility() { return passwordVisibilityButton.isVisible(); }
    
    ///////////////////////////////////////////////// SETTERS /////////////////////////////////////////////////
    public void setText(String text) { passwordField.setText(text); }
    public void setEditable(boolean editable) { passwordField.setEditable(editable); }

    /**
     * Determines whether the password show/hide button is visible
     * @param visible Whether the button should be visible
     */
    public void setPassShowButtonVisibility(boolean visible) { passwordVisibilityButton.setVisible(visible); }

    ///////////////////////////////////////////////// EVENT HANDLERS /////////////////////////////////////////////////
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (!passwordIsVisible) 
        {
            passwordField.setEchoChar((char) 0); // Show the text
            passwordVisibilityButton.setIcon(passwordShownIcon);
        } else 
        {
            passwordField.setEchoChar('*'); // Hide the text
            passwordVisibilityButton.setIcon(passwordHiddenIcon);
        }
        passwordIsVisible = !passwordIsVisible;
    }
}