package com.Group7.SpringStep.UI;

import java.awt.*;
import java.awt.event.*;

import java.time.*;

import javax.swing.*;

import com.Group7.SpringStep.*;

public class MainWindow extends JFrame implements ActionListener
{
    private Timer timer;
    private int timerTick = 10;
    private boolean workMode = true;
    private LocalTime remainingTime;

    private final LocalTime DEFAULT_WORK_DURATION = LocalTime.of(0, 2);
    private final LocalTime DEFAULT_BREAK_DURATION = LocalTime.of(0, 1);

    private ListPanel toDoPanel;
    private ListPanel doingPanel;
    private ListPanel donePanel;
    private JLabel timerLabel;
    private JButton startStopTimerButton;
    private JButton resetTimerButton;

    public MainWindow()
    {
        // Set timer
        remainingTime = DEFAULT_WORK_DURATION;
        timer = new Timer(1000, this);
        timerLabel = new JLabel(formatTime(remainingTime));

        // Set window parameters
        setTitle("SpringStep");

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

                JPanel timerPanel = new JPanel(new GridBagLayout());
                {
                    timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    timerLabel.setFont(new Font(timerLabel.getFont().getFontName(), Font.PLAIN, 21));
                    timerLabel.setForeground(Color.RED);
                    timerLabel.setOpaque(false);

                    startStopTimerButton = new JButton("Start");
                    startStopTimerButton.addActionListener(this);
                    resetTimerButton = new JButton("Reset");
                    resetTimerButton.addActionListener(this);
                    
                    GridBagConstraints timerPanelConstraints = new GridBagConstraints();
                    timerPanelConstraints.anchor = GridBagConstraints.CENTER;
                    timerPanelConstraints.gridx = 0;

                    timerPanelConstraints.gridwidth = 2;
                    timerPanel.add(timerLabel, timerPanelConstraints);

                    timerPanelConstraints.gridy = 1;
                    timerPanelConstraints.gridwidth = 1;
                    timerPanel.add(startStopTimerButton, timerPanelConstraints);

                    timerPanelConstraints.gridx = 1;
                    timerPanel.add(resetTimerButton, timerPanelConstraints);
                }

                JPanel boardPanel = new JPanel(new GridLayout(1, 3));
                Utils.setDebugVisible(boardPanel, Color.MAGENTA);
                {
                    toDoPanel = new ListPanel("To Do", Color.RED);
                    Utils.padJComponent(toDoPanel, 5, 5, 5, 5);
                    
                    doingPanel = new ListPanel("Doing", Color.BLUE);
                    Utils.padJComponent(doingPanel, 5, 5, 5, 5);
                    
                    donePanel = new ListPanel("Done", Color.YELLOW);
                    Utils.padJComponent(donePanel,  5,  5,  5,  5);

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
        if (eventSource == timer) 
        {
            if (!remainingTime.equals(LocalTime.MIN)) 
            {
                remainingTime = remainingTime.minusSeconds(timerTick);
            } else 
            {
                if (workMode) 
                {
                    timerLabel.setForeground(Color.GREEN);
                    remainingTime = DEFAULT_BREAK_DURATION;
                } else 
                {
                    timerLabel.setForeground(Color.RED);
                    remainingTime = DEFAULT_WORK_DURATION;
                }
                workMode = !workMode;
            }
            timerLabel.setText(formatTime(remainingTime));
        }
        
        if (eventSource == startStopTimerButton) 
        {
            if (timer.isRunning()) 
            {
                timer.stop();
                startStopTimerButton.setText("Start");
            } else 
            {
                timer.start();
                startStopTimerButton.setText("Stop");
            }
        } else if(eventSource == resetTimerButton)
        {
            if(workMode)
            {
                remainingTime = DEFAULT_WORK_DURATION;
            }
            else
            {
                remainingTime = DEFAULT_BREAK_DURATION;
            }
            timerLabel.setText(formatTime(remainingTime));
        }
    }
    
    private String formatTime(LocalTime time)
    {
        return String.format("%02d:%02d:%02d", time.getHour(), time.getMinute(), time.getSecond());
    }
}