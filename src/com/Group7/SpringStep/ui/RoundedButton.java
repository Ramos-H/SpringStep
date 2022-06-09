package com.Group7.SpringStep.ui;

import java.awt.*;

import javax.swing.*;

public class RoundedButton extends JButton
{
    private int arcCurve = 12;

    public RoundedButton() { }
    public RoundedButton(String text, Icon icon) { super(text, icon); }

    public RoundedButton(String text)
    {
        super(text);
        setBorderPainted(false);
        setContentAreaFilled(false);
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
        super.paintComponent(g);
    }
}
