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
    /**
     * @return the name
     */
    public String getName() { return name; }

    /**
     * @return the description
     */
    public String getDescription() { return description; }

    /**
     * @return the deadline
     */
    public LocalDate getDeadline() { return deadline; }

    // Setters
    /**
     * @param name the name to set
     */
    public void setName(String name) { this.name = name; }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * @param deadline the deadline to set
     */
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

    /**
     * @param expectedDuration the expectedDuration to set
     */

    @Override
    public boolean equals(Object obj) 
    {
        if (obj == null) {
            return false;
        }
        TaskDetails other = (TaskDetails) obj;
        boolean nameCheck = betterEquals(getName(), other.getName());
        boolean descriptionCheck = betterEquals(getDescription(), other.getDescription());
        boolean deadlineCheck = betterEquals(getDeadline(), other.getDeadline());
        return nameCheck && descriptionCheck && deadlineCheck;
    }
    
    private boolean betterEquals(Object obj1, Object obj2)
    {
        boolean result = false;
        if (obj1 == null && obj2 != null) {
            result = false;
        } else if (obj1 != null && obj2 == null) {
            result = false;
        } else if (obj1 != null && obj2 != null) {
            result = obj1.equals(obj2);
        } else {
            result = true;
        }
        return result;
    }

    public void parseCsv(String csv)
    {
        String[] currentTaskData = csv.split(",");

        // Name
        setName(currentTaskData[0]);

        // Description
        if (!currentTaskData[1].equals("NULL")) 
        {
            setDescription(parseCsvFriendlyFormat(currentTaskData[1]));
        }

        // Deadline
        if (!currentTaskData[2].equals("NULL")) 
        {
            setDeadline(LocalDate.parse(currentTaskData[2]));
        }
    }

    public String getAsCsv() 
    {

        final String DEFAULT_VALUE = "NULL";

        String formattedName = DEFAULT_VALUE;
        if (!Utils.isTextEmpty(getName())) {
            formattedName = String.format("%s", csvFriendlyFormat(name));
        }

        String formattedDescription = DEFAULT_VALUE;
        if (!Utils.isTextEmpty(getDescription())) {
            formattedDescription = String.format("%s", csvFriendlyFormat(description));
        }

        String formattedDeadline = DEFAULT_VALUE;
        if (deadline != null) {
            formattedDeadline = deadline.toString();
        }

        return String.format("%s,%s,%s", formattedName, formattedDescription, formattedDeadline);
    }
    
    private String csvFriendlyFormat(String input)
    {
        return input.replace("\\", "\\\\")
                    .replace("\n", "\\n")
                    .replace("\"", "\\\"");
    }

    private String parseCsvFriendlyFormat(String input)
    {
        return input.replace("\\\\", "\\")
                    .replace("\\n", "\n")
                    .replace("\\\"", "\"");
    }
}
