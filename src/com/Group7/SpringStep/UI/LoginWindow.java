package com.Group7.SpringStep.UI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.Group7.SpringStep.App;

public class LoginWindow extends JFrame
{
    public LoginWindow()
    {
        // Set window parameters first
        setTitle("SpringStep - Login");

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
            JPanel mainPanel = new JPanel(new GridBagLayout());
            mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 150, 0, 150));
            {
                JLabel welcomeLabel = new JLabel("Log in to SpringStep!");
                JTextField userNameField = new JTextField();
                JTextField passwordField = new JTextField();
                JCheckBox rememberPasswordCheckBox = new JCheckBox("Remember password");

                JButton signUpButton = new JButton("Sign Up");
                signUpButton.setBackground(new Color(215, 204, 195));

                JButton logInButton = new JButton("Log In");
                logInButton.setBackground(new Color(135, 195, 193));

                JLabel iconLogo = new JLabel();
                iconLogo.setIcon(new ImageIcon(App.resources.get("SpringStep_Logo_Colored_Circle_200x200.png")));
                
                JPanel buttonPanel = new JPanel(new FlowLayout());
                {
                    buttonPanel.add(signUpButton);
                    buttonPanel.add(logInButton);
                }

                GridBagConstraints logInWindowConstraints = new GridBagConstraints();
                logInWindowConstraints.weightx = 0;
                logInWindowConstraints.weighty = 0;
                logInWindowConstraints.fill = GridBagConstraints.NONE;
                logInWindowConstraints.anchor = GridBagConstraints.CENTER;
                logInWindowConstraints.insets = new Insets(5, 5, 0, 5);

                logInWindowConstraints.gridwidth = 2;
                mainPanel.add(welcomeLabel, logInWindowConstraints);

                logInWindowConstraints.gridy = 1;
                mainPanel.add(iconLogo, logInWindowConstraints);

                logInWindowConstraints.gridwidth = 1;
                logInWindowConstraints.anchor = GridBagConstraints.LINE_START;

                logInWindowConstraints.gridy = 2;
                mainPanel.add(new JLabel("Username: "), logInWindowConstraints);

                logInWindowConstraints.gridy = 3;
                mainPanel.add(new JLabel("Password: "), logInWindowConstraints);

                logInWindowConstraints.gridx = 1;
                logInWindowConstraints.gridy = 2;
                logInWindowConstraints.weightx = 1;
                logInWindowConstraints.fill = GridBagConstraints.HORIZONTAL;
                mainPanel.add(userNameField, logInWindowConstraints);

                logInWindowConstraints.gridy = 3;
                mainPanel.add(passwordField, logInWindowConstraints);
                
                logInWindowConstraints.gridx = 0;
                logInWindowConstraints.gridwidth = 2;
                logInWindowConstraints.fill = GridBagConstraints.NONE;
                logInWindowConstraints.anchor = GridBagConstraints.CENTER;

                logInWindowConstraints.gridy = 4;
                mainPanel.add(rememberPasswordCheckBox, logInWindowConstraints);

                logInWindowConstraints.gridy = 5;
                mainPanel.add(buttonPanel, logInWindowConstraints);
            }
            // Put the call to add the nested components here
            add(mainPanel);
        }
    }
}