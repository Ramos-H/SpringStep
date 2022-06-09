package com.Group7.SpringStep.ui;

import java.time.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import java.awt.TrayIcon.*;

import com.Group7.SpringStep.*;

public class TimerPanel extends JPanel implements ActionListener
{
    private JLabel timerLabel;
    private RoundedButton startStopTimerButton;
    private RoundedButton resetTimerButton;

    private Timer timer;
    private int timerTick = 10;
    private boolean workMode = true;
    private LocalTime remainingTime;

    private TrayIcon trayIcon;

    private final LocalTime DEFAULT_WORK_DURATION = LocalTime.of(0, 25);
    private final LocalTime DEFAULT_BREAK_DURATION = LocalTime.of(0, 5);
    private JLabel timerStatusIcon;
    private Image workTimeImage;
    private Image breakTimeImage;
    private ImageIcon workTimeIcon;
    private ImageIcon breakTimeIcon;

    public TimerPanel(TrayIcon icon)
    {
        trayIcon = icon;
        // Set timer
        remainingTime = DEFAULT_WORK_DURATION;
        timer = new Timer(1000, this);
        workTimeImage = Utils.getScaledImage(App.resources.get("Work_Time_Icon_256.png"), 0.125f);
        if (workTimeImage != null) {
            workTimeIcon = new ImageIcon(workTimeImage);
        }

        breakTimeImage = Utils.getScaledImage(App.resources.get("Break_Time_Icon_256.png"), 0.125f);
        if (breakTimeImage != null) {
            breakTimeIcon = new ImageIcon(breakTimeImage);
        }

        setLayout(new GridBagLayout());
        {
            JPanel timeIndicatorArea = new JPanel(new BorderLayout());
            timeIndicatorArea.setOpaque(false);
            {
                timerStatusIcon = new JLabel();
                if (workTimeIcon != null) {
                    timerStatusIcon.setIcon(workTimeIcon);
                }

                timerLabel = new JLabel(Utils.formatTime(remainingTime));
                timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
                timerLabel.setFont(new Font(timerLabel.getFont().getFontName(), Font.PLAIN, 40));
                timerLabel.setForeground(Color.RED);
                timerLabel.setOpaque(false);

                timeIndicatorArea.add(timerStatusIcon, BorderLayout.LINE_START);
                timeIndicatorArea.add(timerLabel, BorderLayout.CENTER);
            }

            startStopTimerButton = new RoundedButton("Start");
            startStopTimerButton.addActionListener(this);
            startStopTimerButton.setBackground(new Color(135, 195, 193));

            resetTimerButton = new RoundedButton("Reset");
            resetTimerButton.addActionListener(this);
            resetTimerButton.setBackground(new Color(215, 204, 195));

            GridBagConstraints timerPanelConstraints = new GridBagConstraints();
            timerPanelConstraints.anchor = GridBagConstraints.CENTER;
            timerPanelConstraints.gridx = 0;

            timerPanelConstraints.gridwidth = 2;
            add(timeIndicatorArea, timerPanelConstraints);

            timerPanelConstraints.gridy = 1;
            timerPanelConstraints.gridwidth = 1;
            timerPanelConstraints.anchor = GridBagConstraints.EAST;
            timerPanelConstraints.insets = new Insets(0, 26, 0, 0);
            add(startStopTimerButton, timerPanelConstraints);

            timerPanelConstraints.gridx = 1;
            timerPanelConstraints.anchor = GridBagConstraints.WEST;
            timerPanelConstraints.insets = new Insets(0, 5, 0, 0);
            add(resetTimerButton, timerPanelConstraints);
        }
    }

    public boolean getWorkMode() { return workMode; }

    public void setWorkMode(boolean newWorkMode) 
    {
        workMode = newWorkMode;
        if (workMode) 
        {
            timerLabel.setForeground(new Color(255, 164, 133));
            remainingTime = DEFAULT_WORK_DURATION;
            if(workTimeIcon != null) { timerStatusIcon.setIcon(workTimeIcon); }
        } else 
        {
            timerLabel.setForeground(new Color(134, 194, 192));
            remainingTime = DEFAULT_BREAK_DURATION;
            if(breakTimeIcon != null) { timerStatusIcon.setIcon(breakTimeIcon); }
        }
        timerLabel.setText(Utils.formatTime(remainingTime));
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
                displayNotifMessage();
            }
        }
        
        if (eventSource == startStopTimerButton) 
        {
            if (timer.isRunning()) { stopTimer(); } 
            else  { startTimer(); }
        } else if(eventSource == resetTimerButton) { resetTimer(); }
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
        if(workMode) { remainingTime = DEFAULT_WORK_DURATION; }
        else { remainingTime = DEFAULT_BREAK_DURATION; }
        timerLabel.setText(Utils.formatTime(remainingTime));
    }

    private void displayNotifMessage()
    {
        if (trayIcon != null)
        {
            if (getWorkMode())
            {
                trayIcon.displayMessage("Time to work!", "Start working on your tasks now!", MessageType.INFO);
            }
            else
            {
                trayIcon.displayMessage("Time for a break!", "Take a break for a sec! Eat some snacks or play some games", MessageType.INFO);
            }
        }
    }
}