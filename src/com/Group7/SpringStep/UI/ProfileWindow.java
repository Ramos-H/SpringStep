package com.Group7.SpringStep.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.Group7.SpringStep.App;

public class ProfileWindow extends JFrame implements ActionListener 
{
    private JButton editProfileButton;
    private JButton btnBack;
    private JButton editProfilePictureButton;
    private JButton editUsernameButton;
    private JButton editEmailButton;
    private JButton editPasswordButton;
    private ImageIcon editButtonIcon = new ImageIcon(App.resources.get("editIcon.png"));
    private boolean editMode = false;

    public ProfileWindow() 
    {
        // Set window parameters first
        setTitle("SpringStep - My Profile");

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
            // Put nested components here
            JPanel mainPanel = new JPanel(new GridBagLayout());
            mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 150, 0, 150));
            {
                JLabel profileLabel = new JLabel("User Profile");
                JLabel iconProfile = new JLabel();
                iconProfile.setIcon(new ImageIcon(App.resources.get("profileIcon.png")));

                JTextField nameField = new JTextField();
                JTextField emailField = new JTextField();
                JTextField passwordField = new JTextField();

                btnBack = new JButton("Back");
                btnBack.setBackground(new Color(215, 204, 195));
                btnBack.addActionListener(this);

                editProfileButton = new JButton("Edit Profile");
                editProfileButton.setBackground(new Color(135, 195, 193));
                editProfileButton.addActionListener(this);

                // for the edit buttons
                editProfilePictureButton = new JButton();
                editProfilePictureButton.setBackground(Color.WHITE);
                editProfilePictureButton.setEnabled(false);
                editProfilePictureButton.setVisible(false);
                editProfilePictureButton.setIcon(editButtonIcon);

                editUsernameButton = new JButton();
                editUsernameButton.setEnabled(false);
                editUsernameButton.setVisible(false);
                editUsernameButton.setBackground(Color.WHITE);
                editUsernameButton.setIcon(editButtonIcon);

                editEmailButton = new JButton();
                editEmailButton.setEnabled(false);
                editEmailButton.setVisible(false);
                editEmailButton.setBackground(Color.WHITE);
                editEmailButton.setIcon(editButtonIcon);

                editPasswordButton = new JButton();
                editPasswordButton.setEnabled(false);
                editPasswordButton.setVisible(false);
                editPasswordButton.setBackground(Color.WHITE);
                editPasswordButton.setIcon(editButtonIcon);

                JPanel btnPanel = new JPanel(new FlowLayout()); // pinasok buttons here
                {
                    btnPanel.add(btnBack);
                    btnPanel.add(editProfileButton);
                }

                GridBagConstraints profileWindowConstraints = new GridBagConstraints();
                profileWindowConstraints.weighty = 0;
                profileWindowConstraints.anchor = GridBagConstraints.CENTER;
                profileWindowConstraints.insets = new Insets(5, 5, 0, 5);

                profileWindowConstraints.gridwidth = 2; // to center the label
                mainPanel.add(profileLabel, profileWindowConstraints);

                profileWindowConstraints.gridy = 1;
                profileWindowConstraints.fill = GridBagConstraints.NONE;
                mainPanel.add(iconProfile, profileWindowConstraints);

                // for the edit buttons
                profileWindowConstraints.gridx = 1;
                profileWindowConstraints.gridy = 1;
                profileWindowConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
                mainPanel.add(editProfilePictureButton, profileWindowConstraints);

                profileWindowConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
                profileWindowConstraints.gridx = 2;
                profileWindowConstraints.gridy = 2;
                mainPanel.add(editUsernameButton, profileWindowConstraints);

                profileWindowConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
                profileWindowConstraints.gridy = 3;
                mainPanel.add(editEmailButton, profileWindowConstraints);

                profileWindowConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
                profileWindowConstraints.gridy = 4;
                mainPanel.add(editPasswordButton, profileWindowConstraints);

                // jlabels
                profileWindowConstraints.weightx = 0;
                profileWindowConstraints.gridwidth = 1; // para bumalik
                profileWindowConstraints.anchor = GridBagConstraints.LINE_START;

                profileWindowConstraints.gridx = 0;// para bumalik din
                profileWindowConstraints.gridy = 2;
                mainPanel.add(new JLabel("Username: "), profileWindowConstraints);

                profileWindowConstraints.gridy = 3;
                mainPanel.add(new JLabel("Email: "), profileWindowConstraints);

                profileWindowConstraints.gridy = 4;
                mainPanel.add(new JLabel("Password: "), profileWindowConstraints);

                // to make the textfields longer
                profileWindowConstraints.gridx = 1;
                profileWindowConstraints.gridy = 2;
                profileWindowConstraints.weightx = 1;
                profileWindowConstraints.fill = GridBagConstraints.HORIZONTAL;
                mainPanel.add(nameField, profileWindowConstraints);

                profileWindowConstraints.gridy = 3;
                mainPanel.add(emailField, profileWindowConstraints);

                profileWindowConstraints.gridy = 4;
                mainPanel.add(passwordField, profileWindowConstraints);

                // buttons
                profileWindowConstraints.gridx = 0;
                profileWindowConstraints.gridy = 5;
                profileWindowConstraints.gridwidth = 3;
                profileWindowConstraints.fill = GridBagConstraints.NONE;
                profileWindowConstraints.anchor = GridBagConstraints.CENTER;
                mainPanel.add(btnPanel, profileWindowConstraints);
            }
            // Put the call to add the nested components here
            add(mainPanel);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == editProfileButton) 
        {
            editMode = !editMode;
            editProfilePictureButton.setVisible(editMode);
            editProfilePictureButton.setEnabled(editMode);

            editUsernameButton.setVisible(editMode);
            editUsernameButton.setEnabled(editMode);

            editEmailButton.setVisible(editMode);
            editEmailButton.setEnabled(editMode);

            editPasswordButton.setVisible(editMode);
            editPasswordButton.setEnabled(editMode);
        }
    }
}
