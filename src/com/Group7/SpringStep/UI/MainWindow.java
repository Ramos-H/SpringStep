package com.Group7.SpringStep.UI;

import java.awt.*;
import java.awt.event.*;

import java.util.*;

import javax.swing.*;

import com.Group7.SpringStep.*;

public class MainWindow extends JFrame implements ActionListener, AWTEventListener
{
    private ListPanel toDoPanel;
    private ListPanel doingPanel;
    private ListPanel donePanel;
    private JButton toDoAddTaskButton;
    private JButton doingAddTaskButton;
    private JButton doneAddTaskButton;

    private ListPanel previousPanel = null;
    private ListPanel hoveredPanel = null;
    private TaskNode hoveredTask = null;
    private TaskNode heldTask = null;

    private Point oldMouseScreenPosition = new Point();
    private Point newMouseScreenPosition = new Point();
    private Point mouseDelta = new Point();
    private ArrayList<TaskNode> taskNodes;

    public MainWindow()
    {
        // Set window parameters
        setTitle("SpringStep");
        long eventMask = AWTEvent.MOUSE_MOTION_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK;

        getToolkit().addAWTEventListener(this, eventMask);

        Rectangle screenSize = getGraphicsConfiguration().getBounds();
        float widthScale = 80f;
        float heightScale = 80f;
        int width = Math.round(screenSize.width * (widthScale / 100f));
        int height = Math.round(screenSize.height * (heightScale / 100f));
        setSize(width, height);

        int x = Math.round(screenSize.width / 2 - getWidth() / 2);
        int y = Math.round(screenSize.height / 2 - getHeight() / 2);
        setLocation(x, y);

        setLayout(new BorderLayout());
        {
            // Put nested components here
            JPanel titleBar = new JPanel(new GridBagLayout());
            {
                JLabel windowTitle = new JLabel(getTitle());
                windowTitle.setHorizontalAlignment(SwingConstants.CENTER);

                JButton boardListButton = new JButton("v");
                JButton userButton = new JButton("User");
                JButton minimizeButton = new JButton("_");
                JButton maxmimizeButton = new JButton("[ ]");
                JButton closeButton = new JButton("X");

                GridBagConstraints titleBarConstraints = new GridBagConstraints();
                titleBarConstraints.weightx = 1;

                titleBarConstraints.gridx = 0;
                titleBarConstraints.anchor = GridBagConstraints.EAST;
                titleBar.add(windowTitle, titleBarConstraints);

                titleBarConstraints.anchor = GridBagConstraints.WEST;
                titleBarConstraints.gridx = 1;
                titleBar.add(boardListButton, titleBarConstraints);

                titleBarConstraints.weightx = 0;

                titleBarConstraints.gridx = 2;
                titleBar.add(userButton, titleBarConstraints);

                titleBarConstraints.gridx = 3;
                titleBar.add(minimizeButton, titleBarConstraints);

                titleBarConstraints.gridx = 4;
                titleBar.add(maxmimizeButton, titleBarConstraints);

                titleBarConstraints.gridx = 5;
                titleBar.add(closeButton, titleBarConstraints);
            }

            JPanel contentPanel = new JPanel(new GridBagLayout());
            Utils.setDebugVisible(contentPanel, Color.ORANGE);
            {
                JPanel searchBar = new JPanel(new GridBagLayout());
                searchBar.setOpaque(true);
                searchBar.setBackground(Color.WHITE);
                {
                    JTextField searchBarTextField = new JTextField();
                    JButton searchButton = new JButton("Search");
                    JButton helpButton = new JButton("?");

                    GridBagConstraints searchBarConstraints = new GridBagConstraints();
                    searchBarConstraints.anchor = GridBagConstraints.CENTER;

                    searchBarConstraints.gridx = 0;
                    searchBarConstraints.weightx = 1;
                    searchBarConstraints.fill = GridBagConstraints.HORIZONTAL;
                    searchBar.add(searchBarTextField, searchBarConstraints);

                    searchBarConstraints.weightx = 0;
                    searchBarConstraints.fill = GridBagConstraints.NONE;

                    searchBarConstraints.gridx = 1;
                    searchBar.add(searchButton, searchBarConstraints);

                    searchBarConstraints.gridx = 2;
                    searchBar.add(helpButton, searchBarConstraints);
                }

                JPanel timerPanel = new TimerPanel();

                JPanel boardPanel = new JPanel(new GridLayout(1, 3));
                Utils.setDebugVisible(boardPanel, Color.MAGENTA);
                {
                    toDoPanel = new ListPanel("To Do", Color.RED);
                    toDoAddTaskButton = toDoPanel.getAddTaskButton();
                    toDoAddTaskButton.addActionListener(this);
                    Utils.padJComponent(toDoPanel, 5, 5, 5, 5);

                    doingPanel = new ListPanel("Doing", Color.BLUE);
                    doingAddTaskButton = doingPanel.getAddTaskButton();
                    doingAddTaskButton.addActionListener(this);
                    Utils.padJComponent(doingPanel, 5, 5, 5, 5);

                    donePanel = new ListPanel("Done", Color.YELLOW);
                    doneAddTaskButton = donePanel.getAddTaskButton();
                    doneAddTaskButton.setVisible(false);
                    Utils.padJComponent(donePanel, 5, 5, 5, 5);

                    boardPanel.add(toDoPanel);
                    boardPanel.add(doingPanel);
                    boardPanel.add(donePanel);
                }

                GridBagConstraints contentPanelConstraints = new GridBagConstraints();

                contentPanelConstraints.weightx = 1;
                contentPanelConstraints.anchor = GridBagConstraints.NORTH;
                contentPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
                contentPanelConstraints.insets = new Insets(5, 150, 5, 150);

                contentPanelConstraints.gridy = 0;
                contentPanel.add(searchBar, contentPanelConstraints);

                contentPanelConstraints.gridy = 1;
                contentPanelConstraints.fill = GridBagConstraints.NONE;
                contentPanel.add(timerPanel, contentPanelConstraints);

                contentPanelConstraints.gridy = 2;
                contentPanelConstraints.weighty = 1;
                contentPanelConstraints.fill = GridBagConstraints.BOTH;
                contentPanelConstraints.anchor = GridBagConstraints.CENTER;
                contentPanel.add(boardPanel, contentPanelConstraints);
            }

            JPanel shortcutsPanel = new JPanel();
            Utils.setDebugVisible(shortcutsPanel, Color.BLACK);

            //Put the call to add the nested components here
            add(titleBar, BorderLayout.PAGE_START);
            add(contentPanel, BorderLayout.CENTER);
            add(shortcutsPanel, BorderLayout.PAGE_END);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        Object eventSource = e.getSource();
        TaskNode taskNode = new TaskNode();
        if(eventSource == toDoAddTaskButton)
        {
            toDoPanel.addTaskToList(taskNode);
        }
        else if(eventSource == doingAddTaskButton)
        {
            doingPanel.addTaskToList(taskNode);
        }
    }
    
    @Override
    public void eventDispatched(AWTEvent caughtEvent) 
    {
        updateDebugDetails();

        int eventId = caughtEvent.getID();
        newMouseScreenPosition = MouseInfo.getPointerInfo().getLocation();
        taskNodes = new ArrayList<>();

        {
            if(Utils.isMouseOverVisibleRect(newMouseScreenPosition, toDoPanel))
            {
                hoveredPanel = toDoPanel;
            } else if (Utils.isMouseOverVisibleRect(newMouseScreenPosition, doingPanel)) 
            {
                hoveredPanel = doingPanel;
            } else if (Utils.isMouseOverVisibleRect(newMouseScreenPosition, donePanel)) 
            {
                hoveredPanel = donePanel;
            } else 
            {
                hoveredPanel = null;
            }
        }
        
        {
            taskNodes.addAll(toDoPanel.getTaskNodes());
            taskNodes.addAll(doingPanel.getTaskNodes());
            taskNodes.addAll(donePanel.getTaskNodes());
            
            hoveredTask = null;
            for (TaskNode taskNode : taskNodes) 
            {
                if (Utils.isMouseOverVisibleRect(newMouseScreenPosition, taskNode)) {
                    hoveredTask = taskNode;
                    break;
                }
            }
        }

        if(!(caughtEvent.getSource() instanceof JButton)){
            switch (eventId) 
            {
                case MouseEvent.MOUSE_PRESSED:
                    if (hoveredTask == null) 
                    {
                        break;
                    }

                    previousPanel = hoveredPanel;
                    heldTask = hoveredTask;
                    hoveredTask = null;

                    Rectangle heldTaskBounds = heldTask.getBounds();
                    Point heldTaskScreenPosition = heldTask.getLocationOnScreen();
                    Point windowScreenPosition = getLocationOnScreen();
                    Point heldTaskNewPosition = new Point(heldTaskScreenPosition.x - windowScreenPosition.x,
                            heldTaskScreenPosition.y - windowScreenPosition.y);

                    heldTaskBounds.setLocation(heldTaskNewPosition);

                    heldTask.getParent().remove(heldTask);
                    getLayeredPane().add(heldTask);

                    heldTask.setBounds(heldTaskBounds);
                    break;
                case MouseEvent.MOUSE_RELEASED:
                    if (heldTask == null) 
                    {
                        break;
                    }

                    getLayeredPane().remove(heldTask);
                    if(hoveredPanel == null)
                    {
                        previousPanel.addTaskToList(heldTask);
                    }
                    else
                    {
                        hoveredPanel.addTaskToList(heldTask);
                    }
                    previousPanel = null;

                    heldTask = null;
                    hoveredTask = null;
                    break;
            }
        }
        
        if (eventId == MouseEvent.MOUSE_PRESSED || eventId == MouseEvent.MOUSE_RELEASED)
        {
            revalidate();
            repaint();
        }

        if (heldTask != null)
        {
            Point currentLocation = heldTask.getLocation();
            heldTask.setLocation(new Point(currentLocation.x + mouseDelta.x, currentLocation.y + mouseDelta.y));
        }

        mouseDelta = new Point(newMouseScreenPosition.x - oldMouseScreenPosition.x,
                newMouseScreenPosition.y - oldMouseScreenPosition.y);

        oldMouseScreenPosition = newMouseScreenPosition;

        updateDebugDetails();
    }

    private void updateDebugDetails() 
    {
        // Title details
        String currentHoveredPanel = "NONE";
        if (hoveredPanel == toDoPanel)
        {
            currentHoveredPanel = "To Do";
        }
        else if (hoveredPanel == doingPanel) 
        {
            currentHoveredPanel = "Doing";
        }
        else if (hoveredPanel == donePanel)
        {
            currentHoveredPanel = "Done";
        }
        String currentPreviousPanel = "NONE";
        if (previousPanel == toDoPanel)
        {
            currentPreviousPanel = "To Do";
        }
        else if (previousPanel == doingPanel) 
        {
            currentPreviousPanel = "Doing";
        }
        else if (previousPanel == donePanel)
        {
            currentPreviousPanel = "Done";
        }
        String currentHoveredNode = "NONE";
        if (hoveredTask != null)
        {
            currentHoveredNode = hoveredTask.getTaskName().getText();
        }
        String currentHeldNode = "NONE";
        if (heldTask != null)
        {
            currentHeldNode = heldTask.getTaskName().getText();
        }
        String format = "Hovered Panel: %s, Previous Panel: %s, Hovered Node: %s, Held Node: %s, Mouse Delta: %s";
        setTitle(String.format(format, currentHoveredPanel, currentPreviousPanel, currentHoveredNode, currentHeldNode,
                mouseDelta.toString()));
    }
}