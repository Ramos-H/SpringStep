package com.Group7.SpringStep.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class AddTaskWindow extends JFrame implements ActionListener{
    private JButton btnSave;
    private JTextField titleField;

    public AddTaskWindow() {
        // Set window parameters first
        setTitle("Add Task");

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
            JPanel mainPanel = new JPanel(new GridBagLayout());
            mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            {
                // Put nested components here
                JLabel taskLabel = new JLabel("Task Details");
                JLabel titleLabel = new JLabel("Title");
                JLabel descriptionLabel = new JLabel("Description");
                JLabel tagLabel = new JLabel("Tags");
                JLabel dueDateLabel = new JLabel("Due Date");
                JLabel expectedDurationLabel = new JLabel("Expected Duration");

                titleField = new JTextField();
                JTextField descriptionField = new JTextField();

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
                    btnPanelConstraints.fill = GridBagConstraints.BOTH; 
                    btnPanel.add(new JPanel(),btnPanelConstraints);
                }

                GridBagConstraints addTaskWindowConstraints = new GridBagConstraints();
                addTaskWindowConstraints.weighty = 0;
                addTaskWindowConstraints.anchor = GridBagConstraints.LAST_LINE_START;
                addTaskWindowConstraints.insets = new Insets(5, 5, 0, 5);

                mainPanel.add(taskPanel, addTaskWindowConstraints);

                addTaskWindowConstraints.gridy = 1;
                mainPanel.add(titleLabel, addTaskWindowConstraints);

                addTaskWindowConstraints.fill = GridBagConstraints.HORIZONTAL;
                addTaskWindowConstraints.gridy = 2;
                mainPanel.add(titleField, addTaskWindowConstraints);

                addTaskWindowConstraints.gridy = 3;
                mainPanel.add(descriptionLabel, addTaskWindowConstraints);

                addTaskWindowConstraints.gridy = 4;
                mainPanel.add(descriptionField, addTaskWindowConstraints);

                addTaskWindowConstraints.gridy = 5;
                mainPanel.add(tagLabel, addTaskWindowConstraints);

                addTaskWindowConstraints.gridy = 6;
                mainPanel.add(btnTag, addTaskWindowConstraints);

                addTaskWindowConstraints.gridy = 7;
                mainPanel.add(dueDateLabel, addTaskWindowConstraints);

                addTaskWindowConstraints.gridy = 8;
                mainPanel.add(btnDateAndTime, addTaskWindowConstraints);

                addTaskWindowConstraints.gridy = 9;
                mainPanel.add(expectedDurationLabel, addTaskWindowConstraints);

                addTaskWindowConstraints.gridy = 10;
                mainPanel.add(btnDuration, addTaskWindowConstraints);

                addTaskWindowConstraints.gridy = 11;
                addTaskWindowConstraints.fill = GridBagConstraints.NONE;
                addTaskWindowConstraints.anchor = GridBagConstraints.CENTER;
                mainPanel.add(btnPanel, addTaskWindowConstraints);

            }
            // Put the call to add the nested components here
            add(mainPanel);

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btnSave){
            if(titleField.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null, "Please add a name for the task", "Error: No name for task", JOptionPane.ERROR_MESSAGE);
            }
        }
        
    }
}