package com.Group7.SpringStep.ui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import com.Group7.SpringStep.*;
import com.Group7.SpringStep.data.*;

public class TaskNode extends JPanel implements ActionListener 
{
    private JTextArea taskNameArea;
    private TaskDetails taskDetails;
    private JPanel tagPanel;
    private AddTaskPopup taskEditor;
    private JButton editButton;

    public TaskNode(TaskDetails newDetails, AddTaskPopup editor)
    {
        taskEditor = editor;
        
        setLayout(new GridBagLayout());
        setOpaque(true);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        {
            taskNameArea = new JTextArea(Integer.toString(this.hashCode()));
            taskNameArea.setLineWrap(true);

            tagPanel = new JPanel(new FlowLayout());
            Utils.setDebugVisible(tagPanel, Color.PINK);
            
            editButton = new JButton("Edit");
            editButton.addActionListener(this);

            GridBagConstraints taskNodeConstraints = new GridBagConstraints();
            taskNodeConstraints.gridx = 0;
            taskNodeConstraints.gridy = 0;
            taskNodeConstraints.weightx = 1;
            taskNodeConstraints.weighty = 1;
            taskNodeConstraints.gridwidth = 2;
            taskNodeConstraints.fill = GridBagConstraints.BOTH;
            taskNodeConstraints.anchor = GridBagConstraints.CENTER;
            add(taskNameArea, taskNodeConstraints);

            taskNodeConstraints.gridy = 1;
            taskNodeConstraints.weighty = 0;
            taskNodeConstraints.gridwidth = 1;
            taskNodeConstraints.fill = GridBagConstraints.HORIZONTAL;
            taskNodeConstraints.anchor = taskNodeConstraints.NORTHWEST;
            add(tagPanel, taskNodeConstraints);

            taskNodeConstraints.gridx = 1;
            taskNodeConstraints.weightx = 0;
            taskNodeConstraints.fill = GridBagConstraints.NONE;
            add(editButton, taskNodeConstraints);
        }
        setTaskDetails(newDetails);
    }

    
    /**
     * @return the taskNameArea
     */
    public JTextArea getTaskNameArea() { return taskNameArea; }

    /**
     * @param newTaskDetails the taskDetails to set
     */
    public void setTaskDetails(TaskDetails newTaskDetails) 
    {
        taskDetails = newTaskDetails;

        taskNameArea.setText(taskDetails.getName());

        tagPanel.removeAll();

        if (taskDetails.getDeadline() != null)
            tagPanel.add(createTagDisplay("DL: " + taskDetails.getDeadline().toString(), Color.RED));

        // ArrayList<TagDetails> tags = taskDetails.getTags();
        // for (TagDetails tag : tags) 
        // {
        //     tagPanel.add(createTagDisplay(tag.getName(), tag.getColor()));
        // }

        // tagPanel.add(createTagDisplay(taskDetails.getExpectedDuration().toString(), Color.BLUE));
        revalidate();
        repaint();
    }
    
    /**
     * @return the taskDetails
     */
    public TaskDetails getTaskDetails() {
        return taskDetails;
    }
    
    public JPanel createTagDisplay(String text, Color bgColor)
    {
        JPanel newTagDisplay = new JPanel(new FlowLayout());
        newTagDisplay.setBackground(bgColor);
        newTagDisplay.add(new JLabel(text));
        return newTagDisplay;
    }


    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == editButton)
        {
            taskEditor.editTask(this);
        }
    }
}
