package com.Group7.SpringStep.ui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import com.Group7.SpringStep.*;

/** Dialog window specifally made for to ask for the user's password for security purposes */
/* I actually just made this because JOptionPane.showInputDialog shows the user's password, which is undesirable 
   for this purpose */
public class PasswordSecurityPrompterDialog extends JDialog implements ActionListener
{
    private JLabel icon = new JLabel();
    private JTextArea messageLabel;
    private PasswordTextField passField;
    private RoundedButton submitButton = new RoundedButton("Confirm");

    public static int RESPONSE_SUBMITTED = 0;
    public static int RESPONSE_CANCELLED = 1;

    private int value = RESPONSE_CANCELLED;

    ///////////////////////////////////////////////// CONSTRUCTORS /////////////////////////////////////////////////
    public PasswordSecurityPrompterDialog()
    {
        setModal(true);
        setIconImage(App.springStepImage);
        setLayout(new GridBagLayout());
        {
            submitButton.addActionListener(this);
            submitButton.setBackground(new Color(135, 195, 193));

            passField = new PasswordTextField();
            passField.setPassShowButtonVisibility(false);

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
            add(passField, gbc);

            gbc.gridy = 2;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.NONE;
            add(submitButton, gbc);
        }
    }

    ///////////////////////////////////////////////// GETTERS /////////////////////////////////////////////////
    public String getText() { return passField.getText(); }

    ///////////////////////////////////////////////// EVENT HANDLERS /////////////////////////////////////////////////
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == submitButton)
        {
            value = RESPONSE_SUBMITTED;
            setVisible(false);
        }
    }
    
    ///////////////////////////////////////////////// INSTANCE METHODS /////////////////////////////////////////////////
    /** Resets the dialog window to its default state */
    // Should have named this "reset" instead
    public void clear()
    {
        passField.setText("");
        messageLabel.setText("");
        setTitle("");
        icon.setIcon(null);
        value = RESPONSE_CANCELLED;
    }

    /**
     * Shows the dialog window with the given parameter arguments, which blocks the current thread until it has 
     * either received a response from the user, or was closed by the user
     * @param message The message shown in the dialog window
     * @param title The title of the dialog window
     * @param defaultText The default text in the dialog window password field
     * @param newIcon The icon to show in the dialog window
     * @return {@code PasswordSecurityPrompterDialog.RESPONSE_SUBMITTED} if the user has pressed the submit button.
     * {@code PasswordSecurityPrompterDialog.RESPONSE_CANCELLED} if the user closes the window.
     */
    public int showDialog(String message, String title, String defaultText, ImageIcon newIcon)
    {
        clear(); // Reset the window to its default state first

        // Customize the window with the given arguments, if they are provided
        if(message != null) { messageLabel.setText(message); }
        if(message != null) { setTitle(title); }
        if(defaultText != null) { passField.setText(defaultText); }
        if(newIcon != null) { icon.setIcon(newIcon); }

        // Make the dialog window 20 percent of the width and height of the screen, then center it
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Utils.scaleByPercentage(this, screenSize, 20, 20);
        Utils.centerByRect(this, (int) screenSize.getWidth(), (int) screenSize.getHeight());

        setVisible(true);
        
        // Block the current thread until a response has been received
        while (isVisible()) { }
        
        return value;
    }
}
