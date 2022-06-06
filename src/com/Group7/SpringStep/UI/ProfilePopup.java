package com.Group7.SpringStep.ui;

import javax.swing.*;
import javax.swing.undo.StateEditable;

import java.awt.*;
import java.awt.event.*;

import com.Group7.SpringStep.*;
import com.Group7.SpringStep.data.*;

public class ProfilePopup extends JPanel implements ActionListener 
{
    private JButton editProfileButton;
    private JButton backButton;
    private JButton editUsernameButton;
    private JButton editEmailButton;
    private JButton editPasswordButton;
    private boolean editMode = false;
    private JTextField usernameField;
    private JTextField emailField;
    private PasswordTextField passwordField;
    private PopupContainer popupHandler;
    private String oldUsername;
    private String oldEmail;
    private String oldPassword;
    private MainWindow mainWindow;
    private JButton logOutButton;
    private boolean passVerified = false;

    public ProfilePopup(PopupContainer popupContainer, MainWindow window) 
    {
        mainWindow = window;
        popupHandler = popupContainer;
        setLayout(new GridBagLayout());
        Utils.padJComponent(this, 0, 50, 0, 50);
        {
            JLabel profileLabel = new JLabel("User Profile");

            usernameField = new JTextField();
            usernameField.setEditable(false);

            emailField = new JTextField();
            emailField.setEditable(false);

            passwordField = new PasswordTextField();
            passwordField.setEditable(false);
            passwordField.setPassShowButtonVisibility(false);

            // for the edit buttons
            editUsernameButton = new JButton();
            editUsernameButton.addActionListener(this);
            editUsernameButton.setVisible(false);
            editUsernameButton.setBackground(Color.WHITE);
            
            editEmailButton = new JButton();
            editEmailButton.addActionListener(this);
            editEmailButton.setVisible(false);
            editEmailButton.setBackground(Color.WHITE);
            
            editPasswordButton = new JButton();
            editPasswordButton.addActionListener(this);
            editPasswordButton.setVisible(false);
            editPasswordButton.setBackground(Color.WHITE);
            
            Image editButtonImage = Utils.getScaledImage(App.resources.get("Edit_Button_(Pencil)_256.png"), 0.05f);
            if(editButtonImage != null)
            {
                editUsernameButton.setIcon(new ImageIcon(editButtonImage));
                editUsernameButton.setContentAreaFilled(false);
                editUsernameButton.setBorderPainted(false);
                editUsernameButton.setMargin(new Insets(0, 0, 0, 0));
                
                editEmailButton.setIcon(new ImageIcon(editButtonImage));
                editEmailButton.setContentAreaFilled(false);
                editEmailButton.setBorderPainted(false);
                editEmailButton.setMargin(new Insets(0, 0, 0, 0));

                editPasswordButton.setIcon(new ImageIcon(editButtonImage));
                editPasswordButton.setContentAreaFilled(false);
                editPasswordButton.setBorderPainted(false);
                editPasswordButton.setMargin(new Insets(0, 0, 0, 0));
            }
            
            JPanel buttonPanel = new JPanel(new GridBagLayout()); // pinasok buttons here
            Utils.padJComponent(buttonPanel, 0, 5, 0, 5);
            {
                backButton = new JButton("Back");
                backButton.setBackground(new Color(215, 204, 195));
                backButton.addActionListener(this);

                editProfileButton = new JButton("Edit Profile");
                editProfileButton.setBackground(new Color(135, 195, 193));
                editProfileButton.addActionListener(this);

                logOutButton = new JButton("Log out");
                logOutButton.addActionListener(this);

                GridBagConstraints buttonPanelConstraints = new GridBagConstraints();
                buttonPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
                buttonPanelConstraints.insets = new Insets(3, 3, 3, 3);
                buttonPanel.add(backButton, buttonPanelConstraints);

                buttonPanelConstraints.gridy++;
                buttonPanel.add(editProfileButton, buttonPanelConstraints);

                buttonPanelConstraints.gridy++;
                buttonPanelConstraints.gridx = 0;
                buttonPanelConstraints.gridwidth = 2;
                buttonPanel.add(logOutButton, buttonPanelConstraints);
            }

            GridBagConstraints profileWindowConstraints = new GridBagConstraints();
            profileWindowConstraints.anchor = GridBagConstraints.CENTER;
            profileWindowConstraints.insets = new Insets(5, 5, 0, 5);

            profileWindowConstraints.gridwidth = 3; // to center the label
            add(profileLabel, profileWindowConstraints);

            // jlabels
            profileWindowConstraints.gridwidth = 1; // para bumalik
            profileWindowConstraints.anchor = GridBagConstraints.WEST;

            profileWindowConstraints.gridx = 0;
            profileWindowConstraints.gridy = 1;
            add(new JLabel("Username: "), profileWindowConstraints);

            profileWindowConstraints.gridy++;
            add(new JLabel("Email: "), profileWindowConstraints);

            profileWindowConstraints.gridy++;
            add(new JLabel("Password: "), profileWindowConstraints);

            // to make the textfields longer
            profileWindowConstraints.gridx = 1;
            profileWindowConstraints.gridy = 1;
            profileWindowConstraints.weightx = 1;
            profileWindowConstraints.fill = GridBagConstraints.HORIZONTAL;
            add(usernameField, profileWindowConstraints);

            profileWindowConstraints.gridy++;
            add(emailField, profileWindowConstraints);

            profileWindowConstraints.gridy++;
            add(passwordField, profileWindowConstraints);

            // for the edit buttons
            profileWindowConstraints.gridx = 2;
            profileWindowConstraints.gridy = 1;
            profileWindowConstraints.weightx = 0;
            add(editUsernameButton, profileWindowConstraints);

            profileWindowConstraints.gridy++;
            add(editEmailButton, profileWindowConstraints);

            profileWindowConstraints.gridy++;
            add(editPasswordButton, profileWindowConstraints);

            // buttons
            profileWindowConstraints.gridx = 0;
            profileWindowConstraints.gridy++;
            profileWindowConstraints.gridwidth = 3;
            profileWindowConstraints.anchor = GridBagConstraints.CENTER;
            add(buttonPanel, profileWindowConstraints);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        Object eventSource = e.getSource();
        if (eventSource == backButton) 
        {
            if (!editMode) {
                popupHandler.hidePopup();
            }
            setEditMode(false);
        }
        else if (eventSource == logOutButton) 
        {
            int response = JOptionPane.showConfirmDialog(this, "Are you really sure you want to log out?", "Log out?",
                    JOptionPane.YES_NO_OPTION);
            if(response == JOptionPane.YES_OPTION)
            {
                mainWindow.logOut();
            }
        }
        else if (eventSource == editUsernameButton) 
        {
            JDialog dialog = new EditProfilePropertyDialog("username", usernameField, usernameField.getText());
            dialog.setVisible(true);
        } 
        else if (eventSource == editEmailButton) 
        {
            JDialog dialog = new EditProfilePropertyDialog("email", emailField, emailField.getText());
            dialog.setVisible(true);
        } 
        else if (eventSource == editPasswordButton) 
        {
            JDialog dialog = new EditPasswordDialog(passwordField);
            dialog.setVisible(true);
        } 
        else if (eventSource == editProfileButton) 
        {
            if (editMode)
            {
                String currentUsername = usernameField.getText();
                String currentEmail = emailField.getText();
                String currentPassword = passwordField.getText();

                if(!currentUsername.equals(oldUsername) || !currentEmail.equals(oldEmail) || !currentPassword.equals(oldPassword))
                {
                    int response = JOptionPane.showConfirmDialog(this,
                            "Are you really sure you want to save these changes?", "Save profile edits?",
                            JOptionPane.YES_NO_OPTION);
                    if(response == JOptionPane.YES_OPTION)
                    {
                        User editedUser = new User(currentUsername, currentEmail, currentPassword);

                        DataManager dataWriter = new DataManager();
                        try 
                        {
                            dataWriter.saveUserData(editedUser, true);
                            JOptionPane.showMessageDialog(null, "Your account has been successfully edited.", "Account has been successfully edited.",
                                    JOptionPane.INFORMATION_MESSAGE);
                            setEditMode(false);
                            mainWindow.setUser(editedUser);
                        } catch (Exception e1) 
                        {
                            String fileSaveErrorMessage = "An error has occured: File can't be accessed or can't be found.\nPlease try again";
                            JOptionPane.showMessageDialog(null, e1.getMessage(), "Save Record Unsuccessful",
                                    JOptionPane.ERROR_MESSAGE);
                            e1.printStackTrace();
                            return;
                        }
                    }
                }
            }
            else
            {
                String message = "To make sure it really is you, please enter your password before you make changes.\n";
                message += "We'll only ask you this once for this session.";
                while (!passVerified)
                {
                    String response = JOptionPane.showInputDialog(this, message);
                    if (Utils.isTextEmpty(response)) {
                        JOptionPane.showMessageDialog(null, "Please enter your password",
                                "Password confirmation unsuccessful",
                                JOptionPane.ERROR_MESSAGE);
                    } else if (!response.equals(passwordField.getText())) {
                        JOptionPane.showMessageDialog(null, "The password you entered is incorrect. \nPlease try again",
                                "Password confirmation unsuccessful",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        passVerified = true;
                    }
                }
                
                setEditMode(true);
            }
        }
    }
    
    /**
     * @param newMode the editMode to set
     */
    public void setEditMode(boolean newMode) 
    {
        editMode = newMode;
        setEditButtonVisibility(editMode);
        if(editMode)
        {
            editProfileButton.setText("Save changes");
            backButton.setText("Cancel edit");
        }
        else 
        {
            editProfileButton.setText("Edit Profile");
            backButton.setText("Back");
        }
    }

    private void setEditButtonVisibility(boolean isVisible) 
    {
        editUsernameButton.setVisible(isVisible);
        editEmailButton.setVisible(isVisible);
        editPasswordButton.setVisible(isVisible);
    }

    public void setUser(User currentUser)
    {
        String userName = currentUser.getUserName();
        usernameField.setText(userName);
        oldUsername = userName;

        String email = currentUser.getEmail();
        emailField.setText(email);
        oldEmail = email;

        String password = currentUser.getPassword();
        passwordField.setText(password);
        oldPassword = password;
    }
}
