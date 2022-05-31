package com.Group7.SpringStep.data;

import java.util.ArrayList;
import java.util.HashMap;

import com.Group7.SpringStep.Utils;

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

    public static User reconstructFromCsv(String csvInput)
    {
        if (Utils.isTextEmpty(csvInput)) { return null; }

        String[] split = csvInput.split(",");
        User user = new User(split[0], split[1], split[2]);

        return user;
    }

    // Setters
    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) { this.userName = userName; }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) { this.password = password; }

    // Getters
    /**
     * @return the userName
     */
    public String getUserName() { return userName; }
    
    /**
     * @return the email
     */
    public String getEmail() { return email; }
    
    /**
     * @return the password
     */
    public String getPassword() { return password; }

    /**
     * @return the boards
     */
    public ArrayList<BoardDetails> getBoards() { return boards; }

    /**
     * @return The user info formatted into the CSV format for easy writing to file
     */
    public String getCsvFormattedInfo() 
    {
        String output = String.format("%s,%s,%s,%d\n", userName, email, password, boards.size());
        for (BoardDetails board : boards) 
        {
            output += board.getCsvFormattedValues();
        }
        return output;
    }
}
