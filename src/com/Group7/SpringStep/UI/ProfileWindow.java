package com.Group7.SpringStep.UI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class ProfileWindow extends JFrame implements ActionListener {
    JButton btnEditProf;
    JButton btnBack;
    JButton btnEditProfile;
    JButton btnEditUser;
    JButton btnEditEmail;
    JButton btnEditPass;

    public ProfileWindow() {
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

        // JButton btnEditProf;
        {
            // Put nested components here
            JPanel mainPanel = new JPanel(new GridBagLayout());
            mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 150, 0, 150));

            {
                JLabel profileLabel = new JLabel("User Profile");
                JLabel iconProfile = new JLabel();

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

                GridBagConstraints pw = new GridBagConstraints(); // pw stands for profileWindow
                pw.anchor = GridBagConstraints.CENTER;
                pw.insets = new Insets(5, 5, 0, 5);
                pw.weighty = 0;

                pw.gridwidth = 2; // to center the label
                mainPanel.add(profileLabel, pw);

                iconProfile.setIcon(
                        new ImageIcon("C:\\Users\\Nicole\\Desktop\\programs\\SpringStep\\res\\profileIcon.png"));
                pw.gridy = 1;
                pw.fill = GridBagConstraints.NONE;
                mainPanel.add(iconProfile, pw);
                // for the edit buttons
                Icon iconEditProf = new ImageIcon(
                        "C:\\Users\\Nicole\\Desktop\\programs\\SpringStep\\res\\editIcon.png");
                btnEditProfile = new JButton(iconEditProf);
                btnEditProfile.setBackground(Color.WHITE);
                pw.anchor = GridBagConstraints.FIRST_LINE_END;
                pw.gridx = 1;
                pw.gridy = 1;
                btnEditProfile.setVisible(false);
                btnEditProfile.setEnabled(false);
                mainPanel.add(btnEditProfile, pw);

                Icon iconEditUser = new ImageIcon(
                        "C:\\Users\\Nicole\\Desktop\\programs\\SpringStep\\res\\editIcon.png");
                btnEditUser = new JButton(iconEditUser);
                btnEditUser.setBackground(Color.WHITE);
                pw.anchor = GridBagConstraints.FIRST_LINE_START;
                pw.gridx = 2;
                pw.gridy = 2;
                btnEditUser.setVisible(false);
                btnEditUser.setEnabled(false);
                mainPanel.add(btnEditUser, pw);

                Icon iconEditEmail = new ImageIcon(
                        "C:\\Users\\Nicole\\Desktop\\programs\\SpringStep\\res\\editIcon.png");
                btnEditEmail = new JButton(iconEditEmail);
                btnEditEmail.setBackground(Color.WHITE);
                pw.anchor = GridBagConstraints.FIRST_LINE_START;
                pw.gridy = 3;
                btnEditEmail.setVisible(false);
                btnEditEmail.setEnabled(false);
                mainPanel.add(btnEditEmail, pw);

                Icon iconEditPass = new ImageIcon(
                        "C:\\Users\\Nicole\\Desktop\\programs\\SpringStep\\res\\editIcon.png");
                btnEditPass = new JButton(iconEditPass);
                btnEditPass.setBackground(Color.WHITE);
                pw.anchor = GridBagConstraints.FIRST_LINE_START;
                pw.gridy = 4;
                btnEditPass.setVisible(false);
                btnEditPass.setEnabled(false);
                mainPanel.add(btnEditPass, pw);

                // jlabels
                pw.gridwidth = 1; // para bumalik
                pw.anchor = GridBagConstraints.LINE_START;
                pw.weightx = 0;

                pw.gridx = 0;// para bumalik din
                pw.gridy = 2;
                mainPanel.add(new JLabel("Username: "), pw);

                pw.gridy = 3;
                mainPanel.add(new JLabel("Email: "), pw);

                pw.gridy = 4;
                mainPanel.add(new JLabel("Password: "), pw);

                pw.gridy = 5;
                mainPanel.add(new JLabel("Birthday: "), pw);

                // to make the textfields longer
                pw.fill = GridBagConstraints.HORIZONTAL;
                pw.gridx = 1;
                pw.gridy = 2;
                pw.weightx = 1;
                mainPanel.add(userName, pw);

                pw.gridy = 3;
                mainPanel.add(userEmail, pw);

                pw.gridy = 4;
                mainPanel.add(userPassword, pw);

                pw.gridy = 5;
                mainPanel.add(userBdate, pw);
                // buttons
                pw.gridx = 0;
                JPanel btnPanel = new JPanel(new FlowLayout()); // pinasok buttons here
                btnPanel.add(btnBack);
                btnPanel.add(btnEditProf);
                pw.anchor = GridBagConstraints.CENTER;
                pw.fill = GridBagConstraints.NONE;
                pw.gridy = 6;
                pw.gridwidth = 3;

                mainPanel.add(btnPanel, pw);

                // Put the call to add the nested components here
            }
            add(mainPanel);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnEditProf) {
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
