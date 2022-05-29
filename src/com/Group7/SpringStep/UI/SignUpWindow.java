package com.Group7.SpringStep.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.Group7.SpringStep.App;

public class SignUpWindow extends JFrame implements ActionListener 
{
    private JButton backButton;
    private JButton signUpButton;

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

                backButton = new JButton("Back");
                backButton.addActionListener(this);
                backButton.setBackground(new Color(215, 204, 195));

                signUpButton = new JButton("Sign Up");
                signUpButton.addActionListener(this);
                signUpButton.setBackground(new Color(135, 195, 193));

                JLabel iconLogo = new JLabel();
                iconLogo.setIcon(
                        new ImageIcon(App.resources.get("SpringStep_Logo_Colored_Circle_200x200.png")));
                
                JPanel buttonPanel = new JPanel(new FlowLayout()); // pinasok buttons here
                {
                    buttonPanel.add(backButton);
                    buttonPanel.add(signUpButton);
                }

                GridBagConstraints signUpWindowConstraints = new GridBagConstraints();
                signUpWindowConstraints.weighty = 0;
                signUpWindowConstraints.anchor = GridBagConstraints.CENTER;
                signUpWindowConstraints.insets = new Insets(5, 5, 0, 5);

                signUpWindowConstraints.gridwidth = 2; // to center the label
                mainPanel.add(welcomeLabel, signUpWindowConstraints);

                signUpWindowConstraints.gridy = 1;
                signUpWindowConstraints.fill = GridBagConstraints.NONE;
                mainPanel.add(iconLogo, signUpWindowConstraints);

                signUpWindowConstraints.weightx = 0;
                signUpWindowConstraints.gridwidth = 1; // para bumalik
                signUpWindowConstraints.anchor = GridBagConstraints.LINE_START;

                signUpWindowConstraints.gridy = 2;
                mainPanel.add(new JLabel("Username: "), signUpWindowConstraints);

                signUpWindowConstraints.gridy = 3;
                mainPanel.add(new JLabel("Email: "), signUpWindowConstraints);

                signUpWindowConstraints.gridy = 4;
                mainPanel.add(new JLabel("Password: "), signUpWindowConstraints);

                signUpWindowConstraints.gridy = 5;
                mainPanel.add(new JLabel("Birthday: "), signUpWindowConstraints);

                // to make the textfields longer
                signUpWindowConstraints.gridy = 2;
                signUpWindowConstraints.weightx = 1;
                signUpWindowConstraints.fill = GridBagConstraints.HORIZONTAL;
                mainPanel.add(userName, signUpWindowConstraints);

                signUpWindowConstraints.gridy = 3;
                mainPanel.add(userEmail, signUpWindowConstraints);

                signUpWindowConstraints.gridy = 4;
                mainPanel.add(userPassword, signUpWindowConstraints);

                signUpWindowConstraints.gridy = 5;
                mainPanel.add(userBdate, signUpWindowConstraints);

                signUpWindowConstraints.gridy = 6;
                signUpWindowConstraints.gridwidth = 2;
                signUpWindowConstraints.fill = GridBagConstraints.NONE;
                signUpWindowConstraints.anchor = GridBagConstraints.CENTER;
                mainPanel.add(buttonPanel, signUpWindowConstraints);
            }
            // Put the call to add the nested components here
            add(mainPanel);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        Object eventSource = e.getSource();
        if(eventSource == backButton)
        {
            new LoginWindow().setVisible(true);
            dispose();
        }
    }
}