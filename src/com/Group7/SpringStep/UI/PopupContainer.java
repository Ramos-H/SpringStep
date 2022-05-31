package com.Group7.SpringStep.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class PopupContainer extends JPanel implements ComponentListener
{
    Container contentPane;
    JPanel currentPopup;

    public PopupContainer(Container contentPane)
    {
        this.contentPane = contentPane;
        contentPane.addComponentListener(this);
        setOpaque(false);
        setBackground(new Color(64, 64, 64, 64));
        setLayout(null);
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
        int x = Math.round(contentPane.getWidth() / 2 - currentPopup.getWidth() / 2);
        int y = Math.round(contentPane.getHeight() / 2 - currentPopup.getHeight() / 2);
        currentPopup.setLocation(x, y);
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
        super.paintComponent(g);
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
            float widthScale = 40f;
            float heightScale = 80f;
            int width = Math.round(contentPane.getWidth() * (widthScale / 100f));
            int height = Math.round(contentPane.getHeight() * (heightScale / 100f));
            currentPopup.setSize(width, height);
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
}
