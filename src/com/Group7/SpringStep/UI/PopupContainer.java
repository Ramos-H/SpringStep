package com.Group7.SpringStep.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class PopupContainer extends JPanel
{
    Container contentPane;
    JPanel currentPopup;

    public PopupContainer()
    {
        setOpaque(false);
        setBackground(new Color(64, 64, 64, 64));
        setLayout(new GridBagLayout());
        GridBagConstraints initialPopupConstraints = new GridBagConstraints();
        initialPopupConstraints.fill = GridBagConstraints.BOTH;
        initialPopupConstraints.weightx = 1;
        initialPopupConstraints.weighty = 0.3;
        JPanel comp = new JPanel();
        comp.setOpaque(false);
        add(comp, initialPopupConstraints);

        initialPopupConstraints.gridx = 2;
        initialPopupConstraints.gridy = 2;
        JPanel comp2 = new JPanel();
        comp2.setOpaque(false);
        add(comp2, initialPopupConstraints);
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
        GridBagConstraints popupConstraints = new GridBagConstraints();
        popupConstraints.fill = GridBagConstraints.BOTH;
        popupConstraints.weightx = 0.7;
        popupConstraints.weighty = 1;
        popupConstraints.gridx = 1;
        popupConstraints.gridy = 1;

        add(currentPopup, popupConstraints);
        setVisible(true);
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
}
