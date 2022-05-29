package com.Group7.SpringStep.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.Group7.SpringStep.App;

public class SignUpWindow extends JFrame implements ActionListener 
{
    private JButton backButton;
    private JButton signUpButton;
    private JTextField userNameField;
    private JTextField emailField;
    private JTextField passwordField;
    private JTextField confirmPasswordField;

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
                confirmPasswordField = new JTextField();

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
                mainPanel.add(new JLabel("Confirm password: "), signUpWindowConstraints);

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
                mainPanel.add(confirmPasswordField, signUpWindowConstraints);

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
            
            String enteredUsername = userNameField.getText();
            String enteredEmail = emailField.getText();
            String enteredPassword = passwordField.getText();
            String enteredConfirmPassword = confirmPasswordField.getText();

            // If one of the input fields didn't have inputs when the user presses the sign up button
            boolean hasMissingInput = false;
            String noInputErrorTitle = "Error: No Input";
            String noInputErrorMessage = "One of the fields in this window isn't filled!";

            if (enteredUsername == null || enteredUsername.trim().equals("")) 
            {
                hasMissingInput = true;
                noInputErrorTitle = "Error: No username entered";
                noInputErrorMessage = "No username has been entered. \nPlease enter your username and try again.";
            } 
            else if (enteredEmail == null || enteredEmail.trim().equals("")) 
            {
                hasMissingInput = true;
                noInputErrorTitle = "Error: No email entered";
                noInputErrorMessage = "No email address has been entered. \nPlease enter your email address and try again.";
            }
            else if (enteredPassword == null || enteredPassword.trim().equals("")) 
            {
                hasMissingInput = true;
                noInputErrorTitle = "Error: No password entered";
                noInputErrorMessage = "No password has been entered. \nPlease enter your password and try again.";
            }
            else if (enteredConfirmPassword == null || enteredConfirmPassword.trim().equals("")) 
            {
                hasMissingInput = true;
                noInputErrorTitle = "Error: Password not confirmed";
                noInputErrorMessage = "You haven't confirmed your password. \nPlease enter your password again in the \"Confirm Cassword\" field and try again.";
            }

            if (hasMissingInput) 
            {
                JOptionPane.showMessageDialog(this, noInputErrorMessage, noInputErrorTitle, JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // If the password and confirm password inputs don't match
            if(!enteredPassword.equals(enteredConfirmPassword))
            {
                String message = "The text you entered in the \"Confirm Password\" field doesn't match what you entered for your password. \nPlease enter your password again in the confirm password field and try again.";
                JOptionPane.showMessageDialog(this, message, "Error: Password not confirmed",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            
        }
    }
}