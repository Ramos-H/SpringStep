package com.Group7.SpringStep.ui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import com.Group7.SpringStep.*;

public class BoardNamePrompterDialog extends JDialog implements ActionListener
{
    private JLabel icon = new JLabel();
    private JTextArea messageLabel;
    private JTextField nameField = new JTextField();
    private RoundedButton submitButton = new RoundedButton("Confirm");

    public static int RESPONSE_SUBMITTED = 0;
    public static int RESPONSE_CANCELLED = 1;

    private int value = RESPONSE_CANCELLED;

    public BoardNamePrompterDialog()
    {
        setModal(true);
        setIconImage(App.springStepImage);
        setLayout(new GridBagLayout());
        {
            submitButton.addActionListener(this);
            submitButton.setBackground(new Color(135, 195, 193));

            messageLabel = new JTextArea();
            messageLabel.setLineWrap(true);
            messageLabel.setBorder(null);
            messageLabel.setWrapStyleWord(true);
            messageLabel.setEnabled(false);
            messageLabel.setDisabledTextColor(Color.BLACK);
            messageLabel.setOpaque(false);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.weighty = 0.2;
            gbc.gridheight = 2;
            add(icon, gbc);

            gbc.weightx = 0.8;
            gbc.gridheight = 1;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = new Insets(5, 5, 5, 5);

            gbc.gridx = 1;
            add(messageLabel, gbc);

            gbc.gridy = 1;
            add(nameField, gbc);

            gbc.gridy = 2;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.NONE;
            add(submitButton, gbc);
        }
    }
    
    public void clear()
    {
        nameField.setText("");
        messageLabel.setText("");
        setTitle("");
        icon.setIcon(null);
        value = RESPONSE_CANCELLED;
    }

    public int showDialog(String message, String title, String defaultText, ImageIcon newIcon)
    {
        clear();
        if(message != null) { messageLabel.setText(message); }
        if(message != null) { setTitle(title); }
        if(defaultText != null) { nameField.setText(defaultText); }
        if(newIcon != null) { icon.setIcon(newIcon); }
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Utils.scaleByPercentage(this, screenSize, 20, 20);
        Utils.centerByRect(this, (int) screenSize.getWidth(), (int) screenSize.getHeight());
        setVisible(true);
        
        while (isVisible()) { }
        
        return value;
    }
    
    public String getText()
    {
        return nameField.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == submitButton)
        {
            value = RESPONSE_SUBMITTED;
            setVisible(false);
        }
    }
}
