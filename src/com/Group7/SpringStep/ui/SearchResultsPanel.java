package com.Group7.SpringStep.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import com.Group7.SpringStep.*;
import com.Group7.SpringStep.data.*;

/** Represents the search results panel that contains the search results entries that the user can click on */
public class SearchResultsPanel extends JPanel implements MouseListener
{
    private static final String NO_QUERY_TEXT = "Type in the search bar to search tasks from other boards.";
    private static final String NO_RESULTS_TEXT = "No matching results found in other boards.";
    private JPanel innerCountainer;
    private JLabel textMessage;
    private GridBagConstraints innerContainerConstraints;
    private int resultLimit = 10;
    private JComponent searchBar;
    private PopupContainer popupHandler;
    private MainWindow mainWindow;
    private ArrayList<SearchResult> currentResults;

    ///////////////////////////////////////////////// CONSTRUCTORS /////////////////////////////////////////////////
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

    ///////////////////////////////////////////////// GETTERS /////////////////////////////////////////////////
    /** Gets the supposed size of the search bar results panel's stretch container. 
     * @return A {@code Dimension} whose size is the width of the search bar and the height is from the search 
     * bar's bottom edge down to the button edge of the screen
     * @exception NullReferenceException When the search bar isn't assigned using {@code setSearchBar()}
    */
    @Override
    public Dimension getPreferredSize() 
    {
        return new Dimension(searchBar.getWidth(), popupHandler.getHeight());
    }

    /** Gets the supposed location of the search bar results panel's stretch container. 
     * @return A {@code Point} right under the search bar's bottom-left corner
     * @exception NullReferenceException When the search bar isn't assigned using {@code setSearchBar()}
    */
    public Point getPreferredLocation()
    {
        Point searchBarOnScreen = searchBar.getLocationOnScreen();
        Point newLocation = new Point();
        Point contentPaneScreenPos = mainWindow.getContentPane().getLocationOnScreen();
        newLocation.setLocation(searchBarOnScreen.getX() - contentPaneScreenPos.getX(),
                searchBarOnScreen.getY() - contentPaneScreenPos.getY() + searchBar.getHeight());
        return newLocation;
    }

    ///////////////////////////////////////////////// SETTERS /////////////////////////////////////////////////
    /**
     * Sets the search bar to be used by the results panel for its positioning and sizing calculations
     * @param newSearchBar The search bar to be used by the results panel for its layout calculations
     */
    public void setSearchBar(JComponent newSearchBar) { this.searchBar = newSearchBar; }

    /**
     * Sets the value of the text displayed in the search bar (Like "No results found")
     * @param text The text to be displayed
     */
    public void setText(String text) { textMessage.setText(text); }

    /**
     * Sets the current search results entry that the results panel holds and displays it
     * @param results The list of search results to display
     */
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
                JButton resultButton = new JButton(String.format("\"%s\" from \"%s\"", currentResult.getName(),
                        currentResult.getBoardSource().getName()));
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

    ///////////////////////////////////////////////// EVENT HANDLERS /////////////////////////////////////////////////
    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
    
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

    ///////////////////////////////////////////////// INSTANCE METHODS /////////////////////////////////////////////////
    /** Resets the search results panel to its default state */
    public void clearResults()
    {
        innerCountainer.removeAll();
        innerContainerConstraints.gridy = 0;
        innerCountainer.add(textMessage, innerContainerConstraints);
        textMessage.setVisible(true);
        if(currentResults != null && currentResults.size() > 0)
            currentResults.clear();
    }
}
