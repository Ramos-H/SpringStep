package com.Group7.SpringStep.ui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import com.Group7.SpringStep.*;

public class PopupContainer extends JPanel implements ComponentListener, MouseListener
{
    private Container contentPane;
    private JPanel currentPopup;
    private Rectangle exclusionZone;
    private JComponent searchBar;

    public PopupContainer(Container contentPane)
    {
        this.contentPane = contentPane;
        contentPane.addComponentListener(this);
        setOpaque(false);
        setBackground(new Color(64, 64, 64, 64));
        setLayout(null);
        addMouseListener(this);
    }

    /**
     * @param searchBar the searchBar to set
     */
    public void setSearchBar(JComponent searchBar) 
    {
        this.searchBar = searchBar;
    }
    
    public void setPopup(JPanel panel)
    {
        boolean visible = isVisible();
        if (currentPopup == panel && visible) {
            return;
        }

        if (currentPopup != null) {
            remove(currentPopup);
        }

        currentPopup = panel;
        add(currentPopup);
        resizeCurrentPopup();
        positionCurrentPopup();
        setVisible(true);
    }

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

    public void hidePopup()
    {
        setVisible(false);
    }
    
    @Override
    protected void paintComponent(Graphics g) 
    {
        g.setColor(getBackground());
        Rectangle clipBounds = g.getClipBounds();
        g.fillRect(clipBounds.x, clipBounds.y, clipBounds.width, clipBounds.height);
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

    @Override
    public void componentResized(ComponentEvent e) 
    {
        resizeCurrentPopup();
        positionCurrentPopup();
    }

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

    @Override
    public void componentMoved(ComponentEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void componentShown(ComponentEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        // TODO Auto-generated method stub
        
    }

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
    public void mouseEntered(MouseEvent e) 
    {
    }

    @Override
    public void mouseExited(MouseEvent e) 
    {
    }
}
