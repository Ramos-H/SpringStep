package com.Group7.SpringStep.ui;

import javax.swing.*;

import com.Group7.SpringStep.Utils;

import java.awt.*;
import java.awt.event.*;

public class EditProfilePropertyDialog extends JDialog implements ActionListener
{
    private JLabel currentPropertyLabel;
    private JLabel newPropertyLabel;
    private JLabel confirmPropertyLabel;
    private String currentPropertyValue;
    private JButton doneButton;
    private JButton cancelButton;
    private JTextField fieldToModify;
    private JTextField newPropertyField;
    private JTextField confirmPropertyField;
    private String currentProperty;

    public EditProfilePropertyDialog(String property, JTextField textField)
    {
        this(property, textField, null);
    }

    public EditProfilePropertyDialog(String property, JTextField textField, String currentValue)
    {
        currentPropertyValue = currentValue;
        fieldToModify = textField;
        setModal(true);
        Rectangle screenSize = getGraphicsConfiguration().getBounds();
        float widthScale = 30f;
        float heightScale = 30f;
        int width = Math.round(screenSize.width * (widthScale / 100f));
        int height = Math.round(screenSize.height * (heightScale / 100f));
        setSize(width, height);

        int x = Math.round(screenSize.width / 2 - getWidth() / 2);
        int y = Math.round(screenSize.height / 2 - getHeight() / 2);
        setLocation(x, y);
        setLayout(new GridLayout(1, 1));
        {
            JPanel padPanel = new JPanel();
            Utils.padJComponent(padPanel, 0, 30, 0, 30);
            padPanel.setLayout(new GridBagLayout());
            {
                currentPropertyLabel = new JLabel();
                newPropertyLabel = new JLabel();
                confirmPropertyLabel = new JLabel();
                JPanel buttonPanel = new JPanel(new FlowLayout());
                {
                    cancelButton = new JButton("Cancel");
                    cancelButton.addActionListener(this);

                    doneButton = new JButton("Done");
                    doneButton.addActionListener(this);

                    buttonPanel.add(cancelButton);
                    buttonPanel.add(doneButton);
                }
                setPropertyBeingChanged(property);

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.anchor = GridBagConstraints.WEST;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weightx = 1;
                padPanel.add(currentPropertyLabel, gbc);

                gbc.gridy = 1;
                padPanel.add(newPropertyLabel, gbc);

                gbc.gridy = 2;
                newPropertyField = new JTextField();
                padPanel.add(newPropertyField, gbc);

                gbc.gridy = 3;
                padPanel.add(confirmPropertyLabel, gbc);

                gbc.gridy = 4;
                confirmPropertyField = new JTextField();
                padPanel.add(confirmPropertyField, gbc);

                gbc.gridy = 5;
                gbc.gridwidth = 2;
                padPanel.add(buttonPanel, gbc);
            }
            add(padPanel);
        }
    }
    
    public void setPropertyBeingChanged(String property)
    {
        currentProperty = property;
        currentPropertyLabel.setText("Current " + property + ": " + currentPropertyValue);
        newPropertyLabel.setText("New " + property + ": ");
        confirmPropertyLabel.setText("Confirm new " + property + ": ");

        if(currentPropertyValue == null)
            currentPropertyLabel.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        Object eventSource = e.getSource();
        if(eventSource == doneButton)
        {
            String enteredPropertyValue = newPropertyField.getText();
            String enteredConfirmValue = confirmPropertyField.getText();
            boolean hasMissingInput = false;
            String noInputErrorTitle = "Error: No Input";
            String noInputErrorMessage = "One of the fields in this window isn't filled!";

            if (Utils.isTextEmpty(enteredPropertyValue)) 
            {
                hasMissingInput = true;
                noInputErrorMessage = "Error: No new " + currentProperty + " provided.";
                noInputErrorMessage = String.format("No new %s was set. \nPlease enter the new %s and try again.", currentProperty, currentProperty);
            }
            else if (Utils.isTextEmpty(enteredConfirmValue))
            {
                hasMissingInput = true;
                noInputErrorMessage = "Error: " + currentProperty + " not confirmed.";
                noInputErrorMessage = String.format(
                        "You haven't confirmed your %s. \nPlease enter your %s again in the \"Confirm %s\" field and try again.",
                        currentProperty, currentProperty, currentProperty);
            }

            if (hasMissingInput)
            {
                JOptionPane.showMessageDialog(this, noInputErrorMessage, noInputErrorTitle, JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!enteredPropertyValue.equals(enteredConfirmValue))
            {
                hasMissingInput = true;
                String message = String.format(
                        "The text you entered in the \"Confirm %s\" field doesn't match what you entered for your %s. \nPlease enter your %s again in the confirm %s field and try again.",
                        currentProperty, currentProperty, currentProperty, currentProperty);
                JOptionPane.showMessageDialog(this, message, "Error: " + currentProperty + " not confirmed.",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            fieldToModify.setText(enteredPropertyValue);
        }
        setVisible(false);
        dispose();
    }
}
