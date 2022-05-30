package com.Group7.SpringStep.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.Group7.SpringStep.*;
import com.Group7.SpringStep.data.User;

public class ProfilePopup extends JPanel implements ActionListener 
{
    private JButton editProfileButton;
    private JButton backButton;
    private JButton editProfilePictureButton;
    private JButton editUsernameButton;
    private JButton editEmailButton;
    private JButton editPasswordButton;
    private ImageIcon editButtonIcon = new ImageIcon(App.resources.get("editIcon.png"));
    private boolean editMode = false;
    private JTextField usernameField;
    private JTextField emailField;
    private JTextField passwordField;
    private PopupContainer popupHandler;

    public ProfilePopup(PopupContainer popupContainer) 
    {
        popupHandler = popupContainer;
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(0, 150, 0, 150));
        {
            JLabel profileLabel = new JLabel("User Profile");
            JLabel iconProfile = new JLabel();
            iconProfile.setIcon(new ImageIcon(App.resources.get("profileIcon.png")));

            usernameField = new JTextField();
            emailField = new JTextField();
            passwordField = new JTextField();

            backButton = new JButton("Back");
            backButton.setBackground(new Color(215, 204, 195));
            backButton.addActionListener(this);

            editProfileButton = new JButton("Edit Profile");
            editProfileButton.setBackground(new Color(135, 195, 193));
            editProfileButton.addActionListener(this);

            // for the edit buttons
            editProfilePictureButton = new JButton();
            editProfilePictureButton.setBackground(Color.WHITE);
            editProfilePictureButton.setVisible(false);
            editProfilePictureButton.setIcon(editButtonIcon);

            editUsernameButton = new JButton();
            editUsernameButton.setVisible(false);
            editUsernameButton.setBackground(Color.WHITE);
            editUsernameButton.setIcon(editButtonIcon);

            editEmailButton = new JButton();
            editEmailButton.setVisible(false);
            editEmailButton.setBackground(Color.WHITE);
            editEmailButton.setIcon(editButtonIcon);

            editPasswordButton = new JButton();
            editPasswordButton.setVisible(false);
            editPasswordButton.setBackground(Color.WHITE);
            editPasswordButton.setIcon(editButtonIcon);

            JPanel btnPanel = new JPanel(new FlowLayout()); // pinasok buttons here
            {
                btnPanel.add(backButton);
                btnPanel.add(editProfileButton);
            }

            GridBagConstraints profileWindowConstraints = new GridBagConstraints();
            profileWindowConstraints.weighty = 0;
            profileWindowConstraints.anchor = GridBagConstraints.CENTER;
            profileWindowConstraints.insets = new Insets(5, 5, 0, 5);

            profileWindowConstraints.gridwidth = 2; // to center the label
            add(profileLabel, profileWindowConstraints);

            profileWindowConstraints.gridy = 1;
            profileWindowConstraints.fill = GridBagConstraints.NONE;
            add(iconProfile, profileWindowConstraints);

            // for the edit buttons
            profileWindowConstraints.gridx = 1;
            profileWindowConstraints.gridy = 1;
            profileWindowConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
            add(editProfilePictureButton, profileWindowConstraints);

            profileWindowConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
            profileWindowConstraints.gridx = 2;
            profileWindowConstraints.gridy = 2;
            add(editUsernameButton, profileWindowConstraints);

            profileWindowConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
            profileWindowConstraints.gridy = 3;
            add(editEmailButton, profileWindowConstraints);

            profileWindowConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
            profileWindowConstraints.gridy = 4;
            add(editPasswordButton, profileWindowConstraints);

            // jlabels
            profileWindowConstraints.weightx = 0;
            profileWindowConstraints.gridwidth = 1; // para bumalik
            profileWindowConstraints.anchor = GridBagConstraints.LINE_START;

            profileWindowConstraints.gridx = 0;// para bumalik din
            profileWindowConstraints.gridy = 2;
            add(new JLabel("Username: "), profileWindowConstraints);

            profileWindowConstraints.gridy = 3;
            add(new JLabel("Email: "), profileWindowConstraints);

            profileWindowConstraints.gridy = 4;
            add(new JLabel("Password: "), profileWindowConstraints);

            // to make the textfields longer
            profileWindowConstraints.gridx = 1;
            profileWindowConstraints.gridy = 2;
            profileWindowConstraints.weightx = 1;
            profileWindowConstraints.fill = GridBagConstraints.HORIZONTAL;
            add(usernameField, profileWindowConstraints);

            profileWindowConstraints.gridy = 3;
            add(emailField, profileWindowConstraints);

            profileWindowConstraints.gridy = 4;
            add(passwordField, profileWindowConstraints);

            // buttons
            profileWindowConstraints.gridx = 0;
            profileWindowConstraints.gridy = 5;
            profileWindowConstraints.gridwidth = 3;
            profileWindowConstraints.fill = GridBagConstraints.NONE;
            profileWindowConstraints.anchor = GridBagConstraints.CENTER;
            add(btnPanel, profileWindowConstraints);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        Object eventSource = e.getSource();
        if (eventSource == editProfileButton) {
            editMode = true;
            setEditButtonVisibility(true);
            editProfileButton.setText("Save changes");
            backButton.setText("Cancel edit");
        } else if (eventSource == backButton) {
            editMode = false;
            setEditButtonVisibility(false);
            editProfileButton.setText("Edit Profile");
            backButton.setText("Back");
            if(!editMode)
            {
                popupHandler.hidePopup();
            }
        }
    }

    private void setEditButtonVisibility(boolean isVisible) 
    {
        editProfilePictureButton.setVisible(isVisible);
        editUsernameButton.setVisible(isVisible);
        editEmailButton.setVisible(isVisible);
        editPasswordButton.setVisible(isVisible);
    }

    public void setUser(User currentUser)
    {
        usernameField.setText(currentUser.getUserName());
        emailField.setText(currentUser.getEmail());
        passwordField.setText(currentUser.getPassword());
    }
}
