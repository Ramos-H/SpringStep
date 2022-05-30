package com.Group7.SpringStep.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class AddTaskPopup extends JPanel implements ActionListener 
{
    private JButton btnSave;
    private JTextField titleField;
    private PopupContainer popupHandler;
    private MainWindow mainWindow;

    public AddTaskPopup(PopupContainer popupContainer, MainWindow window) 
    {
        popupHandler = popupContainer;
        mainWindow = window;

        setLayout(new GridBagLayout());
        {
            // Put nested components here
            JLabel taskLabel = new JLabel("Task Details");
            JLabel titleLabel = new JLabel("Title");
            JLabel descriptionLabel = new JLabel("Description");
            JLabel tagLabel = new JLabel("Tags");
            JLabel dueDateLabel = new JLabel("Due Date");
            JLabel expectedDurationLabel = new JLabel("Expected Duration");

            titleField = new JTextField();
            JTextArea descriptionArea = new JTextArea();
            descriptionArea.setBorder(BorderFactory.createLineBorder(Color.black));

            JButton btnTag = new JButton("+ TAG");
            btnTag.setBackground(new Color(217, 217, 217));
            JButton btnDateAndTime = new JButton("+");
            btnDateAndTime.setBackground(new Color(217, 217, 217));
            JButton btnDuration = new JButton("+");
            btnDuration.setBackground(new Color(217, 217, 217));
            JButton btnDelete = new JButton("Delete");
            btnDelete.setBackground(new Color(215, 204, 195));
            btnSave = new JButton("Save");
            btnSave.addActionListener(this);
            JButton btnExit = new JButton("X");
            btnExit.setBackground(Color.WHITE);

            JPanel taskPanel = new JPanel(new BorderLayout());
            {
                taskPanel.add(taskLabel, BorderLayout.CENTER);
                taskPanel.add(btnExit, BorderLayout.LINE_END);
            }

            JPanel btnPanel = new JPanel(new GridBagLayout());
            {
                GridBagConstraints btnPanelConstraints = new GridBagConstraints();
                btnPanel.add(btnDelete, btnPanelConstraints);
                btnPanelConstraints.gridx = 2;
                btnPanel.add(btnSave, btnPanelConstraints);

                btnPanelConstraints.gridx = 1;
                btnPanelConstraints.weightx = 1;
                btnPanelConstraints.fill = GridBagConstraints.BOTH;
                btnPanel.add(new JPanel(), btnPanelConstraints);
            }

            GridBagConstraints addTaskWindowConstraints = new GridBagConstraints();
            addTaskWindowConstraints.weightx = 1;
            addTaskWindowConstraints.fill = GridBagConstraints.HORIZONTAL;
            addTaskWindowConstraints.anchor = GridBagConstraints.LAST_LINE_START;
            addTaskWindowConstraints.insets = new Insets(5, 5, 0, 5);

            add(taskPanel, addTaskWindowConstraints);

            addTaskWindowConstraints.gridy = 1;
            add(titleLabel, addTaskWindowConstraints);

            addTaskWindowConstraints.gridy = 2;
            add(titleField, addTaskWindowConstraints);

            addTaskWindowConstraints.gridy = 3;
            add(descriptionLabel, addTaskWindowConstraints);

            addTaskWindowConstraints.gridy = 4;
            add(descriptionArea, addTaskWindowConstraints);

            addTaskWindowConstraints.gridy = 5;
            add(tagLabel, addTaskWindowConstraints);

            addTaskWindowConstraints.gridy = 6;
            add(btnTag, addTaskWindowConstraints);

            addTaskWindowConstraints.gridy = 7;
            add(dueDateLabel, addTaskWindowConstraints);

            addTaskWindowConstraints.gridy = 8;
            add(btnDateAndTime, addTaskWindowConstraints);

            addTaskWindowConstraints.gridy = 9;
            add(expectedDurationLabel, addTaskWindowConstraints);

            addTaskWindowConstraints.gridy = 10;
            add(btnDuration, addTaskWindowConstraints);

            addTaskWindowConstraints.gridy = 11;
            addTaskWindowConstraints.fill = GridBagConstraints.HORIZONTAL;
            addTaskWindowConstraints.anchor = GridBagConstraints.CENTER;
            add(btnPanel, addTaskWindowConstraints);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == btnSave) 
        {
            if (titleField.getText().trim().equals("")) 
            {
                JOptionPane.showMessageDialog(null, "Please add a name for the task", "Error: No name for task", JOptionPane.ERROR_MESSAGE);
            }
        }
        
    }
}