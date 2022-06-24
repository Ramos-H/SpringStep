package com.Group7.SpringStep.ui;

import javax.swing.*;
import javax.swing.border.CompoundBorder;

import java.awt.*;
import java.awt.event.*;

import com.Group7.SpringStep.*;
import com.Group7.SpringStep.data.*;

/** A UI Component that represents a task */
public class TaskNode extends RoundedPanel implements ActionListener 
{
    private JTextArea taskNameArea;
    private TaskDetails taskDetails;
    private JPanel tagPanel;
    private AddTaskPopup taskEditor;
    private JButton editButton;

    ///////////////////////////////////////////////// CONSTRUCTORS /////////////////////////////////////////////////
    public TaskNode(TaskDetails newDetails, AddTaskPopup editor)
    {
        taskEditor = editor;

        setLayout(new GridLayout(1, 1));
        setBackground(Color.WHITE);
        {
            JPanel innerPanel = new JPanel(new GridBagLayout());
            Utils.padJComponent(innerPanel, 5, 5, 5, 5);
            innerPanel.setOpaque(false);
            {
                taskNameArea = new JTextArea();
                taskNameArea.setEditable(false);
                taskNameArea.setLineWrap(true);
                taskNameArea.setWrapStyleWord(true);
                taskNameArea.setBorder(null);
                Utils.padJComponent(taskNameArea, 3, 3, 3, 3);
                taskNameArea.setBorder(
                        new CompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), taskNameArea.getBorder()));

                tagPanel = new JPanel(new FlowLayout());
                tagPanel.setOpaque(false);
                Utils.setDebugVisible(tagPanel, Color.PINK);

                editButton = new JButton();
                editButton.addActionListener(this);
                Image editButtonImage = Utils.getScaledImage(App.resources.get("Edit_Button_(Dots)_256.png"), 0.125);
                if (editButtonImage != null) {
                    Utils.setButtonIcon(editButton, new ImageIcon(editButtonImage));
                }

                GridBagConstraints innerPanelConstraints = new GridBagConstraints();
                innerPanelConstraints.gridx = 0;
                innerPanelConstraints.gridy = 0;
                innerPanelConstraints.weightx = 1;
                innerPanelConstraints.weighty = 1;
                innerPanelConstraints.gridwidth = 2;
                innerPanelConstraints.fill = GridBagConstraints.BOTH;
                innerPanelConstraints.anchor = GridBagConstraints.CENTER;
                innerPanel.add(taskNameArea, innerPanelConstraints);

                innerPanelConstraints.gridy = 1;
                innerPanelConstraints.weighty = 0;
                innerPanelConstraints.gridwidth = 1;
                innerPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
                innerPanelConstraints.anchor = GridBagConstraints.NORTHWEST;
                innerPanel.add(tagPanel, innerPanelConstraints);

                innerPanelConstraints.gridx = 1;
                innerPanelConstraints.weightx = 0;
                innerPanelConstraints.fill = GridBagConstraints.NONE;
                innerPanel.add(editButton, innerPanelConstraints);
            }
            add(innerPanel);
        }
        setTaskDetails(newDetails);
    }

    ///////////////////////////////////////////////// GETTERS /////////////////////////////////////////////////
    public JTextArea getTaskNameArea() { return taskNameArea; }
    public TaskDetails getTaskDetails() { return taskDetails; }

    ///////////////////////////////////////////////// SETTERS /////////////////////////////////////////////////
    /**
     * Updates the task node to display the information held inside the given task details
     * @param newTaskDetails The task details to pull information from
     */
    public void setTaskDetails(TaskDetails newTaskDetails) 
    {
        taskDetails = newTaskDetails;
        taskNameArea.setText(taskDetails.getName());

        tagPanel.removeAll();

        if (taskDetails.getDeadline() != null)
        {
            tagPanel.add(createTagDisplay("Deadline: " + taskDetails.getDeadline().toString(), Color.RED));
        }

        revalidate();
        repaint();
    }

    ///////////////////////////////////////////////// EVENT HANDLERS /////////////////////////////////////////////////
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == editButton) { taskEditor.editTask(this); }
    }

    ///////////////////////////////////////////////// INSTANCE METHODS /////////////////////////////////////////////////
    /**
     * <p>Creates a tag to be displayed on the task node with the given text label and background color.</p>
     * <p>Was originally made for custom tags, before the tag system itself was scrapped during development. Now
     * used for just the deadline of the task.</p>
     * @param text The text on the tag's label
     * @param bgColor The background color of the tag
     * @return A tag that can be added to the tag panel for display purposes.
     */
    public JPanel createTagDisplay(String text, Color bgColor)
    {
        JPanel newTagDisplay = new RoundedPanel(new FlowLayout());
        newTagDisplay.setBackground(bgColor);
        JLabel tagLabel = new JLabel(text);
        tagLabel.setForeground(Color.WHITE);
        newTagDisplay.add(tagLabel);
        return newTagDisplay;
    }
}
