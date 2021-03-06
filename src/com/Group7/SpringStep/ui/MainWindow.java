package com.Group7.SpringStep.ui;

import java.io.*;

import java.util.*;

import java.awt.*;
import java.awt.event.*;

import javax.imageio.*;
import javax.swing.*;
import javax.swing.Timer;

import com.Group7.SpringStep.*;
import com.Group7.SpringStep.data.*;

/** Represents the Main Window that the user interacts with most of the time */
public class MainWindow extends JFrame implements ActionListener, AWTEventListener,
                                                    WindowListener, KeyListener,
                                                    FocusListener, MouseListener
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
    private AddTaskPopup taskEditorPopup;
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
    private JButton helpButton;

    private JTextField searchBarTextField;
    private ArrayList<SearchResult> searchResults;
    private SearchResultsPanel searchResultsPanel;
    private JButton searchButton;

    private PopupMenu trayMenu;
    private MenuItem openAppMenuItem;
    private MenuItem exitAppMenuItem;
    private SystemTray tray;
    private TrayIcon trayIcon;

    ///////////////////////////////////////////////// CONSTRUCTORS /////////////////////////////////////////////////
    public MainWindow()
    {
        boardOptionsMenu = new JPopupMenu();
        popupContainer = new PopupContainer(getContentPane());
        setGlassPane(popupContainer);
        profilePopup = new ProfilePopup(popupContainer, this);
        taskEditorPopup = new AddTaskPopup(popupContainer, this);
        searchResultsPanel = new SearchResultsPanel(popupContainer, this);

        autosaveTimer = new Timer(500, this);
        autosaveTimer.start();

        // Set window parameters
        setTitle("SpringStep");
        setIconImage(App.springStepImage);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        getContentPane().setBackground(new Color(255, 242, 238));
        addWindowListener(this);

        doSystemTrayStuff();

        addWindowEventListener();

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
            topBar.setOpaque(false);
            {
                boardName = new JLabel();
                boardName.setHorizontalAlignment(SwingConstants.CENTER);

                boardListButton = new JButton();
                boardListButton.addActionListener(this);
                Image boardListButtonImage = Utils.getScaledImage(App.resources.get("Dropdown_Icon_256.png"), 0.125f);
                if (boardListButtonImage != null) {
                    Utils.setButtonIcon(boardListButton, new ImageIcon(boardListButtonImage));
                }

                userButton = new JButton("User");
                userButton.addActionListener(this);
                Image userButtonImage = Utils.getScaledImage(App.resources.get("Profile_Icon_256.png"), 0.125);
                if (userButtonImage != null) {
                    Utils.setButtonIcon(userButton, new ImageIcon(userButtonImage));
                }

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
            contentPanel.setOpaque(false);
            Utils.setDebugVisible(contentPanel, Color.ORANGE);
            {
                JPanel searchArea = new JPanel(new GridBagLayout());
                searchArea.setOpaque(false);
                {
                    JPanel searchBar = new JPanel(new BorderLayout());
                    searchBar.setOpaque(false);
                    {
                        searchBarTextField = new JTextField();
                        searchBarTextField.addKeyListener(this);
                        searchBarTextField.addFocusListener(this);
                        popupContainer.setSearchBar(searchBarTextField);
                        searchButton = new JButton();
                        searchButton.addActionListener(this);
                        Image searchButtonImage = Utils.getScaledImage(App.resources.get("Search_Icon_256.png"), 0.125);
                        if (searchButtonImage != null) {
                            Utils.setButtonIcon(searchButton, new ImageIcon(searchButtonImage));
                        }
                        searchButton.setBorderPainted(true);
                        searchBar.add(searchBarTextField, BorderLayout.CENTER);
                        searchBar.add(searchButton, BorderLayout.LINE_END);
                    }
                    searchResultsPanel.setSearchBar(searchBarTextField);

                    helpButton = new JButton();
                    helpButton.addActionListener(this);
                    Image helpButtonImage = Utils.getScaledImage(App.resources.get("Help_Icon_256.png"), 0.125);
                    if (helpButtonImage != null) {
                        Utils.setButtonIcon(helpButton, new ImageIcon(helpButtonImage));
                    }
                    helpButton.setBorderPainted(true);

                    GridBagConstraints searchAreaConstraints = new GridBagConstraints();
                    searchAreaConstraints.gridx = 0;
                    searchAreaConstraints.weightx = 1;
                    searchAreaConstraints.anchor = GridBagConstraints.CENTER;
                    searchAreaConstraints.fill = GridBagConstraints.HORIZONTAL;
                    searchArea.add(searchBar, searchAreaConstraints);

                    searchAreaConstraints.weightx = 0;
                    searchAreaConstraints.gridx++;
                    searchAreaConstraints.anchor = GridBagConstraints.WEST;
                    searchAreaConstraints.fill = GridBagConstraints.NONE;
                    searchArea.add(helpButton, searchAreaConstraints);
                }

                timerPanel = new TimerPanel(trayIcon);
                timerPanel.setOpaque(false);

                JPanel boardPanel = new JPanel(new GridLayout(1, 3));
                boardPanel.setOpaque(false);
                Utils.setDebugVisible(boardPanel, Color.MAGENTA);
                {
                    toDoPanel = new ListPanel("To Do", "Add a task", new Color(247, 226, 203));
                    toDoAddTaskButton = toDoPanel.getAddTaskButton();
                    toDoAddTaskButton.addActionListener(this);
                    Utils.padJComponent(toDoPanel, 5, 5, 5, 5);

                    doingPanel = new ListPanel("Doing", "Add a task or drag one from \"To Do\"",
                            new Color(215, 204, 195));
                    doingAddTaskButton = doingPanel.getAddTaskButton();
                    doingAddTaskButton.addActionListener(this);
                    Utils.padJComponent(doingPanel, 5, 5, 5, 5);

                    donePanel = new ListPanel("Done", "Drag a task from \"To Do\" or \"Doing\"",
                            new Color(179, 195, 193));
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
                contentPanel.add(searchArea, contentPanelConstraints);

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

    ///////////////////////////////////////////////// SETTERS /////////////////////////////////////////////////
    /**
     * Loads user data into the main window's various components (Boards, tasks, etc.)
     * @param newUser The user's details to be loaded in
     */
    public void setUser(User newUser)
    {
        currentUser = newUser;
        ArrayList<BoardDetails> boards = currentUser.getBoards();
        setCurrentBoard(boards.get(0));
        updateBoardList();

        profilePopup.setUser(currentUser);
    }

    /**
     * Load's data contained in a board (Like tasks). Is also used for switching from one board to another
     * @param newBoard The board to load data from
     */
    public void setCurrentBoard(BoardDetails newBoard)
    {
        saveUserData(); // Save the data for the current board first (In case we're switching to another board)
        currentBoard = newBoard;
        searchButton.requestFocus(); // Give the search button focus so the user can just press "Space" to bring up the search panel
        resetMainWindow();
        boardName.setText(currentBoard.getName()); // Set the current board name to the name of the board being loaded

        // Add tasks from the new board's TO DO List
        ArrayList<TaskDetails> todoList = currentBoard.getTodoList();
        for (TaskDetails task : todoList) { toDoPanel.addTaskToList(new TaskNode(task, taskEditorPopup)); }
        
        // Add tasks from the new board's DONE List
        ArrayList<TaskDetails> doneList = currentBoard.getDoneList();
        for (TaskDetails task : doneList) { donePanel.addTaskToList(new TaskNode(task, taskEditorPopup)); }
    }

    ///////////////////////////////////////////////// EVENT HANDLERS /////////////////////////////////////////////////
    @Override
    public void windowIconified(WindowEvent e) { }

    @Override
    public void windowDeactivated(WindowEvent e) { }
    
    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void windowClosed(WindowEvent e) { getToolkit().removeAWTEventListener(this); }

    @Override
    public void windowClosing(WindowEvent e) { getToolkit().removeAWTEventListener(this); }

    @Override
    public void windowOpened(WindowEvent e) { addWindowEventListener(); }

    @Override
    public void windowDeiconified(WindowEvent e) { addWindowEventListener(); }

    @Override
    public void windowActivated(WindowEvent e) { addWindowEventListener(); }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        Object eventSource = e.getSource();
        if (eventSource == toDoAddTaskButton) { taskEditorPopup.addTask(toDoPanel); } 
        else if (eventSource == doingAddTaskButton) { taskEditorPopup.addTask(doingPanel); }

        if (eventSource == boardListButton) 
        {
            boardOptionsMenu.show(boardListButton.getParent(), boardListButton.getX() + boardListButton.getWidth(),
                    boardListButton.getY() + boardListButton.getHeight());
        }

        if (eventSource == userButton) { popupContainer.setPopup(profilePopup); }

        if (eventSource == helpButton) { new HelpWindow().setVisible(true); }

        if (eventSource == searchButton) { searchBarTextField.requestFocus(); }

        if (eventSource == openAppMenuItem) { setVisible(true); } 
        else if (eventSource == exitAppMenuItem) 
        {
            int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to close SpringStep?",
                    "Close SpringStep?",
                    JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) { close(); }
        }

        if (eventSource instanceof JMenuItem) { handleBoardSettingsMenuEvent(eventSource); }

        if (eventSource == autosaveTimer) { saveUserData(); }
    }

    @Override
    public void eventDispatched(AWTEvent caughtEvent) 
    {
        int eventId = caughtEvent.getID();
        Point newMouseScreenPosition = MouseInfo.getPointerInfo().getLocation();
        ArrayList<TaskNode> taskNodes = new ArrayList<>();
        {
            if (Utils.isMouseOverVisibleRect(newMouseScreenPosition, toDoPanel)) 
            {
                hoveredPanel = toDoPanel;
                taskNodes.addAll(toDoPanel.getTaskNodes());
            } else if (Utils.isMouseOverVisibleRect(newMouseScreenPosition, doingPanel)) 
            {
                hoveredPanel = doingPanel;
                taskNodes.addAll(doingPanel.getTaskNodes());
            } else if (Utils.isMouseOverVisibleRect(newMouseScreenPosition, donePanel)) 
            {
                hoveredPanel = donePanel;
                taskNodes.addAll(donePanel.getTaskNodes());
            } else { hoveredPanel = null; }
        }

        {
            hoveredTask = null;
            for (TaskNode taskNode : taskNodes) 
            {
                if (Utils.isMouseOverVisibleRect(newMouseScreenPosition, taskNode)) 
                {
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
                    if (hoveredTask == null) { break; }

                    previousPanel = hoveredPanel;
                    heldTask = hoveredTask;
                    hoveredTask = null;

                    Rectangle heldTaskBounds = heldTask.getBounds();
                    Point heldTaskScreenPosition = heldTask.getLocationOnScreen();
                    Point windowScreenPosition = getContentPane().getLocationOnScreen();
                    
                    heldTask.getParent().remove(heldTask);
                    /* Add the held task to the layered pane so it doesn't get auto-layouted by the MainWindow's
                    * layout manager and to also use the absolute positioning system (because the layeredPane
                    * doesn't have a LayoutManager) in order to move the held task by dragging */
                    getLayeredPane().add(heldTask);
                    
                    /* Manually calculate the held task's position, because we don't have a LayoutManager to do that 
                     * for us now */
                    Point heldTaskNewPosition = new Point(heldTaskScreenPosition.x - windowScreenPosition.x,
                            heldTaskScreenPosition.y - windowScreenPosition.y);
                    heldTaskBounds.setLocation(heldTaskNewPosition);
                    heldTask.setBounds(heldTaskBounds);
                    break;
                case MouseEvent.MOUSE_RELEASED:
                    if (heldTask == null) { break; }

                    getLayeredPane().remove(heldTask);
                    /* If the held task is "dropped" in any part of the main window that isn't a ListPanel,
                     * snap the held task back to its previous holder
                     */
                    if (hoveredPanel == null) { previousPanel.addTaskToList(heldTask); } 
                    else // Otherwise, just add the held task to the ListPanel where it was released on
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
            // We revalidate and repaint here because the user is most likely dragging a task
            revalidate();
            repaint();
        }

        if (heldTask != null) // If the user is holding a task
        {
            /* Add the mouse delta to the current position of the held task to simulate the "moving" 
             * of the held task with the mouse cursor.
             * 
             * This works because the mouse delta calculated at the end of every previous frame is added to the 
             * held task's current position to compensate for the movement of the user's mouse cursor. 
             * In layman's terms, move mouse 10 pixels to the right (10, 0), move the held task by 10 pixels to the right
            */
            Point currentLocation = heldTask.getLocation();
            heldTask.setLocation(new Point(currentLocation.x + mouseDelta.x, currentLocation.y + mouseDelta.y));
        }

        /* Calculate the mouse delta for task-dragging functionality. The mouse delta is (0, 0) when the user is 
        not moving the mouse. If the user moves the mouse, the mouse delta gets the value of the distance from where 
        the mouse was before (oldMouseScreenPosition) to where the mouse is currently (newMouseScreenPosition) in 
        screenspace coordinates */
        mouseDelta = new Point(newMouseScreenPosition.x - oldMouseScreenPosition.x,
                newMouseScreenPosition.y - oldMouseScreenPosition.y);

        /* By the end of this "frame", the new (actually current) mouse position is now old */
        oldMouseScreenPosition = newMouseScreenPosition;

        // updateDebugDetails();
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { searchButton.requestFocus(); }
        if (!popupContainer.isVisible()) { popupContainer.setPopup(searchResultsPanel); }
        compileSearchResults();
    }

    @Override
    public void focusGained(FocusEvent e) 
    {
        if(e.getSource() == searchBarTextField)
        {
            compileSearchResults();
            popupContainer.setPopup(searchResultsPanel);
        }
    }
    
    @Override
    public void focusLost(FocusEvent e) 
    {
        if(e.getSource() == searchBarTextField)
        {
            popupContainer.hidePopup();
            searchBarTextField.setText("");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) 
    {
        if (e.getSource() == trayIcon && e.getClickCount() > 1) {
            setVisible(true);
        }
    }

    ///////////////////////////////////////////////// INSTANCE METHODS /////////////////////////////////////////////////
    /** Resets this window to the initial blank state */
    public void resetMainWindow()
    {
        // Reset timer
        timerPanel.setWorkMode(true);
        timerPanel.stopTimer();
        timerPanel.resetTimer();

        // Clear task lists
        toDoPanel.clear();
        doingPanel.clear();
        donePanel.clear();
    }

    /** Updates the board list in the board switching menu. Used when boards are renamed or deleted */
    public void updateBoardList()
    {
        if (boardOptionsMenu.getComponentCount() > 0) { boardOptionsMenu.removeAll(); }

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

        if (boards.size() > 1)
        {
            deleteBoardMenuItem = new JMenuItem("Delete board");
            deleteBoardMenuItem.addActionListener(this);
            boardOptionsMenu.add(deleteBoardMenuItem);
        }
    }

    /** Starts or stops the timer based on the state of the DOING list */
    public void updateTimerBasedOnDoingList() 
    {
        boolean doingListHasNoTasks = doingPanel.getTaskNodes().size() == 0;
        if (hoveredPanel == toDoPanel && doingListHasNoTasks) { timerPanel.stopTimer(); }
        else if(hoveredPanel == doingPanel) { timerPanel.startTimer(); }
        else if (hoveredPanel == donePanel && doingListHasNoTasks) 
        {
            timerPanel.stopTimer();
            timerPanel.resetTimer();
        }
    }

    /** Compiles search results based on what's entered in the search bar and passes it to the {@code SearchResultsPanel}*/
    public void compileSearchResults()
    {
        searchResults = new ArrayList<>();
        ArrayList<BoardDetails> boards = currentUser.getBoards();
        for (BoardDetails board : boards) 
        {
            if(board == currentBoard) { continue; }

            ArrayList<TaskDetails> todoList = board.getTodoList();
            ArrayList<TaskDetails> doneList = board.getDoneList();
            ArrayList<TaskDetails> taskList = new ArrayList<>();

            if (todoList.size() > 0) { taskList.addAll(todoList); }
            if (doneList.size() > 0) { taskList.addAll(doneList); }

            String searchQuery = searchBarTextField.getText().toUpperCase();
            if (Utils.isTextEmpty(searchQuery))
            {
                searchResultsPanel.setResults(null);
                return;
            }

            if (taskList.size() > 0) 
            {
                for (TaskDetails task : taskList) 
                {
                    if (task.getName().toUpperCase().contains(searchQuery) && !Utils.isTextEmpty(searchQuery)) 
                    {
                        SearchResult newResult = new SearchResult();
                        newResult.setName(task.getName());
                        newResult.setBoardSource(board);
                        searchResults.add(newResult);
                    }
                }
            }
            
        }
        
        if (searchResults.size() > 0)
        {
            for (SearchResult result : searchResults) 
            {
                System.out.printf("%s from %s\n", result.getName(), result.getBoardSource().getName());
            }
            System.out.println();
        }
        else { System.out.println("No results currently"); }
        searchResultsPanel.setResults(searchResults);
    }

    /** Saves the user data to file. Used when the profile is edited, on switching boards, and every autosave tick */
    public void saveUserData()
    {
        if (currentBoard == null) { return; }

        saveTasksToBoard();
        DataManager dataManager = new DataManager();
        try { dataManager.saveUserData(currentUser, true); } 
        catch (Exception e) { e.printStackTrace(); }
        // System.out.println("Saved user data: " + LocalTime.now().toString());
    }

    /** Saves the currently displayed tasks to the current board */
    public void saveTasksToBoard()
    {
        ArrayList<TaskNode> toDoNodes = toDoPanel.getTaskNodes();
        ArrayList<TaskNode> doingNodes = doingPanel.getTaskNodes();
        ArrayList<TaskNode> doneNodes = donePanel.getTaskNodes();
        ArrayList<TaskDetails> todoListSave = new ArrayList<>();

        // Store to do tasks
        if (toDoNodes.size() > 0) 
        {
            for (TaskNode taskDisplay : toDoNodes) { todoListSave.add(taskDisplay.getTaskDetails()); }
        }

        /* Store doing tasks. If the user closes the app while doing a task, it safe to assume
           they aren't done with those tasks. Hence, why we store these incomplete tasks to "To Do" for later.
        */
        if (doingNodes.size() > 0) 
        {
            for (TaskNode taskDisplay : doingNodes) { todoListSave.add(taskDisplay.getTaskDetails()); }
        }

        currentBoard.setTodoList(todoListSave);

        // Store done tasks
        ArrayList<TaskDetails> doneListSave = new ArrayList<>();
        if (doneNodes.size() > 0) 
        {
            for (TaskNode taskDisplay : doneNodes) { doneListSave.add(taskDisplay.getTaskDetails()); }
        }
        currentBoard.setDoneList(doneListSave);
    }

    /** Logs the current user out and opens the Login Window */
    public void logOut() 
    {
        getToolkit().removeAWTEventListener(this);
        Utils.moveToNewWindow(this, new LoginWindow());
        if (trayIcon != null) { tray.remove(trayIcon); }
        autosaveTimer.stop();
    }

    /** Completely closes the application */
    public void close()
    {
        getToolkit().removeAWTEventListener(this);
        setVisible(false);
        if (trayIcon != null) { tray.remove(trayIcon); }
        dispose();
        System.exit(0);
    }

    /** Enters the app's icon to the system tray to enable notifications and "hiding app to tray on close" */
    public void doSystemTrayStuff()
    {
        if (!SystemTray.isSupported()) { return; }

        trayMenu = new PopupMenu();
        openAppMenuItem = new MenuItem("Open");
        openAppMenuItem.addActionListener(this);
        trayMenu.add(openAppMenuItem);
        
        trayMenu.addSeparator();
        
        exitAppMenuItem = new MenuItem("Exit");
        exitAppMenuItem.addActionListener(this);
        trayMenu.add(exitAppMenuItem);
        
        try 
        {
            trayIcon = new TrayIcon(ImageIO.read(App.resources.get("SpringStep_Logo.png")));
        } catch (IOException e1) { e1.printStackTrace(); }
        
        trayIcon.setImageAutoSize(true);
        trayIcon.setPopupMenu(trayMenu);
        trayIcon.addMouseListener(this);
        
        tray = SystemTray.getSystemTray();
        try { tray.add(trayIcon); } 
        catch (AWTException e) { e.printStackTrace(); }
    }
    
    /** Adds this window as AWTEventListener to listen for events used in task-dragging functionality */
    private void addWindowEventListener() 
    {
        long eventMask = AWTEvent.MOUSE_MOTION_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK;
        getToolkit().addAWTEventListener(this, eventMask);
    }

    /**
     * Handles events caused by clicking menu items on the board settings menu
     * @param eventSource The source of the event
     */
    private void handleBoardSettingsMenuEvent(Object eventSource) 
    {
        if (eventSource == createBoardMenuItem) 
        {
            BoardNamePrompterDialog namePrompter = new BoardNamePrompterDialog();
            boolean properlyResponded = false;
            do 
            {
                int response = namePrompter.showDialog("Please give a name for the new board", "New board",
                        "New Board",
                        null);
                if (response == BoardNamePrompterDialog.RESPONSE_SUBMITTED) 
                {
                    boolean nameIsValid = true;
                    String enteredName = namePrompter.getText();
                    if (Utils.isTextEmpty(enteredName)) 
                    {
                        JOptionPane.showMessageDialog(this, "Please enter a name for the board.",
                                "Warning: Can't create a nameless board", JOptionPane.WARNING_MESSAGE);
                        nameIsValid = false;
                    }

                    ArrayList<BoardDetails> boards = currentUser.getBoards();

                    for (BoardDetails boardDetails : boards) 
                    {
                        if (boardDetails.getName().equals(enteredName)) 
                        {
                            String warningTitle = "Warning: Board name already taken";
                            String warningMessage = "That name is already taken. Please enter a different name";
                            JOptionPane.showMessageDialog(this, warningMessage, warningTitle,
                                    JOptionPane.WARNING_MESSAGE);
                            nameIsValid = false;
                        }
                    }

                    if (nameIsValid) 
                    {
                        properlyResponded = true;
                        BoardDetails newBoard = new BoardDetails(enteredName);
                        boards.add(newBoard);
                        setCurrentBoard(newBoard);
                        updateBoardList();
                        JOptionPane.showMessageDialog(this, "The board has been successfully created.",
                                "New board created", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                else { properlyResponded = true; }
            } while (!properlyResponded);
        } 
        else if (eventSource == deleteBoardMenuItem) 
        {
            int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this board?",
                    "Delete current board?", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) 
            {
                ArrayList<BoardDetails> boards = currentUser.getBoards();
                boards.remove(currentBoard);
                updateBoardList();
                setCurrentBoard(boards.get(0));
            }
        } else if (eventSource == renameBoardMenuItem) 
        {
            BoardNamePrompterDialog namePrompter = new BoardNamePrompterDialog();
            boolean properlyResponded = false;
            do {
                int response = namePrompter.showDialog("Enter a new name for the board", "Rename board",
                        currentBoard.getName(),
                        null);
                if (response == BoardNamePrompterDialog.RESPONSE_SUBMITTED) 
                {
                    boolean nameIsValid = true;
                    String enteredName = namePrompter.getText();
                    if (Utils.isTextEmpty(enteredName)) 
                    {
                        JOptionPane.showMessageDialog(this, "Please enter a name for the board.",
                                "Warning: Can't rename board", JOptionPane.WARNING_MESSAGE);
                        nameIsValid = false;
                    }

                    ArrayList<BoardDetails> boards = currentUser.getBoards();
                    for (BoardDetails boardDetails : boards) 
                    {
                        if (boardDetails.getName().equals(enteredName)) 
                        {
                            String warningTitle = "Warning: Board name already taken";
                            String warningMessage = "That name is already taken. Please enter a different name";
                            JOptionPane.showMessageDialog(this, warningMessage, warningTitle,
                                    JOptionPane.WARNING_MESSAGE);
                            nameIsValid = false;
                        }
                    }

                    if (nameIsValid) 
                    {
                        properlyResponded = true;
                        currentBoard.setName(enteredName);
                        setCurrentBoard(currentBoard);
                        updateBoardList();
                        JOptionPane.showMessageDialog(this, "The board has been successfully renamed.",
                                "Current board was renamed", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else { properlyResponded = true; }
            } while (!properlyResponded);
        } 
        else // If the user selects a board instead of the "Create", "Rename", "Delete" board options, switch to that board
        {
            JMenuItem selectedBoard = (JMenuItem) eventSource;
            ArrayList<BoardDetails> boards = currentUser.getBoards();
            for (BoardDetails boardDetails : boards) 
            {
                if (selectedBoard.getText().equals(boardDetails.getName())) 
                {
                    setCurrentBoard(boardDetails);
                    break;
                }
            }
        }
    }

    private void updateDebugDetails() 
    {
        // Title details
        String currentHoveredPanel = "NONE";
        if (hoveredPanel == toDoPanel) { currentHoveredPanel = "To Do"; } 
        else if (hoveredPanel == doingPanel) { currentHoveredPanel = "Doing"; } 
        else if (hoveredPanel == donePanel) { currentHoveredPanel = "Done"; }

        String currentPreviousPanel = "NONE";
        if (previousPanel == toDoPanel) { currentPreviousPanel = "To Do"; } 
        else if (previousPanel == doingPanel) { currentPreviousPanel = "Doing"; } 
        else if (previousPanel == donePanel) { currentPreviousPanel = "Done"; }

        String currentHoveredNode = "NONE";
        if (hoveredTask != null) { currentHoveredNode = hoveredTask.getTaskNameArea().getText(); }
        
        String currentHeldNode = "NONE";
        if (heldTask != null) { currentHeldNode = heldTask.getTaskNameArea().getText(); }
        String format = "Hovered Panel: %s, Previous Panel: %s, Hovered Node: %s, Held Node: %s, Mouse Delta: %s, Screen Position: %s";
        setTitle(String.format(format, currentHoveredPanel, currentPreviousPanel, currentHoveredNode, currentHeldNode,
                mouseDelta.toString(), getLocationOnScreen().toString()));
    }
}