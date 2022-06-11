package com.Group7.SpringStep.data;

import java.util.*;

import com.Group7.SpringStep.Utils;

/** Class that contains details about a user */
public class User 
{
    private String userName;
    private String email;
    private String password;
    private ArrayList<BoardDetails> boards;

    ///////////////////////////////////////////////// CONSTRUCTORS /////////////////////////////////////////////////
    /**
     * Creates a completely new user with the specified username, email and password
     * @param newUsername The username of the new user
     * @param newEmail The email address of the new user
     * @param newPassword The password of the new user
     */
    public User(String newUsername, String newEmail, String newPassword) 
    {
        setUserName(newUsername);
        setEmail(newEmail);
        setPassword(newPassword);
        boards = new ArrayList<>();
        boards.add(new BoardDetails("New Board"));
    }

    /**
     * Creates a copy of the given user
     * @param copyFrom The user object to make a copy of
     */
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
    
    ///////////////////////////////////////////////// GETTERS /////////////////////////////////////////////////
    public String getUserName() { return userName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public ArrayList<BoardDetails> getBoards() { return boards; }

    /**
     * Format this user's details into CSV
     * @return A {@code String} containing this user's information, formatted into a CSV
     */
    public String getAsCsv() 
    {
        // Put the username, email, password, and the number of boards into the first line
        String output = String.format("%s,%s,%s,%d\n", Utils.getCsvFriendlyFormat(getUserName()),
                Utils.getCsvFriendlyFormat(getEmail()), Utils.getCsvFriendlyFormat(getPassword()), boards.size());
        // Append the boards' data to the succeeding lines
        for (BoardDetails board : boards) { output += board.getAsCsv(); }
        return output;
    }

    // Setters
    public void setUserName(String userName) { this.userName = userName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setBoards(ArrayList<BoardDetails> boards) { this.boards = boards; }

    /**
     * Create a {@code User} object from a already read save file
     * @param csvInput The already split user account save file 
     * @return A {@code User} with the data from the save file
     */
    public static User getNewFromCsv(List<String> csvInput)
    {
        if (csvInput.size() < 1) { return null; }

        // Create a user object with the read username, email, and password
        String[] userData = Utils.splitCsv(csvInput.get(0));
        User user = new User(Utils.parseCsvFriendlyFormat(userData[0]), Utils.parseCsvFriendlyFormat(userData[1]),
                Utils.parseCsvFriendlyFormat(userData[2]));

        // Repeatedly parse board information such as board names and their respective tasks
        int boardCount = Integer.parseInt(userData[3]);
        ArrayList<BoardDetails> newBoardList = new ArrayList<>();
        int readOffset = 1;
        for (int boardIndex = 0; boardIndex < boardCount; boardIndex++) 
        {
            String[] currentBoardData = Utils.splitCsv(csvInput.get(readOffset));
            // Create a board and set its name to the name that is read from the file
            BoardDetails newBoard = new BoardDetails(Utils.parseCsvFriendlyFormat(currentBoardData[0]));
            readOffset++;

            int toDoCount = Integer.parseInt(currentBoardData[1]); // Parse number of "To Do" tasks inside the board
            int doneCount = Integer.parseInt(currentBoardData[2]); // Parse number of "Done" tasks inside the board
            int totalTasksCount = toDoCount + doneCount;

            // Make a sublist with the current board's task only for easier parsing
            List<String> subList = csvInput.subList(readOffset, readOffset + totalTasksCount);
            newBoard.parseTasksFromCsv(subList, toDoCount, doneCount); // Parse the tasks and put them into their respective lists in the board
            readOffset += totalTasksCount; // Increment the read offset so we are prepared to read the next board and its tasks, if there are more

            newBoardList.add(newBoard); // Add the board that was just read into the User's board list
        }

        user.setBoards(newBoardList); // Attach the board list to the user object before it gets passed back
        return user;
    }
}
