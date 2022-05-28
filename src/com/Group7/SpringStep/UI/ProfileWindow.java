package com.Group7.SpringStep.UI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.Group7.SpringStep.App;

public class ProfileWindow extends JFrame implements ActionListener 
{
    private JButton btnEditProf;
    private JButton btnBack;
    private JButton btnEditProfile;
    private JButton btnEditUser;
    private JButton btnEditEmail;
    private JButton btnEditPass;
    private ImageIcon editButtonIcon = new ImageIcon(App.resources.get("editIcon.png"));

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

                JTextField userName = new JTextField();
                JTextField userEmail = new JTextField();
                JTextField userPassword = new JTextField();
                JTextField userBdate = new JTextField();

                btnBack = new JButton("Back");
                btnBack.setBackground(new Color(215, 204, 195));
                btnBack.addActionListener(this);

                btnEditProf = new JButton("Edit Profile");
                btnEditProf.setBackground(new Color(135, 195, 193));
                btnEditProf.addActionListener(this);

                btnEditProfile = new JButton();
                btnEditUser = new JButton();
                btnEditEmail = new JButton();
                btnEditPass = new JButton();

                // for the edit buttons
                btnEditProfile = new JButton();
                btnEditProfile.setBackground(Color.WHITE);
                btnEditProfile.setEnabled(false);
                btnEditProfile.setVisible(false);
                btnEditProfile.setIcon(editButtonIcon);

                btnEditUser = new JButton();
                btnEditUser.setEnabled(false);
                btnEditUser.setVisible(false);
                btnEditUser.setBackground(Color.WHITE);
                btnEditUser.setIcon(editButtonIcon);

                btnEditEmail = new JButton();
                btnEditEmail.setEnabled(false);
                btnEditEmail.setVisible(false);
                btnEditEmail.setBackground(Color.WHITE);
                btnEditEmail.setIcon(editButtonIcon);

                btnEditPass = new JButton();
                btnEditPass.setEnabled(false);
                btnEditPass.setVisible(false);
                btnEditPass.setBackground(Color.WHITE);
                btnEditPass.setIcon(editButtonIcon);

                JPanel btnPanel = new JPanel(new FlowLayout()); // pinasok buttons here
                {
                    btnPanel.add(btnBack);
                    btnPanel.add(btnEditProf);
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
                mainPanel.add(btnEditProfile, profileWindowConstraints);

                profileWindowConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
                profileWindowConstraints.gridx = 2;
                profileWindowConstraints.gridy = 2;
                mainPanel.add(btnEditUser, profileWindowConstraints);

                profileWindowConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
                profileWindowConstraints.gridy = 3;
                mainPanel.add(btnEditEmail, profileWindowConstraints);

                profileWindowConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
                profileWindowConstraints.gridy = 4;
                mainPanel.add(btnEditPass, profileWindowConstraints);

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

                profileWindowConstraints.gridy = 5;
                mainPanel.add(new JLabel("Birthday: "), profileWindowConstraints);

                // to make the textfields longer
                profileWindowConstraints.gridx = 1;
                profileWindowConstraints.gridy = 2;
                profileWindowConstraints.weightx = 1;
                profileWindowConstraints.fill = GridBagConstraints.HORIZONTAL;
                mainPanel.add(userName, profileWindowConstraints);

                profileWindowConstraints.gridy = 3;
                mainPanel.add(userEmail, profileWindowConstraints);

                profileWindowConstraints.gridy = 4;
                mainPanel.add(userPassword, profileWindowConstraints);

                profileWindowConstraints.gridy = 5;
                mainPanel.add(userBdate, profileWindowConstraints);

                // buttons
                profileWindowConstraints.gridx = 0;
                profileWindowConstraints.gridy = 6;
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
        if (e.getSource() == btnEditProf) 
        {
            btnEditProfile.setVisible(true);
            btnEditProfile.setEnabled(true);

            btnEditUser.setVisible(true);
            btnEditUser.setEnabled(true);

            btnEditEmail.setVisible(true);
            btnEditEmail.setEnabled(true);

            btnEditPass.setVisible(true);
            btnEditPass.setEnabled(true);
        }
    }
}
