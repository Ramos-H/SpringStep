package com.Group7.SpringStep.UI;

import java.awt.*;

import javax.swing.*;

public class ListScrollPane extends JScrollPane
{
    private Rectangle oldScrollPanelSize;
    private boolean maxed = false;

    public ListScrollPane(Component component)
    {
        super(component);
        oldScrollPanelSize = getBounds();
    }

    @Override
    public void setBounds(Rectangle r) 
    {
        JOptionPane.showMessageDialog(null, "Bounds was set: " + r.toString());
        super.setBounds(r);
    }
    
    @Override
    public void setBounds(int x, int y, int width, int height) 
    {
        Rectangle newScrollPanelSize = new Rectangle(x, y, width, height);

        if (oldScrollPanelSize.y <= newScrollPanelSize.y)
        {
            // JOptionPane.showMessageDialog(null, "Bounds was set: " + newScrollPanelSize.toString());
            super.setBounds(newScrollPanelSize.x, newScrollPanelSize.y, newScrollPanelSize.width,
                    newScrollPanelSize.height);
        }
        else 
        {
            maxed = true;
        }

        oldScrollPanelSize = newScrollPanelSize;
    }

    @Override
    public Dimension getPreferredSize() 
    {
        Dimension preferredSize = super.getPreferredSize();
        if (maxed)
            preferredSize = getSize();
        // String message = "Got Preferred size: " + preferredSize.toString() +
        //                 "\nGot current size: " + super.getSize().toString();
        // JOptionPane.showMessageDialog(null, message);
        return preferredSize;
    }

    @Override
    public void setPreferredSize(Dimension preferredSize) 
    {
        JOptionPane.showMessageDialog(null, "Preferred size has been set");
        super.setPreferredSize(preferredSize);
    }
}
