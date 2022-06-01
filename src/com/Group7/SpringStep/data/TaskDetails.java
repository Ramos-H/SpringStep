package com.Group7.SpringStep.data;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TaskDetails 
{
    private String name;
    private String description;
    private ArrayList<TagDetails> tags = new ArrayList<>();
    private LocalDate deadline;
    private LocalTime expectedDuration;

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

    public String getCsvFormattedInfo() 
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
