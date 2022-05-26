package com.Group7.SpringStep.UI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.Group7.SpringStep.*;

public class MainWindow extends JFrame
{
    private ListPanel toDoPanel;
    private ListPanel doingPanel;
    private ListPanel donePanel;
    

    public MainWindow()
    {
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

                JPanel timerPanel = new TimerPanel();

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
}