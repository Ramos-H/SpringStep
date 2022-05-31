package com.Group7.SpringStep.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.Group7.SpringStep.Utils;
import com.Group7.SpringStep.data.TagDetails;
import com.Group7.SpringStep.data.TaskDetails;

import com.github.lgooddatepicker.components.*;

public class AddTaskPopup extends JPanel implements ActionListener 
{
    private JButton btnSave;
    private JTextField taskTitleField;
    private PopupContainer popupHandler;
    private MainWindow mainWindow;
    private ListPanel listPanel;
    private TaskDetails currentTask;
    private JButton addTagButton;
    private JButton btnExit;
    private JButton btnDelete;
    private JPanel tagPanel;
    private JTextArea descriptionArea;
    private DatePicker deadlinePicker;

    public AddTaskPopup(PopupContainer popupContainer, MainWindow window, TaskDetails taskToEdit, ListPanel destinationList) 
    {
        currentTask = taskToEdit;
        popupHandler = popupContainer;
        mainWindow = window;
        listPanel = destinationList;

        setLayout(new BorderLayout());
        Utils.padJComponent(this, 5, 5, 5, 5);
        {
            JLabel titleLabel = new JLabel("Task Details");
            btnExit = new JButton("X");
            btnExit.setBackground(Color.WHITE);
            JPanel titleBar = new JPanel(new BorderLayout());
            {
                titleBar.add(titleLabel, BorderLayout.CENTER);
                titleBar.add(btnExit, BorderLayout.LINE_END);
            }

            JPanel contentArea = new JPanel(new GridBagLayout());
            {
                taskTitleField = new JTextField();

                descriptionArea = new JTextArea();
                descriptionArea.setBorder(BorderFactory.createLineBorder(Color.black));
                descriptionArea.setLineWrap(true);

                tagPanel = new JPanel(new FlowLayout());
                {
                    addTagButton = new JButton("+ TAG");
                    addTagButton.addActionListener(this);
                    addTagButton.setBackground(new Color(217, 217, 217));

                    tagPanel.add(addTagButton);
                }

                deadlinePicker = new DatePicker();
                deadlinePicker.setBackground(new Color(217, 217, 217));

                JButton btnDuration = new JButton("+");
                btnDuration.setBackground(new Color(217, 217, 217));

                GridBagConstraints addTaskWindowConstraints = new GridBagConstraints();
                addTaskWindowConstraints.weightx = 1;
                addTaskWindowConstraints.fill = GridBagConstraints.HORIZONTAL;
                addTaskWindowConstraints.anchor = GridBagConstraints.WEST;
                addTaskWindowConstraints.insets = new Insets(5, 5, 0, 5);

                addTaskWindowConstraints.gridy = 0;
                contentArea.add(new JLabel("Title"), addTaskWindowConstraints);

                addTaskWindowConstraints.gridy = 1;
                contentArea.add(taskTitleField, addTaskWindowConstraints);

                addTaskWindowConstraints.gridy = 2;
                contentArea.add(new JLabel("Description"), addTaskWindowConstraints);

                addTaskWindowConstraints.gridy = 3;
                contentArea.add(descriptionArea, addTaskWindowConstraints);
                
                // addTaskWindowConstraints.gridy = 4;
                // contentArea.add(new JLabel("Tags"), addTaskWindowConstraints);

                // addTaskWindowConstraints.gridy = 5;
                // // contentArea.add(tagPanel, addTaskWindowConstraints);

                addTaskWindowConstraints.gridy = 6;
                contentArea.add(new JLabel("Due Date"), addTaskWindowConstraints);

                addTaskWindowConstraints.gridy = 7;
                contentArea.add(deadlinePicker, addTaskWindowConstraints);

                // addTaskWindowConstraints.gridy = 8;
                // contentArea.add(new JLabel("Expected Duration"), addTaskWindowConstraints);

                // addTaskWindowConstraints.gridy = 9;
                // contentArea.add(btnDuration, addTaskWindowConstraints);
            }
            
            JScrollPane contentAreaContainer = new JScrollPane(contentArea);

            JPanel buttonBar = new JPanel(new GridBagLayout());
            {
                btnDelete = new JButton("Delete");
                btnDelete.setBackground(new Color(215, 204, 195));
                btnSave = new JButton("Save");
                btnSave.addActionListener(this);

                GridBagConstraints btnPanelConstraints = new GridBagConstraints();
                buttonBar.add(btnDelete, btnPanelConstraints);

                btnPanelConstraints.gridx = 2;
                buttonBar.add(btnSave, btnPanelConstraints);

                // Spacer
                btnPanelConstraints.gridx = 1;
                btnPanelConstraints.weightx = 1;
                btnPanelConstraints.fill = GridBagConstraints.BOTH;
                buttonBar.add(new JPanel(), btnPanelConstraints);
            }
            
            add(titleBar, BorderLayout.PAGE_START);
            add(contentAreaContainer, BorderLayout.CENTER);
            add(buttonBar, BorderLayout.PAGE_END);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        Object eventSource = e.getSource();
        if (eventSource == addTagButton) 
        {
            JDialog newTagDialog = new NewTagDialog(tagPanel);
            newTagDialog.setVisible(true);
        } else if (eventSource == btnSave) 
        {
            if (taskTitleField.getText().trim().equals("")) 
            {
                JOptionPane.showMessageDialog(null, "Please add a name for the task", "Error: No name for task",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            currentTask.setName(taskTitleField.getText().trim());

            currentTask.setDescription(descriptionArea.getText().trim());

            if(deadlinePicker.getDate() != null)
            {
                currentTask.setDeadline(deadlinePicker.getDate());
            }

            TaskNode newTaskNode = new TaskNode(currentTask);
            listPanel.addTaskToList(newTaskNode);
            popupHandler.hidePopup();
        }
    }
}