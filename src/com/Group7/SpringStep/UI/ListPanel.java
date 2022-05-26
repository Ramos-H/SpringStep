package com.Group7.SpringStep.UI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.Group7.SpringStep.*;

public class ListPanel extends JPanel implements ActionListener
{
    private JPanel internalListContainer;
    private JScrollPane listScrollPanel;
    private JButton addTaskButton;
    
    public ListPanel(String title, Color color)
    {
        setLayout(new GridBagLayout());
        {
            JPanel outerContainer = new JPanel(new GridBagLayout());
            {
                outerContainer.setBackground(color);
                JPanel listTitleBar = new JPanel(new BorderLayout());
                listTitleBar.setOpaque(false);
                Utils.padJComponent(listTitleBar, 5, 5, 5, 5);
                {
                    JLabel listTitleLabel = new JLabel(title);
                    listTitleLabel.setFont(new Font(listTitleLabel.getFont().getFontName(), Font.BOLD, 21));

                    JButton listEditButton = new JButton("...");

                    listTitleBar.add(listTitleLabel, BorderLayout.CENTER);
                    listTitleBar.add(listEditButton, BorderLayout.LINE_END);
                }

                internalListContainer = new JPanel(new GridBagLayout());
                internalListContainer.setOpaque(false);
                listScrollPanel = new ListScrollPane(internalListContainer);
                listScrollPanel.setVisible(false);
                listScrollPanel.setOpaque(false);

                addTaskButton = new JButton("Add");
                addTaskButton.addActionListener(this);

                GridBagConstraints outerPanelConstraints = new GridBagConstraints();
                outerPanelConstraints.weightx = 1;
                outerPanelConstraints.gridwidth = 2;

                outerPanelConstraints.gridy = 0;
                outerPanelConstraints.anchor = GridBagConstraints.NORTH;
                outerPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
                outerContainer.add(listTitleBar, outerPanelConstraints);

                outerPanelConstraints.gridy = 1;
                outerPanelConstraints.weighty = 1;
                outerPanelConstraints.fill = GridBagConstraints.BOTH;
                outerContainer.add(listScrollPanel, outerPanelConstraints);

                outerPanelConstraints.weighty = 0;
                outerPanelConstraints.gridwidth = 1;
                outerPanelConstraints.anchor = GridBagConstraints.WEST;

                outerPanelConstraints.gridx = 0;
                outerPanelConstraints.gridy = 2;
                outerPanelConstraints.fill = GridBagConstraints.NONE;
                outerContainer.add(addTaskButton, outerPanelConstraints);

                outerPanelConstraints.gridx = 1;
                outerPanelConstraints.gridy = 2;
                outerPanelConstraints.fill = GridBagConstraints.BOTH;
                outerContainer.add(new JLabel("Add a task"), outerPanelConstraints);
            }
            
            GridBagConstraints listPanelConstraints = new GridBagConstraints();
            listPanelConstraints.anchor = GridBagConstraints.NORTH;
            listPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
            listPanelConstraints.weightx = 1;
            listPanelConstraints.weighty = 1;

            add(outerContainer, listPanelConstraints);
        }
    }
    
    public JButton getAddTaskButton() 
    {
        return addTaskButton;
    }
    
    public void AddTask()
    {
        GridBagConstraints taskListConstraints = new GridBagConstraints();
        taskListConstraints.weightx = 1;
        taskListConstraints.fill = GridBagConstraints.HORIZONTAL;
        taskListConstraints.anchor = GridBagConstraints.CENTER;
        taskListConstraints.gridy = internalListContainer.getComponentCount() + 1;
        taskListConstraints.insets = new Insets(5, 5, 5, 5);

        JPanel taskNode = new TaskNode();

        if(!listScrollPanel.isVisible())
            listScrollPanel.setVisible(true);
            
        internalListContainer.add(taskNode, taskListConstraints);
        
        this.revalidate();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        AddTask();
    }
}