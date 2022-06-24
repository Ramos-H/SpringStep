package com.Group7.SpringStep.ui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import com.Group7.SpringStep.*;

/** Holds particular panels like the AddTaskPopup, ProfilePopup, and SearchResultsPanel and handles their size 
 * and position relative to the MainWindow */
public class PopupContainer extends JPanel implements ComponentListener, MouseListener
{
    private Container contentPane;
    private JPanel currentPopup;
    private Rectangle exclusionZone;
    private JComponent searchBar;

    ///////////////////////////////////////////////// CONSTRUCTORS /////////////////////////////////////////////////
    public PopupContainer(Container contentPane)
    {
        this.contentPane = contentPane;
        contentPane.addComponentListener(this);
        setOpaque(false);
        setBackground(new Color(64, 64, 64, 64));
        setLayout(null);
        addMouseListener(this);
    }

    ///////////////////////////////////////////////// SETTERS /////////////////////////////////////////////////
    public void setSearchBar(JComponent searchBar) { this.searchBar = searchBar; }

    /**
     * Sets the popup panel to be displayed and displays it
     * @param panel The popup panel to be displayed
     */
    public void setPopup(JPanel panel)
    {
        boolean visible = isVisible();
        if (currentPopup == panel && visible) { return; }
        if (currentPopup != null) { remove(currentPopup); }

        currentPopup = panel;
        add(currentPopup);
        resizeCurrentPopup();
        positionCurrentPopup();
        setVisible(true);
    }
    
    ///////////////////////////////////////////////// OVERRIDES /////////////////////////////////////////////////
    @Override
    protected void paintComponent(Graphics g) 
    {
        // Darken the whole main window
        g.setColor(getBackground());
        Rectangle clipBounds = g.getClipBounds();
        g.fillRect(clipBounds.x, clipBounds.y, clipBounds.width, clipBounds.height);

        // If the search bar is present, lighten the area of the search bar on the main window
        if (currentPopup instanceof SearchResultsPanel && searchBar != null)
        {
            Point searchBarOnScreen = searchBar.getLocationOnScreen();
            Point containerOnScreen = getLocationOnScreen();
            exclusionZone = new Rectangle(searchBarOnScreen.x - containerOnScreen.x,
                    searchBarOnScreen.y - containerOnScreen.y, searchBar.getWidth(), searchBar.getHeight());
            g.setColor(new Color(255, 255, 255, 128));
            g.fillRect(exclusionZone.x, exclusionZone.y, exclusionZone.width, exclusionZone.height);
        }
    }

    ///////////////////////////////////////////////// EVENT HANDLERS /////////////////////////////////////////////////
    @Override
    public void componentResized(ComponentEvent e) 
    {
        resizeCurrentPopup();
        positionCurrentPopup();
    }

    @Override
    public void mousePressed(MouseEvent e) 
    {
        if (currentPopup instanceof SearchResultsPanel) 
        {
            if (searchBar != null) 
            {
                Point mousePos = e.getLocationOnScreen();
                if (Utils.isMouseOverVisibleRect(mousePos, searchBar)) 
                {
                    redispatchMouseEvent(e);
                    return;
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) 
    {
        if (currentPopup instanceof SearchResultsPanel) 
        {
            if (searchBar != null) 
            {
                Point mousePos = e.getLocationOnScreen();
                if (Utils.isMouseOverVisibleRect(mousePos, searchBar)) 
                {
                    redispatchMouseEvent(e);
                    return;
                }
            }
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) { }

    @Override
    public void componentShown(ComponentEvent e) { }

    @Override
    public void componentHidden(ComponentEvent e) { }
    
    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseClicked(MouseEvent e) 
    {
        if (currentPopup instanceof SearchResultsPanel) 
        {
            if (searchBar != null) 
            {
                Point mousePos = e.getLocationOnScreen();
                if (Utils.isMouseOverVisibleRect(mousePos, searchBar)) 
                {
                    redispatchMouseEvent(e);
                    return;
                }
            }
            hidePopup();
        }
    }

    ///////////////////////////////////////////////// INSTANCE METHODS /////////////////////////////////////////////////
    /** Resizes the currently held popup panel (except the SearchResultsPanel) to occupy 40% of the width and 
     * 80% of the height of the main window */
    private void resizeCurrentPopup() 
    {
        if(currentPopup != null)
        {
            if (currentPopup instanceof SearchResultsPanel)
            {
                SearchResultsPanel searchPanel = (SearchResultsPanel) currentPopup;
                searchPanel.setSize(searchPanel.getPreferredSize());
            }
            else
            {
                Utils.scaleByPercentage(currentPopup, contentPane.getSize(), 40, 80);
            }
            revalidate();
            repaint();
        }
    }
    
    /** Centers the currently held popup panel (except the SearchResultsPanel) to the MainWindow */
    private void positionCurrentPopup() 
    {
        if(currentPopup != null)
        {
            if (currentPopup instanceof SearchResultsPanel)
            {
                SearchResultsPanel searchPanel = (SearchResultsPanel) currentPopup;
                searchPanel.setLocation(searchPanel.getPreferredLocation());
            }
            else
            {
                Utils.centerByRect(currentPopup, contentPane.getWidth(), contentPane.getHeight());
            }
            revalidate();
            repaint();
        }
    }

    /** Hides the currently held popup */
    public void hidePopup() { setVisible(false); }
    
    /**
     * Redispatches the mouse event to the component both under the mouse *and* this popup container
     * @param e The mouse event to be redispatched
     */
    private void redispatchMouseEvent(MouseEvent e) 
    {
        Point glassPanePoint = e.getPoint();
        Container container = contentPane;
        Point containerPoint = SwingUtilities.convertPoint(this, glassPanePoint, contentPane);
        Component component = SwingUtilities.getDeepestComponentAt(container, containerPoint.x, containerPoint.y);

        boolean isSearchBar = (component == searchBar);

        if (component != null) 
        {
            component.dispatchEvent(SwingUtilities.convertMouseEvent(this, e, component));
        }
    }
}
