package com.Group7.SpringStep.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import com.Group7.SpringStep.*;
import com.Group7.SpringStep.data.*;

public class SearchResultsPanel extends JPanel implements MouseListener
{
    private static final String NO_QUERY_TEXT = "Type in the search bar to search tasks from other boards.";
    private static final String NO_RESULTS_TEXT = "No matching results found in other boards.";
    private JPanel innerCountainer;
    private JLabel textMessage;
    private GridBagConstraints innerContainerConstraints;
    private int resultLimit = 10;
    private JPanel searchBar;
    private PopupContainer popupHandler;
    private MainWindow mainWindow;
    private ArrayList<SearchResult> currentResults;

    public SearchResultsPanel(PopupContainer popupContainer, MainWindow window)
    {
        popupHandler = popupContainer;
        mainWindow = window;
        setLayout(new GridBagLayout());
        setOpaque(false);
        {
            JPanel outerContainer = new RoundedPanel(new GridBagLayout());
            {
                innerCountainer = new JPanel(new GridBagLayout());
                innerCountainer.setOpaque(false);
                Utils.padJComponent(innerCountainer, 5, 5, 5, 5);
                {
                    textMessage = new JLabel(NO_QUERY_TEXT);

                    innerContainerConstraints = new GridBagConstraints();
                    innerContainerConstraints.anchor = GridBagConstraints.NORTH;
                    innerContainerConstraints.fill = GridBagConstraints.HORIZONTAL;
                    innerContainerConstraints.weightx = 1;
                    innerCountainer.add(textMessage, innerContainerConstraints);
                }

                GridBagConstraints outerContainerConstraints = new GridBagConstraints();
                outerContainerConstraints.anchor = GridBagConstraints.NORTH;
                outerContainerConstraints.fill = GridBagConstraints.HORIZONTAL;
                outerContainerConstraints.weightx = 1;
                outerContainerConstraints.weighty = 1;
                outerContainer.add(innerCountainer, outerContainerConstraints);
            }

            GridBagConstraints searchPanelConstraints = new GridBagConstraints();
            searchPanelConstraints.anchor = GridBagConstraints.NORTH;
            searchPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
            searchPanelConstraints.weightx = 1;
            searchPanelConstraints.weighty = 1;
            add(outerContainer, searchPanelConstraints);
        }
    }

    @Override
    public Dimension getPreferredSize() 
    {
        return new Dimension(searchBar.getWidth(), popupHandler.getHeight());
    }

    public Point getPreferredLocation()
    {
        Point searchBarOnScreen = searchBar.getLocationOnScreen();
        Point newLocation = new Point();
        Point contentPaneScreenPos = mainWindow.getContentPane().getLocationOnScreen();
        newLocation.setLocation(searchBarOnScreen.getX() - contentPaneScreenPos.getX(),
                searchBarOnScreen.getY() - contentPaneScreenPos.getY() + searchBar.getHeight());
        return newLocation;
    }

    public void setSearchBar(JPanel newSearchBar)
    {
        this.searchBar = newSearchBar;
    }

    public void setText(String text)
    {
        textMessage.setText(text);
    }

    public void clearResults()
    {
        innerCountainer.removeAll();
        innerContainerConstraints.gridy = 0;
        innerCountainer.add(textMessage, innerContainerConstraints);
        textMessage.setVisible(true);
        if(currentResults != null && currentResults.size() > 0)
            currentResults.clear();
    }

    public void setResults(ArrayList<SearchResult> results)
    {
        clearResults();
        currentResults = results;
        if (currentResults == null)
        {
            textMessage.setText(NO_QUERY_TEXT);
            return;
        }
        
        if(currentResults.size() > 0)
        {
            textMessage.setVisible(false);
            for (int resultIndex = 0; resultIndex < resultLimit && resultIndex < currentResults.size(); resultIndex++) 
            {
                SearchResult currentResult = currentResults.get(resultIndex);
                innerContainerConstraints.gridy++;
                JButton resultButton = new JButton(
                        currentResult.getName() + " from " + currentResult.getBoardSource().getName());
                resultButton.addMouseListener(this);
                resultButton.setHorizontalAlignment(SwingConstants.LEFT);
                resultButton.setContentAreaFilled(false);
                innerCountainer.add(resultButton, innerContainerConstraints);
            }
        }
        else
        {
            textMessage.setText(NO_RESULTS_TEXT);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) 
    {
        Component[] components = innerCountainer.getComponents();
        for (int i = 0; i < components.length; i++) 
        {
            if(components[i].equals(e.getSource()))
            {
                mainWindow.setCurrentBoard(currentResults.get(i - 1).getBoardSource());
                popupHandler.hidePopup();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
}
