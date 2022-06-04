package com.Group7.SpringStep.data;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.print.attribute.standard.MediaSize.Other;

public class TaskDetails 
{
    private String name;
    private String description;
    private ArrayList<TagDetails> tags = new ArrayList<>();
    private LocalDate deadline;
    private LocalTime expectedDuration;

    public TaskDetails() { }
    public TaskDetails(String csv) { parseCsv(csv); }
    public TaskDetails(TaskDetails source)
    {
        setName(new String(source.getName()));
        setDescription(new String(source.getDescription()));
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
     * @return the tags
     */
    public ArrayList<TagDetails> getTags() { return tags; }

    /**
     * @return the deadline
     */
    public LocalDate getDeadline() { return deadline; }

    /**
     * @return the expectedDuration
     */
    public LocalTime getExpectedDuration() { return expectedDuration; }

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

    public void setExpectedDuration(LocalTime expectedDuration) { this.expectedDuration = expectedDuration; }

    // Methods
    public void addTag(TagDetails newTag) { tags.add(newTag); }

    public void removeTag(TagDetails tag) { tags.remove(tag); }

    public void removeTagByIndex(int index) { tags.remove(index); }

    @Override
    public boolean equals(Object obj) 
    {
        if(obj == null) { return false; }
        TaskDetails other = (TaskDetails) obj;
        boolean nameCheck = getName().equals(other.getName());
        boolean descriptionCheck = getDescription().equals(other.getDescription());
        boolean deadlineCheck = false;
        LocalDate otherDeadline = other.getDeadline();
        if (otherDeadline != null)
        {
            deadlineCheck = getDeadline().equals(other.getDeadline());
        }
        else
        {
            deadlineCheck = (getDeadline() == otherDeadline);
        }
        return nameCheck && descriptionCheck && deadlineCheck;
    }

    public void parseCsv(String csv)
    {
        String[] currentTaskData = csv.split(",");

        // Name
        setName(currentTaskData[0]);

        // Description
        if (!currentTaskData[1].equals("NULL")) 
        {
            setDescription(currentTaskData[1]);
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
        if(name != null)
        {
            formattedName = String.format("%s", name);
        }

        String formattedDescription = DEFAULT_VALUE;
        if(description != null)
        {
            formattedDescription = String.format("%s", description);
        }

        String formattedDeadline = DEFAULT_VALUE;
        if(deadline != null)
        {
            formattedDeadline = deadline.toString();
        }

        return String.format("%s,%s,%s", formattedName, formattedDescription, formattedDeadline);
    }
}
