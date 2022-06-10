package com.Group7.SpringStep;

import java.net.*;

/**
* Class that is used to access resources
*/
public class Resources 
{
    /**
     * Gets the URL object of the specified resource
     * @param path The file path and file name of the resource relative to the res folder
     * @return The URL object of the resource inside the res folder
     */
    public URL get(String path) { return getClass().getResource("res/" + path); }
}
