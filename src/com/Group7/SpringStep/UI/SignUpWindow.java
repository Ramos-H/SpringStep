package com.Group7.SpringStep.ui;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

import javax.swing.*;

import com.Group7.SpringStep.App;
import com.github.lgooddatepicker.components.*;

public class SignUpWindow extends JFrame implements ActionListener 
{
    private JButton backButton;
    private JButton signUpButton;
    private JTextField userNameField;
    private JTextField emailField;
    private JTextField passwordField;
    private DatePicker birthDateField;

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
                userNameField = new JTextField();
                emailField = new JTextField();
                passwordField = new JTextField();
                birthDateField = new DatePicker();

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
                mainPanel.add(userNameField, signUpWindowConstraints);

                signUpWindowConstraints.gridy = 3;
                mainPanel.add(emailField, signUpWindowConstraints);

                signUpWindowConstraints.gridy = 4;
                mainPanel.add(passwordField, signUpWindowConstraints);

                signUpWindowConstraints.gridy = 5;
                mainPanel.add(birthDateField, signUpWindowConstraints);

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
        else if(eventSource == signUpButton)
        {
            boolean hasInputErrors = false;
            String noInputErrorTitle = "Error: No Input";
            String noInputErrorMessage = "One of the fields in this window isn't filled!";

            String enteredUsername = userNameField.getText();
            String enteredEmail = emailField.getText();
            String enteredPassword = passwordField.getText();
            LocalDate enteredDate = birthDateField.getDate();

            if (enteredUsername == null || enteredUsername.equals("")) 
            {
                hasInputErrors = true;
                noInputErrorTitle = "Error: No username entered";
                noInputErrorMessage = "No username has been entered. Please enter your username and try again.";
            } 
            else if (enteredEmail == null || enteredEmail.equals("")) 
            {
                hasInputErrors = true;
                noInputErrorTitle = "Error: No email entered";
                noInputErrorMessage = "No email address has been entered. Please enter your email address and try again.";
            }
            else if (enteredPassword == null || enteredPassword.equals("")) 
            {
                hasInputErrors = true;
                noInputErrorTitle = "Error: No password entered";
                noInputErrorMessage = "No password has been entered. Please enter your password and try again.";
            }
            else if (enteredDate == null) 
            {
                hasInputErrors = true;
                noInputErrorTitle = "Error: No birth date entered";
                noInputErrorMessage = "No birth date has been entered. Please enter your birth date and try again.";
            }

            if (hasInputErrors) 
            {
                JOptionPane.showMessageDialog(this, noInputErrorMessage, noInputErrorTitle, JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}