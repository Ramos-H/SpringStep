package com.Group7.SpringStep.UI;

import java.awt.*;

import javax.swing.*;

import com.Group7.SpringStep.*;

public class TaskNode extends JPanel 
{
    public TaskNode()
    {
        setLayout(new GridBagLayout());
        setOpaque(true);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        {
            JTextArea taskName = new JTextArea("New Task");
            taskName.setLineWrap(true);

            JPanel tagPanel = new JPanel(new FlowLayout());
            Utils.setDebugVisible(tagPanel, Color.PINK);

            GridBagConstraints taskNodeConstraints = new GridBagConstraints();
            taskNodeConstraints.gridx = 0;
            taskNodeConstraints.gridy = 0;
            taskNodeConstraints.weightx = 1;
            taskNodeConstraints.weighty = 1;
            taskNodeConstraints.gridwidth = 2;
            taskNodeConstraints.fill = GridBagConstraints.BOTH;
            taskNodeConstraints.anchor = GridBagConstraints.CENTER;
            add(taskName, taskNodeConstraints);
            
            taskNodeConstraints.gridy = 1;
            taskNodeConstraints.weighty = 0;
            taskNodeConstraints.gridwidth = 1;
            taskNodeConstraints.fill = GridBagConstraints.HORIZONTAL;
            taskNodeConstraints.anchor = taskNodeConstraints.NORTHWEST;
            add(tagPanel, taskNodeConstraints);
            
            taskNodeConstraints.gridx = 1;
            taskNodeConstraints.weightx = 0;
            taskNodeConstraints.fill = GridBagConstraints.NONE;
            add(new JButton("Edit"), taskNodeConstraints);
        }
    }
}
