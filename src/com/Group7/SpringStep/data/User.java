package com.Group7.SpringStep.data;

import com.Group7.SpringStep.Utils;

public class User 
{
    private String userName;
    private String email;
    private String password;

    // Constructors
    public User() { }

    public static User reconstructFromCsv(String csvInput)
    {
        if (Utils.isTextEmpty(csvInput))
            return null;

        User user = new User();
        String[] split = csvInput.split(",");
        user.userName = split[0];
        user.email = split[1];
        user.password = split[2];
        
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
     * @return The user info formatted into the CSV format for easy writing to file
     */
    public String getCsvFormattedInfo() { return String.format("%s,%s,%s", userName, email, password); }
}
