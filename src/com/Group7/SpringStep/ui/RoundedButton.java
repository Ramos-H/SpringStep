package com.Group7.SpringStep.ui;

import java.awt.*;

import javax.swing.*;

/** Custom button with rounded corners */
public class RoundedButton extends JButton
{
    /** Determines how rounded the corners are */
    private int arcCurve = 12;

    ///////////////////////////////////////////////// CONSTRUCTORS /////////////////////////////////////////////////
    public RoundedButton() { }
    public RoundedButton(String text, Icon icon) { super(text, icon); }
    public RoundedButton(String text)
    {
        super(text);
        setBorderPainted(false); // Don't draw the borders
        /* Don't fill the button area, because we'll handle the drawing of the background ourselves
         * by drawing a rounded rectangle in the background's place
        */
        setContentAreaFilled(false);
    }

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
        super.paintComponent(g); // Handles the painting of the text
    }
}
