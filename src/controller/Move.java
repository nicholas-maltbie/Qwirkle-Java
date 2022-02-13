/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Board;
import model.Piece;

/**
 *
 * @author Nick_Pro
 */
public class Move 
{
    
    private int row, col;
    private Piece piece;
    
    public Move(int row, int col, Piece piece)
    {
        this.row = row;
        this.col = col;
        this.piece = piece;
    }    
    
    public int getRow()
    {
        return row;
    }
    
    public int getCol()
    {
        return col;
    }
    
    public Piece getPiece()
    {
        return piece;
    }
    
    public void doMove(Board<Piece> board)
    {
        board.setPiece(row, col, piece);
    }
    
    public void undoMove(Board<Piece> board)
    {
        board.removePiece(row, col);
    }
    
    public String toString()
    {
        return piece.toString() + " at row: " + row + " col: " + col;
    }
}
