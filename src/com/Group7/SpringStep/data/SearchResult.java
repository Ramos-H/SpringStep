package com.Group7.SpringStep.data;

/** Class that contains data for a search result */
public class SearchResult 
{
    /** Name of the task */
    private String name;
    /** The board that contains the task */
    private BoardDetails boardSource;

    /**
     * @return The name of the task
     */
    public String getName() { return name; }

    /**
     * @return The board containing the task
     */
    public BoardDetails getBoardSource() { return boardSource; }

    /**
     * @param name The name of the task
     */
    public void setName(String name) { this.name = name; }

    /**
     * @param boardSource The board containing the task
     */
    public void setBoardSource(BoardDetails boardSource) { this.boardSource = boardSource; }
}
