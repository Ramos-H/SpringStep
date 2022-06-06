package com.Group7.SpringStep.ui;

import javax.swing.*;

import java.nio.file.*;

import java.awt.*;
import java.awt.event.*;

import com.Group7.SpringStep.*;
import com.Group7.SpringStep.data.*;

public class LoginWindow extends JFrame implements ActionListener
{
    private JTextField userNameField;
    private PasswordTextField passwordField;
    private JCheckBox rememberPasswordCheckBox;
    private RoundedButton signUpButton;
    private RoundedButton logInButton;

    public LoginWindow()
    {
        // Set window parameters first
        setTitle("SpringStep - Login");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

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
                userNameField = new JTextField();
                passwordField = new PasswordTextField();
                rememberPasswordCheckBox = new JCheckBox("Remember password");

                signUpButton = new RoundedButton("Sign Up");
                signUpButton.addActionListener(this);
                signUpButton.setBackground(new Color(215, 204, 195));

                logInButton = new RoundedButton("Log In");
                logInButton.addActionListener(this);
                logInButton.setBackground(new Color(135, 195, 193));

                JLabel iconLogo = new JLabel();
                Image scaledLogoImage = Utils.getScaledImage(App.resources.get("SpringStep_Logo.png"), 0.10);
                if(scaledLogoImage != null)
                {
                    iconLogo.setIcon(new ImageIcon(scaledLogoImage));
                }
                
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

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        Object eventSource = e.getSource();
        if(eventSource == logInButton)
        {
            boolean hasInputErrors = false;
            String noInputErrorTitle = "Error: No Input";
            String noInputErrorMessage = "One of the fields in this window isn't filled!";

            String enteredUsername = userNameField.getText();
            String enteredPassword = passwordField.getText();
            if (Utils.isTextEmpty(enteredUsername)) {
                hasInputErrors = true;
                noInputErrorTitle = "Error: No username entered";
                noInputErrorMessage = "No username has been entered. \nPlease enter your username and try again.";
            } else if (Utils.isTextEmpty(enteredPassword)) {
                hasInputErrors = true;
                noInputErrorTitle = "Error: No password entered";
                noInputErrorMessage = "No password has been entered. \nPlease enter your password and try again.";
            }

            if (hasInputErrors) 
            {
                JOptionPane.showMessageDialog(this, noInputErrorMessage, noInputErrorTitle, JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Dynamically construct the output file path
            String userHome = System.getProperty("user.home");
            String completeFileName =  enteredUsername + ".csv";
            Path filePath = Paths.get(userHome, "Documents", "SpringStep", "users", completeFileName);
            
            // Try writing the output to the file
            // If writing fails for whatever reason, display the exception
            try
            {
                boolean fileExists = Files.exists(filePath);
                boolean fileDoesntExist = Files.notExists(filePath);
                boolean fileUnverifiable = !fileExists && !fileDoesntExist;
                boolean fileSurelyDoesntExists = !fileExists && fileDoesntExist;
                if(fileSurelyDoesntExists)
                {
                    String message = String.format(
                            "There is no account found with the username \"%s\". \nCheck the username entered and try again.",
                            enteredUsername);
                    String title = String.format("Error: No accounts found with the username \"%s\"", enteredUsername);
                    JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
                    return;
                }

                DataManager dataManager = new DataManager();
                User currentUser = dataManager.readUser(filePath);

                if(!currentUser.getPassword().equals(enteredPassword))
                {
                    String message = "The password you entered is incorrect. Please re-enter your password and try again.";
                    String title = "Error: Incorrect password";
                    JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(this, "Logged in successfully", "Successful log in",
                        JOptionPane.INFORMATION_MESSAGE);
                
                MainWindow nextWindow = new MainWindow();
                nextWindow.setUser(currentUser);
                Utils.moveToNewWindow(this, nextWindow);
            } catch (Exception e1) 
            {
                String fileSaveErrorMessage = "An error has occured: File can't be accessed or can't be found.\nPlease try again";
                JOptionPane.showMessageDialog(this, e1.getMessage(), "Save Record Unsuccessful",
                        JOptionPane.ERROR_MESSAGE);
                e1.printStackTrace();
                return;
            }
        }
        else if(eventSource == signUpButton)
        {
            Utils.moveToNewWindow(this, new SignUpWindow());
        }
    }
}