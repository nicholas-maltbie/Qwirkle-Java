/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import model.Board;
import model.Deck;
import model.Hand;
import model.Piece;
import model.Player;
import view.BoardView;
import view.PlayerView;
import view.QwirkleView;

/**
 *
 * @author Maltbie_N
 */
public class Computer 
{
    private Computer()
    {
        
    }
    
    public static void makeMove(Board<Piece> board, Deck deck, Player computer, BoardView view)
    {
        Hand h = computer.getHand();
        List<Piece> pieces = new ArrayList<>();
        for(int i = 0; i < h.getSize(); i++)
            pieces.add(h.getPiece(i));
        
        List<MoveSet> moves = getPossibleMoveSets(board, pieces, new ArrayList<Piece>(), new ArrayList<Integer>(), new ArrayList<Integer>());
        List<MoveSet> highValueMoves = new ArrayList<>();
        int highScore = 0;
        
        for(MoveSet move : moves)
        {
            move.doMoveSet(board);
            int score = QwirkleRuleSet.getMoveScore(board, move.getRows(), move.getCols());
            if(score > highScore)
            {
               highValueMoves.clear();
               highScore = score;
            }
            if(score == highScore)
                highValueMoves.add(move);
            move.undoMoveSet(board);
        }
        
        if(highScore <= 1)
        {
            for(int i = 0; i < computer.getHand().getSize(); i++)
            {
                if(Math.random() > .5)
                {
                    deck.addPiece(computer.getHand().removePiece(1));
                }
            }
        }
        else
        {
            MoveSet desired = highValueMoves.remove((int)(Math.random() * highValueMoves.size()));
            desired.doMoveSet(board);
            computer.addScore(highScore);
            
            for(int i = 0; i < desired.getLength(); i++)
            {
                computer.getHand().removePiece(desired.getMove(i).getPiece());
                view.highlightPiece(desired.getMove(i).getRow(), desired.getMove(i).getCol(), Color.ORANGE);
            }
        }
        
        computer.fillHand(deck);
    }
    
    public static List<MoveSet> getPossibleMoveSets(Board<Piece> board, List<Piece> left, List<Piece> played,
            List<Integer> rows, List<Integer> cols)
    {
        List<MoveSet> moveSets = new ArrayList<>();
        
        //view.setStatus(player, "Thinking... adding moves");
        
        for(int index = 0; index < left.size(); index++)
        {
            List<Piece> newLeft = new ArrayList<>(left);
            Piece play = newLeft.remove(index);
            
            List<Piece> newPlayed = new ArrayList<>(played);
            newPlayed.add(play);
            
            List<Move> moves = getPossibleMoves(board, play, played, rows, cols);
            
            for(Move move : moves)
            {
                Board<Piece> boardCopy = new Board<>(board);
                
                List<Integer> newRows = new ArrayList<>(rows);
                List<Integer> newCols = new ArrayList<>(cols);
                newRows.add(move.getRow());
                newCols.add(move.getCol());

                boardCopy.setPiece(move.getRow(), move.getCol(), move.getPiece());
                                
                if(newLeft.size() > 0)
                    for(MoveSet moveSet : getPossibleMoveSets(boardCopy, newLeft, newPlayed, newRows, newCols))
                    {
                        moveSet.addMove(0, move);
                        moveSets.add(moveSet);
                    }
                MoveSet moveSet = new MoveSet();
                moveSet.addMove(move);
                moveSets.add(moveSet);
            }
        }
        
        return moveSets;
    }
    
    public static List<Move> getPossibleMoves(Board<Piece> board, Piece play, List<Piece> played, List<Integer> rows, List<Integer> cols)
    {
        //view.setStatus(player, "Thinking... checking moves");
        List<Move> moves = new ArrayList<>();
        if(play == null)
            return moves;
        for(int r = 0; r < board.getNumRows(); r++)
            for(int c = 0; c < board.getNumCols(); c++)
                if(QwirkleRuleSet.canPlayPiece(played, play, r, c, rows, cols, board))
                    moves.add(new Move(r ,c ,play));
        return moves;
    }
    
    /*public static List<MoveSet> getPossible(Board<Piece> board, List<MoveSet> moves,
            MoveSet move, Piece play, List<Piece> left, List<Piece> played,
            List<Integer> rows, List<Integer> cols)
    {
        for(int r = 0; r < board.getNumRows(); r++)
            for(int c = 0; c < board.getNumCols(); c++)
            {
                if(QwirkleRuleSet.canPlayPiece(played, play, r, c, rows, cols, board))
                {
                    rows.add(r);
                    cols.add(c);
                    for(int i = 0; i < left.size(); i++)
                    {
                        m
                        moves.addAll(getPossible(board, moves, move, ))
                    }
                    rows.remove(rows.size()-1);
                    cols.remove(cols.size()-1);
                }
            }
        if(left.isEmpty())
        {
            moves.add(move);
            return moves;
        }
    }*/
    
}
