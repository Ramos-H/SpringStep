package com.Group7.SpringStep.ui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import com.Group7.SpringStep.*;

/** Dialog window specifically made for editing the user's password */
public class EditPasswordDialog extends JDialog implements ActionListener
{
    private JLabel newPasswordLabel;
    private JLabel confirmPasswordLabel;
    private RoundedButton doneButton;
    private RoundedButton cancelButton;
    private PasswordTextField fieldToModify;
    private PasswordTextField newPasswordField;
    private PasswordTextField confirmPasswordField;

    ///////////////////////////////////////////////// CONSTRUCTORS /////////////////////////////////////////////////
    public EditPasswordDialog(PasswordTextField textField)
    {
        fieldToModify = textField;
        setModal(true);
        setTitle("Enter new password");
        setIconImage(App.springStepImage);

        // Scale the dialog to 30 percent of the width and height of the screen
        Rectangle screenSize = getGraphicsConfiguration().getBounds();
        float widthScale = 30f;
        float heightScale = 30f;
        int width = Math.round(screenSize.width * (widthScale / 100f));
        int height = Math.round(screenSize.height * (heightScale / 100f));
        setSize(width, height);

        // Center the dialog's location on the screen
        int x = Math.round(screenSize.width / 2 - getWidth() / 2);
        int y = Math.round(screenSize.height / 2 - getHeight() / 2);
        setLocation(x, y);

        setLayout(new GridLayout(1, 1));
        {
            JPanel padPanel = new JPanel();
            Utils.padJComponent(padPanel, 0, 30, 0, 30);
            padPanel.setLayout(new GridBagLayout());
            {
                JPanel buttonPanel = new JPanel(new FlowLayout());
                {
                    cancelButton = new RoundedButton("Cancel");
                    cancelButton.addActionListener(this);
                    cancelButton.setBackground(new Color(215, 204, 195));

                    doneButton = new RoundedButton("Done");
                    doneButton.addActionListener(this);
                    doneButton.setBackground(new Color(135, 195, 193));

                    buttonPanel.add(cancelButton);
                    buttonPanel.add(doneButton);
                }
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.anchor = GridBagConstraints.WEST;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weightx = 1;
                padPanel.add(new JLabel("New password:"), gbc);

                gbc.gridy = 1;
                newPasswordField = new PasswordTextField();
                padPanel.add(newPasswordField, gbc);

                gbc.gridy = 2;
                padPanel.add(new JLabel("Confirm password:"), gbc);

                gbc.gridy = 3;
                confirmPasswordField = new PasswordTextField();
                padPanel.add(confirmPasswordField, gbc);

                gbc.gridy = 5;
                gbc.gridwidth = 2;
                padPanel.add(buttonPanel, gbc);
            }
            add(padPanel);
        }
    }

    ///////////////////////////////////////////////// EVENT HANDLERS /////////////////////////////////////////////////
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        Object eventSource = e.getSource();
        if(eventSource == doneButton)
        {
            String enteredPasswordValue = newPasswordField.getText();
            String enteredConfirmValue = confirmPasswordField.getText();
            boolean hasMissingInput = false; // Determines whether to show the error message or not

            // Default values just in case I forgot to set them
            String noInputErrorTitle = "Error: No Input";
            String noInputErrorMessage = "One of the fields in this window isn't filled!";

            if (Utils.isTextEmpty(enteredPasswordValue)) 
            {
                hasMissingInput = true;
                noInputErrorTitle = "Error: No new password provided.";
                noInputErrorMessage = "No new password was entered. \nPlease enter the new password and try again.";
            }
            else if (Utils.isTextEmpty(enteredConfirmValue))
            {
                hasMissingInput = true;
                noInputErrorTitle = "Error: Password not confirmed.";
                noInputErrorMessage = "You haven't confirmed your password. \nPlease enter your new password again in the \"Confirm password\" field and try again.";
            }

            // If the dialog has *any* missing input, show the error message
            if (hasMissingInput)
            {
                JOptionPane.showMessageDialog(this, noInputErrorMessage, noInputErrorTitle, JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!enteredPasswordValue.equals(enteredConfirmValue))
            {
                hasMissingInput = true;
                String message = "The text you entered in the \"Confirm password\" field doesn't match what you entered for your new password.\n";
                message += "Please enter your password again in the confirm password field and try again.";
                JOptionPane.showMessageDialog(this, message, "Error: Password not confirmed.", JOptionPane.ERROR_MESSAGE);
                return;
            }

            fieldToModify.setText(enteredPasswordValue);
        }
        setVisible(false);
        dispose();
    }
}
