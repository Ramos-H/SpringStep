package com.Group7.SpringStep.ui;

import java.awt.*;

import javax.swing.*;

public class RoundedPanel extends JPanel
{
    private int arcCurve = 12;

    public RoundedPanel(LayoutManager layout)
    {
        super(layout);
    }

    /**
     * @return the arcCurve
     */
    public int getArcCurve() { return arcCurve; }

    /**
     * @param arcCurve the arcCurve to set
     */
    public void setArcCurve(int arcCurve) { this.arcCurve = arcCurve; }

    @Override
    protected void paintComponent(Graphics g) 
    {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth(), getHeight(), arcCurve, arcCurve);
    }
}
