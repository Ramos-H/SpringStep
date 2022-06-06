package com.Group7.SpringStep.ui;

import java.util.*;

import java.awt.*;

import javax.swing.*;

import com.Group7.SpringStep.*;

public class ListPanel extends JPanel
{
    private JPanel internalListContainer;
    private JScrollPane listScrollPanel;
    private RoundedButton addTaskButton;
    private JPanel innerPanel;
    
    public ListPanel(String title, String addButtonMessage, Color color)
    {
        setLayout(new GridBagLayout());
        {
            setOpaque(false);
            RoundedPanel outerPanel = new RoundedPanel(new GridBagLayout());
            outerPanel.setBackground(color);
            {
                innerPanel = new JPanel(new GridBagLayout());
                innerPanel.setOpaque(false);
                Utils.padJComponent(innerPanel, 5, 5, 5, 5);
                {
                    JPanel listTitleBar = new JPanel(new BorderLayout());
                    listTitleBar.setOpaque(false);
                    Utils.padJComponent(listTitleBar, 5, 5, 5, 5);
                    {
                        JLabel listTitleLabel = new JLabel(title);
                        listTitleLabel.setFont(new Font(listTitleLabel.getFont().getFontName(), Font.BOLD, 21));

                        listTitleBar.add(listTitleLabel, BorderLayout.CENTER);
                    }

                    internalListContainer = new JPanel(new GridBagLayout());
                    internalListContainer.setBackground(new Color(0, 0, 0, 128));

                    listScrollPanel = new ListScrollPane(internalListContainer);
                    listScrollPanel.setOpaque(false);
                    listScrollPanel.getVerticalScrollBar().setUnitIncrement(10);

                    addTaskButton = new RoundedButton("Add");
                    addTaskButton.setBackground(new Color(135, 195, 193));

                    GridBagConstraints innerPanelConstraints = new GridBagConstraints();
                    innerPanelConstraints.weightx = 1;
                    innerPanelConstraints.gridwidth = 2;

                    innerPanelConstraints.gridy = 0;
                    innerPanelConstraints.anchor = GridBagConstraints.NORTH;
                    innerPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
                    innerPanel.add(listTitleBar, innerPanelConstraints);

                    innerPanelConstraints.gridy = 1;
                    innerPanelConstraints.weighty = 1;
                    innerPanelConstraints.fill = GridBagConstraints.BOTH;
                    innerPanel.add(listScrollPanel, innerPanelConstraints);

                    innerPanelConstraints.weighty = 0;
                    innerPanelConstraints.gridwidth = 1;
                    innerPanelConstraints.anchor = GridBagConstraints.WEST;

                    innerPanelConstraints.gridx = 0;
                    innerPanelConstraints.gridy = 2;
                    innerPanelConstraints.fill = GridBagConstraints.NONE;
                    innerPanelConstraints.insets = new Insets(5, 0, 0, 5);
                    innerPanel.add(addTaskButton, innerPanelConstraints);
                    
                    innerPanelConstraints.gridx = 1;
                    innerPanelConstraints.gridy = 2;
                    innerPanelConstraints.fill = GridBagConstraints.BOTH;
                    innerPanelConstraints.insets = new Insets(5, 0, 5, 0);
                    innerPanel.add(new JLabel(addButtonMessage), innerPanelConstraints);
                }

                GridBagConstraints outerPanelConstraints = new GridBagConstraints();
                outerPanelConstraints.anchor = GridBagConstraints.NORTH;
                outerPanelConstraints.fill = GridBagConstraints.BOTH;
                outerPanelConstraints.weightx = 1;
                outerPanelConstraints.weighty = 1;

                outerPanel.add(innerPanel, outerPanelConstraints);
            }

            GridBagConstraints listPanelConstraints = new GridBagConstraints();
            listPanelConstraints.anchor = GridBagConstraints.NORTH;
            listPanelConstraints.fill = GridBagConstraints.BOTH;
            listPanelConstraints.weightx = 1;
            listPanelConstraints.weighty = 1;

            add(outerPanel, listPanelConstraints);
        }
    }

    @Override
    public Rectangle getVisibleRect() 
    {
        return innerPanel.getVisibleRect();
    }
    
    public JButton getAddTaskButton() 
    {
        return addTaskButton;
    }
    
    public void addTaskToList(JPanel newTaskNode)
    {
        GridBagConstraints taskListConstraints = new GridBagConstraints();
        taskListConstraints.weightx = 1;
        taskListConstraints.fill = GridBagConstraints.HORIZONTAL;
        taskListConstraints.anchor = GridBagConstraints.CENTER;
        taskListConstraints.insets = new Insets(5, 5, 5, 5);
        
        if (!listScrollPanel.isVisible())
            listScrollPanel.setVisible(true);
        
        ArrayList<TaskNode> currentTaskNodes = getTaskNodes();
        internalListContainer.removeAll();

        if(currentTaskNodes.size() > 0)
        {
            for (TaskNode existingTaskNode : currentTaskNodes) 
            {
                internalListContainer.add(existingTaskNode, taskListConstraints);
                taskListConstraints.gridy = internalListContainer.getComponentCount() + 1;
            }
        }
        
        internalListContainer.add(newTaskNode, taskListConstraints);

        revalidate();
    }
    
    public ArrayList<TaskNode> getTaskNodes()
    {
        int taskNodeCount = internalListContainer.getComponentCount();
        ArrayList<TaskNode> taskNodes = new ArrayList<>(taskNodeCount);
        if (taskNodeCount < 1) {
            return taskNodes;
        }

        Component[] components = internalListContainer.getComponents();
        for (Object currentObject : components) {
            if (currentObject instanceof TaskNode) {
                taskNodes.add((TaskNode) currentObject);
            }
        }

        return taskNodes;
    }
    
    public void clear()
    {
        internalListContainer.removeAll();
    }
}
