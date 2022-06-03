package com.Group7.SpringStep.ui;

import java.awt.*;
import java.awt.event.*;

import java.util.*;

import javax.swing.*;
import javax.swing.Timer;

import com.Group7.SpringStep.*;
import com.Group7.SpringStep.data.*;

public class MainWindow extends JFrame implements ActionListener, AWTEventListener, WindowListener
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
    private TimerPanel timerPanel;
    private JButton userButton;
    private PopupContainer popupContainer;
    private ProfilePopup profilePopup;

    private User currentUser;
    private BoardDetails currentBoard;
    private JLabel windowTitle;

    private Timer autosaveTimer;

    public MainWindow()
    {
        popupContainer = new PopupContainer(getContentPane());
        setGlassPane(popupContainer);
        profilePopup = new ProfilePopup(popupContainer, this);

        autosaveTimer = new Timer(1000 * 10, this);
        autosaveTimer.start();

        // Set window parameters
        setTitle("SpringStep");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(this);
        
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
            JPanel topBar = new JPanel(new GridBagLayout());
            {
                windowTitle = new JLabel();
                windowTitle.setHorizontalAlignment(SwingConstants.CENTER);

                JButton boardListButton = new JButton("v");
                userButton = new JButton("User");
                userButton.addActionListener(this);

                GridBagConstraints titleBarConstraints = new GridBagConstraints();
                titleBarConstraints.weightx = 1;

                titleBarConstraints.gridx = 0;
                titleBarConstraints.anchor = GridBagConstraints.EAST;
                topBar.add(windowTitle, titleBarConstraints);

                titleBarConstraints.anchor = GridBagConstraints.WEST;
                titleBarConstraints.gridx = 1;
                topBar.add(boardListButton, titleBarConstraints);

                titleBarConstraints.weightx = 0;

                titleBarConstraints.gridx = 2;
                topBar.add(userButton, titleBarConstraints);
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

                timerPanel = new TimerPanel();

                JPanel boardPanel = new JPanel(new GridLayout(1, 3));
                Utils.setDebugVisible(boardPanel, Color.MAGENTA);
                {
                    toDoPanel = new ListPanel("To Do", "Add a task", Color.RED);
                    toDoAddTaskButton = toDoPanel.getAddTaskButton();
                    toDoAddTaskButton.addActionListener(this);
                    Utils.padJComponent(toDoPanel, 5, 5, 5, 5);

                    doingPanel = new ListPanel("Doing", "Add a task or drag one from \"To Do\"", Color.BLUE);
                    doingAddTaskButton = doingPanel.getAddTaskButton();
                    doingAddTaskButton.addActionListener(this);
                    Utils.padJComponent(doingPanel, 5, 5, 5, 5);

                    donePanel = new ListPanel("Done", "Drag a task from \"To Do\" or \"Doing\"", Color.YELLOW);
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
            add(topBar, BorderLayout.PAGE_START);
            add(contentPanel, BorderLayout.CENTER);
            add(shortcutsPanel, BorderLayout.PAGE_END);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        Object eventSource = e.getSource();
        if(eventSource == toDoAddTaskButton)
        {
            popupContainer.setPopup(new AddTaskPopup(popupContainer, this, new TaskDetails(), toDoPanel));
        }
        else if(eventSource == doingAddTaskButton)
        {
            popupContainer.setPopup(new AddTaskPopup(popupContainer, this, new TaskDetails(), doingPanel));
        }

        if(eventSource == userButton)
        {
            if (profilePopup == null) {
                profilePopup = new ProfilePopup(popupContainer, this);
            }
            popupContainer.setPopup(profilePopup);
        }
        
        if(eventSource == autosaveTimer)
        {
            saveUserData();
        }
    }

    @Override
    public void eventDispatched(AWTEvent caughtEvent) 
    {
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

        boolean eventIsntFromButton = !(caughtEvent.getSource() instanceof JButton);
        if (eventIsntFromButton && !popupContainer.isVisible()) 
        {
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
                    Point windowScreenPosition = getContentPane().getLocationOnScreen();
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
                        updateTimerBasedOnDoingList();
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

        // updateDebugDetails();
    }

    public void updateTimerBasedOnDoingList() 
    {
        boolean doingListHasNoTasks = doingPanel.getTaskNodes().size() == 0;
        if (hoveredPanel == toDoPanel)
        {
            if(doingListHasNoTasks)
            {
                timerPanel.stopTimer();
            }
        }
        else if(hoveredPanel == doingPanel)
        {
            timerPanel.startTimer();
        }
        else if(hoveredPanel == donePanel)
        {
            if(doingListHasNoTasks)
            {
                timerPanel.stopTimer();
                timerPanel.resetTimer();
            }
        }
    }

    private void updateDebugDetails() 
    {
        // Title details
        String currentHoveredPanel = "NONE";
        if (hoveredPanel == toDoPanel) {
            currentHoveredPanel = "To Do";
        } else if (hoveredPanel == doingPanel) {
            currentHoveredPanel = "Doing";
        } else if (hoveredPanel == donePanel) {
            currentHoveredPanel = "Done";
        }
        String currentPreviousPanel = "NONE";
        if (previousPanel == toDoPanel) {
            currentPreviousPanel = "To Do";
        } else if (previousPanel == doingPanel) {
            currentPreviousPanel = "Doing";
        } else if (previousPanel == donePanel) {
            currentPreviousPanel = "Done";
        }
        String currentHoveredNode = "NONE";
        if (hoveredTask != null) {
            currentHoveredNode = hoveredTask.getTaskNameArea().getText();
        }
        String currentHeldNode = "NONE";
        if (heldTask != null) {
            currentHeldNode = heldTask.getTaskNameArea().getText();
        }
        String format = "Hovered Panel: %s, Previous Panel: %s, Hovered Node: %s, Held Node: %s, Mouse Delta: %s, Screen Position: %s";
        setTitle(String.format(format, currentHoveredPanel, currentPreviousPanel, currentHoveredNode, currentHeldNode,
                mouseDelta.toString(), getLocationOnScreen().toString()));
    }
    
    public void setUser(User newUser)
    {
        currentUser = newUser;
        setCurrentBoard(currentUser.getBoards().get(0));
        profilePopup.setUser(currentUser);
    }

    public void setCurrentBoard(BoardDetails newBoard)
    {
        currentBoard = newBoard;
        windowTitle.setText(currentBoard.getName());
        ArrayList<TaskDetails> todoList = currentBoard.getTodoList();
        for (TaskDetails task : todoList) 
        {
            toDoPanel.addTaskToList(new TaskNode(task));
        }

        ArrayList<TaskDetails> doneList = currentBoard.getDoneList();
        for (TaskDetails task : doneList) 
        {
            donePanel.addTaskToList(new TaskNode(task));
        }
    }

    public void saveTasksToBoard()
    {
        ArrayList<TaskNode> toDoNodes = toDoPanel.getTaskNodes();
        ArrayList<TaskNode> doingNodes = doingPanel.getTaskNodes();
        ArrayList<TaskNode> doneNodes = donePanel.getTaskNodes();
        ArrayList<TaskDetails> todoListSave = new ArrayList<>();

        // Store to do tasks
        if (toDoNodes.size() > 0) {
            for (TaskNode taskDisplay : toDoNodes) {
                todoListSave.add(taskDisplay.getTaskDetails());
            }
        }

        /* Store doing tasks. If the user closes the app while doing a task, it safe to assume
           they aren't done with those tasks. Hence, why we store these incomplete tasks to "To Do" for later.
        */
        if (doingNodes.size() > 0) {
            for (TaskNode taskDisplay : doingNodes) {
                todoListSave.add(taskDisplay.getTaskDetails());
            }
        }

        currentBoard.setTodoList(todoListSave);

        // Store done tasks
        if (doneNodes.size() > 0) {
            ArrayList<TaskDetails> doneListSave = new ArrayList<>();
            for (TaskNode taskDisplay : doneNodes) {
                doneListSave.add(taskDisplay.getTaskDetails());
            }
            currentBoard.setDoneList(doneListSave);
        }

        System.out.println();
    }

    public void saveUserData()
    {
        saveTasksToBoard();
        DataManager dataManager = new DataManager();
        try {
            dataManager.saveUserData(currentUser, true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void windowClosed(WindowEvent e) 
    {
        getToolkit().removeAWTEventListener(this);
    }

    @Override
    public void windowClosing(WindowEvent e) { }

    @Override
    public void windowOpened(WindowEvent e) { }

    @Override
    public void windowIconified(WindowEvent e) { }

    @Override
    public void windowDeiconified(WindowEvent e) { }

    @Override
    public void windowActivated(WindowEvent e) { }

    @Override
    public void windowDeactivated(WindowEvent e) { }
}