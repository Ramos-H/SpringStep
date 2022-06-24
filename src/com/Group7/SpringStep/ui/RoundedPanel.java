package com.Group7.SpringStep.ui;

import java.awt.*;

import javax.swing.*;

/** Custom panel with rounded corners */
public class RoundedPanel extends JPanel
{
    /** Determines how rounded the corners are */
    private int arcCurve = 12;

    ///////////////////////////////////////////////// CONSTRUCTORS /////////////////////////////////////////////////
    public RoundedPanel() { super(); }
    public RoundedPanel(LayoutManager layout) { super(layout); }

    ///////////////////////////////////////////////// GETTERS /////////////////////////////////////////////////
    /**
     * @return The curve value of the corners
     */
    public int getArcCurve() { return arcCurve; }

    ///////////////////////////////////////////////// SETTERS /////////////////////////////////////////////////
    /**
     * @param arcCurve How curved you want the corners to be
     */
    public void setArcCurve(int arcCurve) { this.arcCurve = arcCurve; }

    ///////////////////////////////////////////////// OVERRIDES /////////////////////////////////////////////////
    @Override
    protected void paintComponent(Graphics g) 
    {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth(), getHeight(), arcCurve, arcCurve);
    }
}
