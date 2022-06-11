package com.Group7.SpringStep.data;

import java.time.*;
import java.util.*;

import com.Group7.SpringStep.Utils;

/** Class that contains details about a task */
public class TaskDetails 
{
    private String name;
    private String description;
    private LocalDate deadline;

    ///////////////////////////////////////////////// CONSTRUCTORS /////////////////////////////////////////////////
    public TaskDetails() { }

    /**
     * Creates a new task from a CSV file. Used when importing the task from file.
     * @param csv String of the CSV-formatted task details
     */
    public TaskDetails(String csv) { parseCsv(csv); }

    /**
     * Creates a copy of an existing task
     * @param source The task to create a copy of
     */
    public TaskDetails(TaskDetails source)
    {
        setName(new String(source.getName()));
        if (description != null)
        {
            setDescription(new String(source.getDescription()));
        }
        setDeadline(source.getDeadline());
    }

    ///////////////////////////////////////////////// GETTERS /////////////////////////////////////////////////
    public String getName() { return name; }
    public String getDescription() { return description; }
    public LocalDate getDeadline() { return deadline; }

    /**
     * Format this task's details into CSV
     * @return A {@code String} containing this task's information, formatted into a CSV
     */
    public String getAsCsv() 
    {
        final String DEFAULT_VALUE = "NULL";

        String formattedName = DEFAULT_VALUE;
        if (!Utils.isTextEmpty(getName())) 
        {
            formattedName = String.format("%s", Utils.getCsvFriendlyFormat(name));
        }

        String formattedDescription = DEFAULT_VALUE;
        if (!Utils.isTextEmpty(getDescription())) 
        {
            formattedDescription = String.format("%s", Utils.getCsvFriendlyFormat(description));
        }

        String formattedDeadline = DEFAULT_VALUE;
        if (deadline != null) 
        {
            formattedDeadline = deadline.toString();
        }

        // Put NULL if the value isn't set
        return String.format("%s,%s,%s", formattedName, formattedDescription, formattedDeadline);
    }

    ///////////////////////////////////////////////// SETTERS /////////////////////////////////////////////////
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

    ///////////////////////////////////////////////// OVERRIDES /////////////////////////////////////////////////
    @Override
    public boolean equals(Object obj) 
    {
        if (obj == null) { return false; }
        TaskDetails other = (TaskDetails) obj;
        boolean nameCheck = betterEquals(getName(), other.getName());
        boolean descriptionCheck = betterEquals(getDescription(), other.getDescription());
        boolean deadlineCheck = betterEquals(getDeadline(), other.getDeadline());

        return nameCheck && descriptionCheck && deadlineCheck;
    }
    
    ////////////////////////////////////////////// INSTANCE METHODS /////////////////////////////////////////////
    /**
     * Compares if the two objects are the same. Now with null-checking on both inputs
     * @param obj1 The first object to compare
     * @param obj2 The second object to compare
     * @return {@code true} if the objects are the same. {@code false} otherwise
     */
    private boolean betterEquals(Object obj1, Object obj2)
    {
        boolean result = false;
        if (obj1 == null && obj2 != null) { result = false; } 
        else if (obj1 != null && obj2 == null) { result = false; } 
        else if (obj1 != null && obj2 != null) { result = obj1.equals(obj2); } 
        else { result = true; }
        return result;
    }

    /**
     * Parses task details from a CSV-formatted string
     * @param csv The CSV-formatted string
     */
    public void parseCsv(String csv)
    {
        String[] currentTaskData = Utils.splitCsv(csv);

        // Name
        setName(Utils.parseCsvFriendlyFormat(currentTaskData[0]));

        // Description
        if (!currentTaskData[1].equals("NULL")) 
        {
            setDescription(Utils.parseCsvFriendlyFormat(currentTaskData[1]));
        }

        // Deadline
        if (!currentTaskData[2].equals("NULL")) 
        {
            setDeadline(LocalDate.parse(currentTaskData[2]));
        }
    }
}
