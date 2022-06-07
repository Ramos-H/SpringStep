package com.Group7.SpringStep.data;

import java.util.*;

public class User 
{
    private String userName;
    private String email;
    private String password;
    private ArrayList<BoardDetails> boards;

    // Constructors
    public User(String newUsername, String newEmail, String newPassword) 
    {
        setUserName(newUsername);
        setEmail(newEmail);
        setPassword(newPassword);
        boards = new ArrayList<>();
        boards.add(new BoardDetails("New Board"));
    }

    public User(User copyFrom)
    {
        if(copyFrom != null)
        {
            setUserName(copyFrom.getUserName());
            setEmail(copyFrom.getEmail());
            setPassword(copyFrom.getPassword());
            setBoards(copyFrom.getBoards());
        }
    }
    
    // Getters
    public String getUserName() { return userName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public ArrayList<BoardDetails> getBoards() { return boards; }
    public String getAsCsv() 
    {
        String output = String.format("%s,%s,%s,%d\n", userName, email, password, boards.size());
        for (BoardDetails board : boards) { output += board.getAsCsv(); }
        return output;
    }

    // Setters
    public void setUserName(String userName) { this.userName = userName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setBoards(ArrayList<BoardDetails> boards) { this.boards = boards; }

    public static User getNewFromCsv(List<String> csvInput)
    {
        if (csvInput.size() < 1) { return null; }

        String[] userData = csvInput.get(0).split(",");
        User user = new User(userData[0], userData[1], userData[2]);

        int boardCount = Integer.parseInt(userData[3]);
        ArrayList<BoardDetails> newBoardList = new ArrayList<>();
        int readOffset = 1;
        for (int boardIndex = 0; boardIndex < boardCount; boardIndex++) 
        {
            String[] currentBoardData = csvInput.get(readOffset).split(",");
            BoardDetails newBoard = new BoardDetails(currentBoardData[0]);
            readOffset++;

            int toDoCount = Integer.parseInt(currentBoardData[1]);
            int doneCount = Integer.parseInt(currentBoardData[2]);
            int totalTasksCount = toDoCount + doneCount;
            List<String> subList = csvInput.subList(readOffset, readOffset + totalTasksCount);
            newBoard.parseTasksFromCsv(subList, toDoCount, doneCount);
            readOffset += totalTasksCount;

            newBoardList.add(newBoard);
        }

        user.setBoards(newBoardList);
        return user;
    }
}
