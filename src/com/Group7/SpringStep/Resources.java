package com.Group7.SpringStep;

import java.net.*;

public class Resources 
{
    public URL get(String path)
    {
        return getClass().getResource("res/" + path);
    }
}
