package com.Group7.SpringStep.ui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import com.Group7.SpringStep.*;
import com.Group7.SpringStep.data.*;

import com.github.lgooddatepicker.components.*;

public class AddTaskPopup extends JPanel implements ActionListener 
{
    private JButton btnSave;
    private JButton btnExit;
    private JButton btnDelete;

    private JTextField taskTitleField;
    private JTextArea descriptionArea;
    private DatePicker deadlinePicker;

    private TaskNode currentTaskNode;
    private TaskDetails currentTaskDetails;
    private TaskDetails newTaskDetails;
    private ListPanel destinationPanel;
    private boolean editMode;

    private PopupContainer popupHandler;
    private MainWindow mainWindow;

    public AddTaskPopup(PopupContainer popupContainer, MainWindow window)
    {
        popupHandler = popupContainer;
        mainWindow = window;

        setLayout(new BorderLayout());
        Utils.padJComponent(this, 5, 5, 5, 5);
        {
            JPanel titleBar = new JPanel(new BorderLayout());
            {
                JLabel titleLabel = new JLabel("Task Details");

                btnExit = new JButton("X");
                btnExit.addActionListener(this);
                btnExit.setBackground(Color.WHITE);

                titleBar.add(titleLabel, BorderLayout.CENTER);
                titleBar.add(btnExit, BorderLayout.LINE_END);
            }

            JPanel contentArea = new JPanel(new GridBagLayout());
            Utils.padJComponent(contentArea, 5, 5, 5, 5);
            {
                taskTitleField = new JTextField();

                JScrollPane descriptionScroller = new JScrollPane();
                {
                    descriptionArea = new JTextArea();
                    descriptionArea.setBorder(BorderFactory.createLineBorder(Color.black));
                    descriptionArea.setLineWrap(true);

                    descriptionScroller.setViewportView(descriptionArea);
                }

                DatePickerSettings settings = new DatePickerSettings();
                settings.setAllowEmptyDates(true);
                deadlinePicker = new DatePicker(settings);
                deadlinePicker.setBackground(new Color(217, 217, 217));

                JButton btnDuration = new JButton("+");
                btnDuration.setBackground(new Color(217, 217, 217));

                GridBagConstraints addTaskWindowConstraints = new GridBagConstraints();
                addTaskWindowConstraints.weightx = 1;
                addTaskWindowConstraints.fill = GridBagConstraints.HORIZONTAL;
                addTaskWindowConstraints.anchor = GridBagConstraints.NORTHWEST;
                addTaskWindowConstraints.insets = new Insets(5, 5, 0, 5);

                addTaskWindowConstraints.gridx = 0;
                addTaskWindowConstraints.gridy = 0;
                contentArea.add(new JLabel("Title"), addTaskWindowConstraints);

                addTaskWindowConstraints.gridx = 0;
                addTaskWindowConstraints.gridy++;
                contentArea.add(taskTitleField, addTaskWindowConstraints);

                addTaskWindowConstraints.gridy++;
                contentArea.add(new JLabel("Description"), addTaskWindowConstraints);
                
                addTaskWindowConstraints.gridy++;
                addTaskWindowConstraints.weighty = 0.1;
                addTaskWindowConstraints.fill = GridBagConstraints.BOTH;
                contentArea.add(descriptionScroller, addTaskWindowConstraints);
                
                addTaskWindowConstraints.gridy++;
                addTaskWindowConstraints.weighty = 0;
                addTaskWindowConstraints.fill = GridBagConstraints.HORIZONTAL;
                contentArea.add(new JLabel("Due Date"), addTaskWindowConstraints);

                addTaskWindowConstraints.gridy++;
                contentArea.add(deadlinePicker, addTaskWindowConstraints);
            }
            
            JPanel buttonBar = new JPanel(new GridBagLayout());
            {
                btnDelete = new JButton("Delete");
                btnDelete.addActionListener(this);
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
            add(contentArea, BorderLayout.CENTER);
            add(buttonBar, BorderLayout.PAGE_END);

            setEditMode(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        Object eventSource = e.getSource();
        if (eventSource == btnSave) {
            if (taskTitleField.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(null, "Please add a name for the task", "Error: No name for task",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            insertChangesIntoNewTaskDetails();

            if (editMode) {
                if (!currentTaskDetails.equals(newTaskDetails)) {
                    int response = JOptionPane.showConfirmDialog(this,
                            "Are you sure you want to save your changes?",
                            "Save changes to task?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (response == JOptionPane.NO_OPTION) {
                        popupHandler.hidePopup();
                    } else {
                        currentTaskNode.setTaskDetails(newTaskDetails);
                        popupHandler.hidePopup();
                    }
                }
            } else {
                TaskNode newTaskNode = new TaskNode(newTaskDetails, this);
                destinationPanel.addTaskToList(newTaskNode);
                popupHandler.hidePopup();
                mainWindow.updateTimerBasedOnDoingList();
            }
        } else if (eventSource == btnExit) 
        {
            insertChangesIntoNewTaskDetails();
            int response = JOptionPane.YES_OPTION;
            if (!editMode)
            {
                response = JOptionPane.showConfirmDialog(this, "Are you sure you want to discard this new task?",
                        "Discard new task?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            }
            else
            {
                if (!currentTaskDetails.equals(newTaskDetails)) {
                    response = JOptionPane.showConfirmDialog(this,
                            "Are you sure you want to discard your changes to this task?",
                            "Discard your edits?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                }
            }
            
            if (response == JOptionPane.YES_OPTION)
            {
                popupHandler.hidePopup();
            }
        } else if (eventSource == btnDelete) 
        {
            int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this task?",
                    "Delete task?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION)
            {
                currentTaskNode.getParent().remove(currentTaskNode);
                popupHandler.hidePopup();
            }
        }
    }

    private void insertChangesIntoNewTaskDetails() {
        newTaskDetails.setName(taskTitleField.getText().trim());
        newTaskDetails.setDescription(descriptionArea.getText().trim());
        newTaskDetails.setDeadline(deadlinePicker.getDate());
    }
    
    public void setEditMode(boolean newValue)
    {
        editMode = newValue;
        btnDelete.setVisible(editMode);
    }
    
    public void addTask(ListPanel destPanel)
    {
        clearPopupInput();
        setEditMode(false);
        newTaskDetails = new TaskDetails();
        currentTaskDetails = newTaskDetails;
        destinationPanel = destPanel;
        popupHandler.setPopup(this);
    }
    
    public void editTask(TaskNode taskToEdit)
    {
        setEditMode(true);
        currentTaskNode = taskToEdit;
        currentTaskDetails = currentTaskNode.getTaskDetails();
        newTaskDetails = new TaskDetails(currentTaskDetails);
        updatePopupInput();
        popupHandler.setPopup(this);
    }

    public void updatePopupInput()
    {
        taskTitleField.setText(currentTaskDetails.getName());
        descriptionArea.setText(currentTaskDetails.getDescription());
        deadlinePicker.setDate(currentTaskDetails.getDeadline());
    }
    
    public void clearPopupInput()
    {
        taskTitleField.setText("");
        descriptionArea.setText("");
        deadlinePicker.clear();
        currentTaskDetails = null;
        currentTaskNode = null;
        newTaskDetails = null;
    }
}