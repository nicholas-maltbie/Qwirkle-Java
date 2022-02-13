/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import model.Board;
import model.Piece;

/**
 *
 * @author Nick_Pro
 */
public class MoveSet
{
    List<Move> moves;
    
    public MoveSet(MoveSet move)
    {
        moves = new ArrayList<>(move.moves);
    }
    
    public MoveSet()
    {
        moves = new ArrayList<>();
    }
    
    public int[] getRows()
    {
        int[] rows = new int[moves.size()];
        for(int i = 0; i < moves.size(); i++)
            rows[i] = moves.get(i).getRow();
        return rows;
    }
    
    public Move getMove(int index)
    {
        return moves.get(index);
    }
    
    public int[] getCols()
    {
        int[] cols = new int[moves.size()];
        for(int i = 0; i < moves.size(); i++)
            cols[i] = moves.get(i).getCol();
        return cols;
    }
    
    public void addMove(Move m)
    {
        moves.add(m);
    }
    
    public void addMove(int index, Move m)
    {
        moves.add(index, m);
    }
    
    public int getLength()
    {
        return moves.size();
    }
    
    public String toString()
    {
        return moves.toString();
    }
    
    public void doMoveSet(Board<Piece> board)
    {
        for(Move m : moves)
            m.doMove(board);
    }
    
    public void undoMoveSet(Board<Piece> board)
    {
        for(Move m : moves)
            m.undoMove(board);
    }
    
}
