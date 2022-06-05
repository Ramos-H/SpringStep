package com.Group7.SpringStep.ui;

import java.time.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import com.Group7.SpringStep.*;

public class TimerPanel extends JPanel implements ActionListener
{
    private JLabel timerLabel;
    private JButton startStopTimerButton;
    private JButton resetTimerButton;

    private Timer timer;
    private int timerTick = 10;
    private boolean workMode = true;
    private LocalTime remainingTime;

    private final LocalTime DEFAULT_WORK_DURATION = LocalTime.of(0, 2);
    private final LocalTime DEFAULT_BREAK_DURATION = LocalTime.of(0, 1);

    public TimerPanel()
    {
        // Set timer
        remainingTime = DEFAULT_WORK_DURATION;
        timer = new Timer(1000, this);

        setLayout(new GridBagLayout());
        {
            timerLabel = new JLabel(Utils.formatTime(remainingTime));
            timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
            timerLabel.setFont(new Font(timerLabel.getFont().getFontName(), Font.PLAIN, 21));
            timerLabel.setForeground(Color.RED);
            timerLabel.setOpaque(false);

            startStopTimerButton = new JButton("Start");
            startStopTimerButton.addActionListener(this);
            resetTimerButton = new JButton("Reset");
            resetTimerButton.addActionListener(this);

            GridBagConstraints timerPanelConstraints = new GridBagConstraints();
            timerPanelConstraints.anchor = GridBagConstraints.CENTER;
            timerPanelConstraints.gridx = 0;

            timerPanelConstraints.gridwidth = 2;
            add(timerLabel, timerPanelConstraints);

            timerPanelConstraints.gridy = 1;
            timerPanelConstraints.gridwidth = 1;
            add(startStopTimerButton, timerPanelConstraints);

            timerPanelConstraints.gridx = 1;
            add(resetTimerButton, timerPanelConstraints);
        }
    }
    
    public void startTimer()
    {
        timer.start();
        startStopTimerButton.setText("Stop");
    }

    public void stopTimer()
    {
        timer.stop();
        startStopTimerButton.setText("Start");
    }

    public void resetTimer()
    {
        if(workMode)
        {
            remainingTime = DEFAULT_WORK_DURATION;
        }
        else
        {
            remainingTime = DEFAULT_BREAK_DURATION;
        }
        timerLabel.setText(Utils.formatTime(remainingTime));
    }

    /**
     * @param workMode the workMode to set
     */
    public void setWorkMode(boolean newWorkMode) 
    {
        workMode = newWorkMode;
        if (workMode) {
            timerLabel.setForeground(Color.RED);
            remainingTime = DEFAULT_WORK_DURATION;
        } else {
            timerLabel.setForeground(Color.GREEN);
            remainingTime = DEFAULT_BREAK_DURATION;
        }
        timerLabel.setText(Utils.formatTime(remainingTime));
    }

    public boolean getWorkMode()
    {
        return workMode;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        Object eventSource = e.getSource();
        if (eventSource == timer) 
        {
            if (!remainingTime.equals(LocalTime.MIN)) 
            {
                remainingTime = remainingTime.minusSeconds(timerTick);
                timerLabel.setText(Utils.formatTime(remainingTime));
            } else 
            {
                setWorkMode(!getWorkMode());
            }
        }
        
        if (eventSource == startStopTimerButton) 
        {
            if (timer.isRunning()) 
            {
                stopTimer();
            } else 
            {
                startTimer();
            }
        } else if(eventSource == resetTimerButton)
        {
            resetTimer();
        }
    }
}