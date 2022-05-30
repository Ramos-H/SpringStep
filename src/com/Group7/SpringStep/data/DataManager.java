package com.Group7.SpringStep.data;

import java.io.*;
import java.nio.file.*;

import javax.swing.*;

public class DataManager 
{
    public static final int USER_SAVE_SUCCESSFUL = 0;
    public static final int USER_ALREADY_EXISTS = 1;

    public User readUser(Path filePath) throws Exception
    {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath.toString()));
        User currentUser = new User(bufferedReader.readLine());
        bufferedReader.close();
        return currentUser;
    }

    public int saveUserData(User newUser, boolean overwrite) throws Exception
    {
        // Dynamically construct the output file path
        String userHome = System.getProperty("user.home");
        Path accountSavePath = Paths.get(userHome, "Documents", "SpringStep", "users");
        String completeFileName =  newUser.getUserName() + ".csv";
        Path filePath = accountSavePath.resolve(completeFileName);
        
        // Try writing the output to the file
        boolean folderExists = Files.exists(accountSavePath);
        boolean folderDoesntExist = Files.notExists(accountSavePath);
        boolean folderUnverifiable = !folderExists && !folderDoesntExist;
        boolean folderSurelyDoesntExists = !folderExists && folderDoesntExist;
        if(folderSurelyDoesntExists)
        {
            Files.createDirectories(accountSavePath);
        }

        boolean fileExists = Files.exists(filePath);
        boolean fileDoesntExist = Files.notExists(filePath);
        boolean fileUnverifiable = !fileExists && !fileDoesntExist;
        boolean fileSurelyExists = fileExists && !fileDoesntExist;
        boolean fileSurelyDoesntExists = !fileExists && fileDoesntExist;
        if(!fileSurelyExists)
        {
            Files.createFile(filePath);
        }

        if(!overwrite)
        {
            return USER_ALREADY_EXISTS;
        }
        
        PrintWriter printWriter = new PrintWriter(new FileWriter(filePath.toString(), false));
        printWriter.println(newUser.getCsvFormattedInfo());
        printWriter.close();
        return USER_SAVE_SUCCESSFUL;
    }
}
