package com.Group7.SpringStep.data;

import java.util.ArrayList;

public class BoardDetails 
{
    private String name;
    ArrayList<TaskDetails> todoList;
    ArrayList<TaskDetails> doingList;
    ArrayList<TaskDetails> doneList;

    public BoardDetails(String newName)
    {
        setName(newName);
        todoList = new ArrayList<>();
        doingList = new ArrayList<>();
        doneList = new ArrayList<>();
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @return the todoList
     */
    public ArrayList<TaskDetails> getTodoList() 
    {
        return todoList;
    }

    /**
     * @return the doingList
     */
    public ArrayList<TaskDetails> getDoingList() 
    {
        return doingList;
    }

    /**
     * @return the doneList
     */
    public ArrayList<TaskDetails> getDoneList() 
    {
        return doneList;
    }

    /**
     * @param todoList the todoList to set
     */
    public void setTodoList(ArrayList<TaskDetails> todoList) 
    {
        this.todoList = todoList;
    }

    /**
     * @param doingList the doingList to set
     */
    public void setDoingList(ArrayList<TaskDetails> doingList) 
    {
        this.doingList = doingList;
    }

    /**
     * @param doneList the doneList to set
     */
    public void setDoneList(ArrayList<TaskDetails> doneList) 
    {
        this.doneList = doneList;
    }

    public void setLists(ArrayList<TaskDetails> newTodoList, ArrayList<TaskDetails> newDoingList, ArrayList<TaskDetails> newDoneList)
    {
        todoList = newTodoList;
        doingList = newDoingList;
        doneList = newDoneList;
    }

    public String getCsvFormattedValues()
    {
        String boardOutput = String.format("%s,%d,%d\n", getName(), todoList.size(), doneList.size());
        for (TaskDetails taskDetails : todoList) 
        {
            boardOutput += taskDetails.getCsvFormattedInfo() + "\n";
        }

        for (TaskDetails taskDetails : doneList) 
        {
            boardOutput += taskDetails.getCsvFormattedInfo() + "\n";
        }

        return boardOutput;
    }
}
