package com.Group7.SpringStep.UI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class SignUpWindow extends JFrame {
  public SignUpWindow() {
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
        btnBack.setBackground(new Color(215, 204, 195)); // rgb
        JButton btnSignUp = new JButton("Sign Up");
        btnSignUp.setBackground(new Color(135, 195, 193));

        GridBagConstraints signUpC = new GridBagConstraints();
        signUpC.anchor = GridBagConstraints.CENTER;
        signUpC.insets = new Insets(5, 5, 0, 5);
        signUpC.weighty = 0;

        signUpC.gridwidth = 2; // to center the label
        mainPanel.add(welcomeLabel, signUpC);

        signUpC.gridwidth = 1; // para bumalik
        signUpC.anchor = GridBagConstraints.LINE_START;
        signUpC.fill = GridBagConstraints.NONE;
        signUpC.weightx = 0;

        signUpC.gridy = 1;
        mainPanel.add(new JLabel("Username: "), signUpC);

        signUpC.gridy = 2;
        mainPanel.add(new JLabel("Email: "), signUpC);

        signUpC.gridy = 3;
        mainPanel.add(new JLabel("Password: "), signUpC);

        signUpC.gridy = 4;
        mainPanel.add(new JLabel("Birthday: "), signUpC);

        // to make the textfields longer
        signUpC.fill = GridBagConstraints.HORIZONTAL;
        signUpC.gridy = 1;
        signUpC.weightx = 1;
        mainPanel.add(userName, signUpC);

        signUpC.gridy = 2;
        mainPanel.add(userEmail, signUpC);

        signUpC.gridy = 3;
        mainPanel.add(userPassword, signUpC);

        signUpC.gridy = 4;
        mainPanel.add(userBdate, signUpC);

        JPanel btnPanel = new JPanel(new FlowLayout()); // pinasok buttons here
        btnPanel.add(btnBack);
        btnPanel.add(btnSignUp);
        signUpC.anchor = GridBagConstraints.CENTER;
        signUpC.fill = GridBagConstraints.NONE;
        signUpC.gridy = 5;
        signUpC.gridwidth = 2;

        mainPanel.add(btnPanel, signUpC);
      }
      add(mainPanel);
      // Put the call to add the nested components here
    }
  }
}