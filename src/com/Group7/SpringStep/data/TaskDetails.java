package com.Group7.SpringStep.data;

import java.time.*;
import java.util.*;

import com.Group7.SpringStep.Utils;

public class TaskDetails 
{
    private String name;
    private String description;
    private LocalDate deadline;

    public TaskDetails() { }
    public TaskDetails(String csv) { parseCsv(csv); }
    public TaskDetails(TaskDetails source)
    {
        setName(new String(source.getName()));
        if (description != null)
        {
            setDescription(new String(source.getDescription()));
        }
        setDeadline(source.getDeadline());
    }

    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public LocalDate getDeadline() { return deadline; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

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
    
    private boolean betterEquals(Object obj1, Object obj2)
    {
        boolean result = false;
        if (obj1 == null && obj2 != null) { result = false; } 
        else if (obj1 != null && obj2 == null) { result = false; } 
        else if (obj1 != null && obj2 != null) { result = obj1.equals(obj2); } 
        else { result = true; }
        return result;
    }

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

        return String.format("%s,%s,%s", formattedName, formattedDescription, formattedDeadline);
    }

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
