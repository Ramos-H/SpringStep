package com.Group7.SpringStep.data;

import java.util.*;

import com.Group7.SpringStep.Utils;

/** Class that contains details about a task board */
public class BoardDetails 
{
    private String name;
    ArrayList<TaskDetails> todoList;
    ArrayList<TaskDetails> doneList;

    ///////////////////////////////////////////////// CONSTRUCTORS /////////////////////////////////////////////////
    /**
     * Creates a new task board information container with the specified name
     * @param newName The name of the new task board
     */
    public BoardDetails(String newName)
    {
        setName(newName);
        todoList = new ArrayList<>();
        doneList = new ArrayList<>();
    }

    ///////////////////////////////////////////////// GETTERS /////////////////////////////////////////////////
    public String getName() { return name; }
    public ArrayList<TaskDetails> getTodoList() { return todoList; }
    public ArrayList<TaskDetails> getDoneList() { return doneList; }

    /**
     * Format this board's details into CSV
     * @return A {@code String} containing this board's information, formatted into a CSV
     */
    public String getAsCsv()
    {
        int todoCount = todoList.size();
        int doneCount = doneList.size();

        // Put the name of the board, the number of tasks in the "To Do" list, and the number of tasks in the "Done" list
        String boardOutput = String.format("%s,%d,%d\n", Utils.getCsvFriendlyFormat(getName()), todoCount, doneCount);

        // If the "To Do" list has at least one task, append the CSV-formatted details of those tasks into the board details
        if(todoCount > 0)
        {
            for (TaskDetails taskDetails : todoList) { boardOutput += taskDetails.getAsCsv() + "\n"; }
        }
        // If the "Done" list has at least one task, append the CSV-formatted details of those tasks into the board details
        if (doneCount > 0)
        {
            for (TaskDetails taskDetails : doneList) { boardOutput += taskDetails.getAsCsv() + "\n"; }
        }
        
        return boardOutput;
    }
    
    ///////////////////////////////////////////////// SETTERS /////////////////////////////////////////////////
    public void setName(String name) { this.name = name; }
    public void setTodoList(ArrayList<TaskDetails> todoList) { this.todoList = todoList; }
    public void setDoneList(ArrayList<TaskDetails> doneList) { this.doneList = doneList; }
    public void setLists(ArrayList<TaskDetails> newTodoList, ArrayList<TaskDetails> newDoneList)
    {
        todoList = newTodoList;
        doneList = newDoneList;
    }

    /**
     * Parse task information from a CSV format and put them into the board
     * @param csvs The array of strings containing CSVs with task data
     * @param toDoCount The number of "To Do" tasks
     * @param doneCount The number of "Done" tasks
     */
    public void parseTasksFromCsv(List<String> csvs, int toDoCount, int doneCount)
    {
        int readOffset = 0;
        // Read TO DO tasks
        ArrayList<TaskDetails> newTodoList = new ArrayList<>(toDoCount);
        for (int toDoIndex = 0; toDoIndex < toDoCount; toDoIndex++, readOffset++) 
        {
            TaskDetails newTask = new TaskDetails(csvs.get(readOffset));
            newTodoList.add(newTask);
        }
        setTodoList(newTodoList);

        // Read DONE tasks
        ArrayList<TaskDetails> newDoneList = new ArrayList<>(doneCount);
        for (int doneIndex = 0; doneIndex < doneCount; doneIndex++, readOffset++) 
        {
            TaskDetails newTask = new TaskDetails(csvs.get(readOffset));
            newDoneList.add(newTask);
        }
        setDoneList(newDoneList);
    }
}
