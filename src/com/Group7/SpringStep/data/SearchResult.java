package com.Group7.SpringStep.data;

public class SearchResult 
{
    private String name;
    private BoardDetails boardSource;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the boardSource
     */
    public BoardDetails getBoardSource() {
        return boardSource;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param boardSource the boardSource to set
     */
    public void setBoardSource(BoardDetails boardSource) {
        this.boardSource = boardSource;
    }
}
