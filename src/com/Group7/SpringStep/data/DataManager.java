package com.Group7.SpringStep.data;

import java.io.*;
import java.nio.file.*;

import javax.swing.*;

/** Class that handles the reading and saving of user data */
public class DataManager 
{
    public static final int USER_SAVE_SUCCESSFUL = 0;
    public static final int USER_ALREADY_EXISTS = 1;

    /**
     * Reads a user file from the given path
     * @param filePath The path of the user file. Filename and extension must be included
     * @return {@code User} object containing the read data
     * @throws Exception
     */
    public User readUser(Path filePath) throws Exception
    {
        User currentUser = User.getNewFromCsv(Files.readAllLines(filePath));
        return currentUser;
    }

    /**
     * Saves the user file to the app's save directory located on 
     * {@code USER_HOME/Documents/SpringStep/users/USER_NAME.csv}
     * @param newUser The user data to save
     * @param overwrite Determines whether to overwrite the file's data if it already exists
     * @return {@code USER_SAVE_SUCCESSFUL} if the save operation is successful. 
     *         {@code USER_ALREADY_EXISTS} if {@code overwrite} is set to {@code false} and 
     *         the user already exists.
     * @throws Exception
     */
    public int saveUserData(User newUser, boolean overwrite) throws Exception
    {
        // Dynamically construct the output file path
        String userHome = System.getProperty("user.home");
        Path accountSavePath = Paths.get(userHome, "Documents", "SpringStep", "users");
        String completeFileName = newUser.getUserName() + ".csv";
        Path filePath = accountSavePath.resolve(completeFileName);

        // If the save directory doesn't exist, create it
        boolean folderExists = Files.exists(accountSavePath);
        boolean folderDoesntExist = Files.notExists(accountSavePath);
        boolean folderUnverifiable = !folderExists && !folderDoesntExist;
        boolean folderSurelyDoesntExists = !folderExists && folderDoesntExist;
        if (folderSurelyDoesntExists) { Files.createDirectories(accountSavePath); }
        
        // If the save file doesn't exist, create it
        boolean fileExists = Files.exists(filePath);
        boolean fileDoesntExist = Files.notExists(filePath);
        boolean fileUnverifiable = !fileExists && !fileDoesntExist;
        boolean fileSurelyExists = fileExists && !fileDoesntExist;
        boolean fileSurelyDoesntExists = !fileExists && fileDoesntExist;
        if (!fileSurelyExists) { Files.createFile(filePath); }
        
        // If the user already exists and overwrite isn't enabled, return the error status code
        if (!overwrite && (readUser(filePath) != null)) { return USER_ALREADY_EXISTS; }
        
        // Try writing the output to the file
        PrintWriter printWriter = new PrintWriter(new FileWriter(filePath.toString(), false));
        printWriter.println(newUser.getAsCsv());
        printWriter.close();
        return USER_SAVE_SUCCESSFUL;
    }
}
