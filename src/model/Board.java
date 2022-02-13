/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 * Board that will hold a 2d array of E
 * @author Maltbie_N
 * @param <E>
 */
public class Board<E>
{
    /**
     * Saved board of E
     */
    private E[][] board;

    /**
     * Constructor that will make a square 2d array of E.
     * @param length length of the sides of the squares
     */
    public Board(int length)
    {
        this(length, length);
    }

    /**
     * Constructor to make a rectangular, 2d array E.
     * @param rows number of rows in the 2d array
     * @param cols number of colums in the 2d array
     */
    public Board(int rows, int cols)
    {
        board = (E[][])new Object[rows] [cols];
    }

    /**
     * Uses a pre-made 2d array of E for the board. Not a copy constructor,
     * this uses that 2d array as the board.
     * @param board 
     */
    public Board(E[][] board)
    {
        this.board = board;
    }
    
    /**
     * Copy constructor, will copy a board that is a copy of board, doesn't clone
     * the elements, the elements are the same, just a different stored array
     * @param board - board with copied elements
     */
    public Board(Board<E> board)
    {
        this(board.getNumRows(), board.getNumCols());
        for(int r = 0; r < board.getNumRows(); r++)
            for(int c = 0; c < board.getNumCols(); c++)
                this.setPiece(r, c, board.getPiece(r, c));
    }

    /**
     * Fills the entire board with that E.
     * @param fill the type of E to fill the board with. null can be used to
     * create an empty board.
     */
    public void fill(E fill)
    {
        for(int row = 0; row < getNumRows(); row++)
            for(int col = 0; col < getNumCols(); col++)
                setPiece(row, col, fill);
    }

    /**
     * Gets the E from the spot specified by row and col in the board.
     * @param row row of the object, must be greater than or equal to 0 and less than getNumRows();
     * @param col col of the object, must be greater than or equal to 0 and less than getNumCols();
     * @return the E found in the spot specified by row and col. It will return null if 
     * the spot is empty.
     */
    public E getPiece(int row, int col)
    {
        return board[row][col];
    }
    
    /**
     * Checks if the E at the spot specified by row and col is empty. If it is empty, it means
     * that it is null (see getPiece(int row, int col));
     * @param row - of the spot
     * @param col - of the spot
     * @return if the object at the spot specified by row and col is null (empty)
     */
    public boolean isEmpty(int row, int col)
    {
        return getPiece(row, col) == null;
    }

    /**
     * Sets a E in the spot specified by row and col in the board. To empty a spot,
     * use the method removePiece(int row, int col).
     * @param row row to put the fill in.
     * @param col colum to put the fill in.
     * @param fill E to put in the spot (row, col). Don't pass a null here,
     * use removePiece(int row, int col).
     * @return the object that was previously there, if it was empty it will
     * return null
     */
    public E setPiece(int row, int col, E fill)
    {
        E removed = removePiece(row, col);
        board[row][col] = fill;
        return removed;
    }

    /**
     * Removes the object from the spot specified by row and col and returns the object that was
     * there earlier. This fills it with a null.
     * @param row row of the E to remove.
     * @param col colum of the E to remove.
     * @return the object that was previously there or a null if it was empty.
     */
    public E removePiece(int row, int col)
    {
        E removed = getPiece(row,col);
        board[row][col] = null;
        return removed;
    }

    /**
     * Gets the number of rows in the board.
     * @return the number of rows as an int.
     */
    public int getNumRows()
    {
        return board.length;
    }

    /**
     * Gets the number of colums in the board.
     * @return the number of colums as an int.
     */
    public int getNumCols()
    {
        return board[0].length;
    }
    
    @Override
    public String toString()
    {
        String str = "";
        for(int row = 0; row < getNumRows(); row++)
        {
            for(int col = 0; col < getNumCols(); col++)
                str += getPiece(row, col) + "\t";
            str += "\n";
        }
        return str;
    }
}
