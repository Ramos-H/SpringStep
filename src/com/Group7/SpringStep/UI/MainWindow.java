package com.Group7.SpringStep.ui;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalTime;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;

import com.Group7.SpringStep.*;
import com.Group7.SpringStep.data.*;

public class MainWindow extends JFrame implements ActionListener, AWTEventListener, WindowListener
{
    private ListPanel toDoPanel, doingPanel, donePanel;
    private JButton toDoAddTaskButton, doingAddTaskButton;
    
    private TaskNode heldTask, hoveredTask;
    private ListPanel hoveredPanel, previousPanel;
    private Point mouseDelta = new Point();
    private Point oldMouseScreenPosition = new Point();

    private TimerPanel timerPanel;
    private JButton userButton;

    private ProfilePopup profilePopup;
    private PopupContainer popupContainer;

    private User currentUser;

    private JLabel boardName;
    private BoardDetails currentBoard;
    private JButton boardListButton;
    private JPopupMenu boardOptionsMenu;

    private Timer autosaveTimer;
    private JMenuItem createBoardMenuItem;
    private JMenuItem renameBoardMenuItem;
    private JMenuItem deleteBoardMenuItem;

    public MainWindow()
    {
        boardOptionsMenu = new JPopupMenu();
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
                boardName = new JLabel();
                boardName.setHorizontalAlignment(SwingConstants.CENTER);

                boardListButton = new JButton("v");
                boardListButton.addActionListener(this);

                userButton = new JButton("User");
                userButton.addActionListener(this);

                GridBagConstraints titleBarConstraints = new GridBagConstraints();
                titleBarConstraints.weightx = 1;

                titleBarConstraints.gridx = 0;
                titleBarConstraints.anchor = GridBagConstraints.EAST;
                topBar.add(boardName, titleBarConstraints);

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
                    donePanel.getAddTaskButton().setVisible(false);

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

        if (eventSource == boardListButton)
        {
            boardOptionsMenu.show(boardListButton.getParent(), boardListButton.getX() + boardListButton.getWidth(), boardListButton.getY() + boardListButton.getHeight());
        }

        if(eventSource == userButton)
        {
            if (profilePopup == null) {
                profilePopup = new ProfilePopup(popupContainer, this);
            }
            popupContainer.setPopup(profilePopup);
        }

        if (eventSource instanceof JMenuItem)
        {
            if(eventSource == createBoardMenuItem)
            {
                saveUserData();
                BoardDetails newBoard = new BoardDetails("Newer board" + currentUser.getBoards().size());
                ArrayList<BoardDetails> boards = currentUser.getBoards();
                boards.add(newBoard);
                updateBoardList();
                setCurrentBoard(newBoard);
            }
            else if(eventSource == deleteBoardMenuItem)
            {
                int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this board?",
                        "Delete current board?", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    ArrayList<BoardDetails> boards = currentUser.getBoards();
                    boards.remove(currentBoard);
                    updateBoardList();
                    setCurrentBoard(boards.get(0));
                }
            }
            else
            {
                JMenuItem selectedBoard = (JMenuItem) eventSource;
                ArrayList<BoardDetails> boards = currentUser.getBoards();
                for (BoardDetails boardDetails : boards) 
                {
                    if(selectedBoard.getText().equals(boardDetails.getName()))
                    {
                        setCurrentBoard(boardDetails);
                        break;
                    }
                }
            }
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
        Point newMouseScreenPosition = MouseInfo.getPointerInfo().getLocation();
        ArrayList<TaskNode> taskNodes = new ArrayList<>();
        {
            if (Utils.isMouseOverVisibleRect(newMouseScreenPosition, toDoPanel)) {
                hoveredPanel = toDoPanel;
                taskNodes.addAll(toDoPanel.getTaskNodes());
            } else if (Utils.isMouseOverVisibleRect(newMouseScreenPosition, doingPanel)) {
                hoveredPanel = doingPanel;
                taskNodes.addAll(doingPanel.getTaskNodes());
            } else if (Utils.isMouseOverVisibleRect(newMouseScreenPosition, donePanel)) {
                hoveredPanel = donePanel;
                taskNodes.addAll(donePanel.getTaskNodes());
            } else {
                hoveredPanel = null;
            }
        }

        {
            hoveredTask = null;
            for (TaskNode taskNode : taskNodes) {
                if (Utils.isMouseOverVisibleRect(newMouseScreenPosition, taskNode)) {
                    hoveredTask = taskNode;
                    break;
                }
            }
        }

        boolean eventIsntFromButton = !(caughtEvent.getSource() instanceof JButton);
        if (eventIsntFromButton && !popupContainer.isVisible()) {
            switch (eventId) {
                case MouseEvent.MOUSE_PRESSED:
                    if (hoveredTask == null) {
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
                    if (heldTask == null) {
                        break;
                    }

                    getLayeredPane().remove(heldTask);
                    if (hoveredPanel == null) {
                        previousPanel.addTaskToList(heldTask);
                    } else {
                        hoveredPanel.addTaskToList(heldTask);
                        updateTimerBasedOnDoingList();
                    }
                    previousPanel = null;

                    heldTask = null;
                    hoveredTask = null;
                    break;
            }
        }

        if (eventId == MouseEvent.MOUSE_PRESSED || eventId == MouseEvent.MOUSE_RELEASED) {
            revalidate();
            repaint();
        }

        if (heldTask != null) {
            Point currentLocation = heldTask.getLocation();
            heldTask.setLocation(new Point(currentLocation.x + mouseDelta.x, currentLocation.y + mouseDelta.y));
        }

        mouseDelta = new Point(newMouseScreenPosition.x - oldMouseScreenPosition.x,
                newMouseScreenPosition.y - oldMouseScreenPosition.y);

        oldMouseScreenPosition = newMouseScreenPosition;

        // updateDebugDetails();
    }
    
    public void resetMainWindow()
    {
        // reset timer
        timerPanel.setWorkMode(true);
        timerPanel.resetTimer();
        // clear task lists
        toDoPanel.clear();
        doingPanel.clear();
        donePanel.clear();

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
        ArrayList<BoardDetails> boards = currentUser.getBoards();
        setCurrentBoard(boards.get(0));
        updateBoardList();

        profilePopup.setUser(currentUser);
    }

    public void setCurrentBoard(BoardDetails newBoard)
    {
        currentBoard = newBoard;
        resetMainWindow();
        boardName.setText(currentBoard.getName());
        ArrayList<TaskDetails> todoList = currentBoard.getTodoList();
        for (TaskDetails task : todoList) {
            toDoPanel.addTaskToList(new TaskNode(task));
        }

        ArrayList<TaskDetails> doneList = currentBoard.getDoneList();
        for (TaskDetails task : doneList) {
            donePanel.addTaskToList(new TaskNode(task));
        }
        saveUserData();
    }
    
    public void updateBoardList()
    {
        if (boardOptionsMenu.getComponentCount() > 0)
        {
            boardOptionsMenu.removeAll();
        }

        ArrayList<BoardDetails> boards = currentUser.getBoards();
        for (BoardDetails boardDetails : boards) 
        {
            JMenuItem newBoardEntry = new JMenuItem(boardDetails.getName());
            newBoardEntry.addActionListener(this);
            boardOptionsMenu.add(newBoardEntry);
        }

        boardOptionsMenu.add(new JPopupMenu.Separator());

        createBoardMenuItem = new JMenuItem("Create board...");
        createBoardMenuItem.addActionListener(this);
        boardOptionsMenu.add(createBoardMenuItem);

        renameBoardMenuItem = new JMenuItem("Rename board");
        renameBoardMenuItem.addActionListener(this);
        boardOptionsMenu.add(renameBoardMenuItem);

        deleteBoardMenuItem = new JMenuItem("Delete board");
        deleteBoardMenuItem.addActionListener(this);
        boardOptionsMenu.add(deleteBoardMenuItem);
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
    }

    public void saveUserData()
    {
        saveTasksToBoard();
        DataManager dataManager = new DataManager();
        try 
        {
            dataManager.saveUserData(currentUser, true);
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
        System.out.println("Saved user data: " + LocalTime.now().toString());
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