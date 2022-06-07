package com.Group7.SpringStep.data;

import java.util.*;

public class BoardDetails 
{
    private String name;
    ArrayList<TaskDetails> todoList;
    ArrayList<TaskDetails> doneList;

    public BoardDetails(String newName)
    {
        setName(newName);
        todoList = new ArrayList<>();
        doneList = new ArrayList<>();
    }

    public String getName() { return name; }
    public ArrayList<TaskDetails> getTodoList() { return todoList; }
    public ArrayList<TaskDetails> getDoneList() { return doneList; }
    public String getAsCsv()
    {
        int todoCount = todoList.size();
        int doneCount = doneList.size();

        String boardOutput = String.format("%s,%d,%d\n", getName(), todoCount, doneCount);
        if(todoCount > 0)
        {
            for (TaskDetails taskDetails : todoList) 
            {
                boardOutput += taskDetails.getAsCsv() + "\n";
            }
        }

        if (doneCount > 0)
        {
            for (TaskDetails taskDetails : doneList) 
            {
                boardOutput += taskDetails.getAsCsv() + "\n";
            }
        }
        
        return boardOutput;
    }
    
    public void setName(String name) { this.name = name; }
    public void setTodoList(ArrayList<TaskDetails> todoList) { this.todoList = todoList; }
    public void setDoneList(ArrayList<TaskDetails> doneList) { this.doneList = doneList; }
    public void setLists(ArrayList<TaskDetails> newTodoList, ArrayList<TaskDetails> newDoneList)
    {
        todoList = newTodoList;
        doneList = newDoneList;
    }

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
