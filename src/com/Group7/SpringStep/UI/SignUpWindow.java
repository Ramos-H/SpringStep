package com.Group7.SpringStep.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.Group7.SpringStep.App;

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
        setLayout(new GridLayout(1, 1));
        {
            // Initialize nested components here
            JPanel mainPanel = new JPanel(new GridBagLayout());
            mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 150, 0, 150));
            {
                JLabel welcomeLabel = new JLabel("Welcome to SpringStep!");
                JTextField userName = new JTextField();
                JTextField userEmail = new JTextField();
                JTextField userPassword = new JTextField();
                JTextField userBdate = new JTextField();

                JButton btnBack = new JButton("Back");
                btnBack.setBackground(new Color(215, 204, 195));

                JButton btnSignUp = new JButton("Sign Up");
                btnSignUp.setBackground(new Color(135, 195, 193));

                JLabel iconLogo = new JLabel();
                iconLogo.setIcon(
                        new ImageIcon(App.resources.get("SpringStep_Logo_Colored_Circle_200x200.png")));
                
                JPanel btnPanel = new JPanel(new FlowLayout()); // pinasok buttons here
                {
                    btnPanel.add(btnBack);
                    btnPanel.add(btnSignUp);
                }

                GridBagConstraints signUpC = new GridBagConstraints();
                signUpC.weighty = 0;
                signUpC.anchor = GridBagConstraints.CENTER;
                signUpC.insets = new Insets(5, 5, 0, 5);

                signUpC.gridwidth = 2; // to center the label
                mainPanel.add(welcomeLabel, signUpC);

                signUpC.gridy = 1;
                signUpC.fill = GridBagConstraints.NONE;
                mainPanel.add(iconLogo, signUpC);

                signUpC.weightx = 0;
                signUpC.gridwidth = 1; // para bumalik
                signUpC.anchor = GridBagConstraints.LINE_START;

                signUpC.gridy = 2;
                mainPanel.add(new JLabel("Username: "), signUpC);

                signUpC.gridy = 3;
                mainPanel.add(new JLabel("Email: "), signUpC);

                signUpC.gridy = 4;
                mainPanel.add(new JLabel("Password: "), signUpC);

                signUpC.gridy = 5;
                mainPanel.add(new JLabel("Birthday: "), signUpC);

                // to make the textfields longer
                signUpC.gridy = 2;
                signUpC.weightx = 1;
                signUpC.fill = GridBagConstraints.HORIZONTAL;
                mainPanel.add(userName, signUpC);

                signUpC.gridy = 3;
                mainPanel.add(userEmail, signUpC);

                signUpC.gridy = 4;
                mainPanel.add(userPassword, signUpC);

                signUpC.gridy = 5;
                mainPanel.add(userBdate, signUpC);

                signUpC.gridy = 6;
                signUpC.gridwidth = 2;
                signUpC.fill = GridBagConstraints.NONE;
                signUpC.anchor = GridBagConstraints.CENTER;
                mainPanel.add(btnPanel, signUpC);
            }
            // Put the call to add the nested components here
            add(mainPanel);
        }
    }
}