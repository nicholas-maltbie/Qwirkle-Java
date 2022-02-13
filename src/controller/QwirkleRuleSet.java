/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.Board;
import model.Piece;
import model.Player;
import model.QwirkleGame;

/**
 * Representation of the Qwirkle rules by using static methods to check validity of
 * game actions.
 * @author Maltbie_N
 */
public class QwirkleRuleSet 
{    
    public static final int QWIRKLE_GROUP_SIZE = 6;
    
    /**
     * Scoring method, must be called after pieces are played. It applies
     * the vertical and horizontal forms of this method be deciding if the move
     * was horizontal or vertical, but if all the pieces were neither played in
     * the same row, nor the same column, it will return 0.
     * @param b - Board that the pieces are located in
     * @param rows - rows in which pieces were played.
     * @param cols - columns in which pieces were played
     * @return the score of the move
     */
    public static int getMoveScore(Board<Piece> b, int[] rows, int[] cols)
    {
        boolean sameRow = true;
        boolean sameCol = true;
        
        for(int playedRow : rows)
            for(int playedRow2 : rows)
                if(playedRow != playedRow2)
                    sameRow = false;
        for(int playedCol : cols)
            for(int playedCol2 : cols)
                if(playedCol != playedCol2)
                    sameCol = false;
        
        if(sameRow)
            return getMoveScoreHoriz(b, rows[0], cols);
        else if(sameCol)
            return getMoveScoreVert(b, rows, cols[0]);
        else
            return 0;        
    }
    
    /**
     * Get the score of a group of pieces played vertically. Must be called after
     * the pieces have been added to the board.
     * @param b - Board that the pieces are located in
     * @param rows - rows in which pieces were played
     * @param col - column that the pieces were played in
     * @return the score the move earned
     */
    public static int getMoveScoreVert(Board<Piece> b, int[] rows, int col)
    {
        List<List<Piece>>extraGroups = new ArrayList<>();
        for(int row : rows)
            extraGroups.add(getGroupHoriz(b, row, col));
        
        int score = 0;
        for(List<Piece> group : extraGroups)
            if(group.size() > 1)
                score += getGroupScore(group);
        
        List<Piece> main = getGroupVert(b,rows[0],col);
        if(main.size() > 1)
            score += getGroupScore(main);
        
        if(score == 0)
            score++;
        return score;
    }
    
    /**
     * Get the score of a group of pieces played horizontally. Must be called after
     * the pieces have been added to the board.
     * @param b - Board that the pieces are located in
     * @param row - row in which the pieces were played in
     * @param cols - columns in which pieces were placed
     * @return the score the move earned
     */
    public static int getMoveScoreHoriz(Board<Piece> b, int row, int[] cols)
    {     
        List<List<Piece>> extraGroups = new ArrayList<>();
        for(int col : cols)
            extraGroups.add(getGroupVert(b, row, col));
        
        int score = 0;
        for(List<Piece> group : extraGroups)
            if(group.size() > 1)
                score += getGroupScore(group);
        
        List<Piece> main = getGroupHoriz(b,row,cols[0]);
        if(main.size() > 1)
            score += getGroupScore(main);
        
        if(score == 0)
            score++;
        return score;
    }
    
    /**
     * Gets the score of a group of pieces, doesn't check if it's valid or not.
     * @param group - Pieces to score
     * @return the int value of the group
     */
    public static int getGroupScore(List<Piece> group)
    {
        if(group.size() < QWIRKLE_GROUP_SIZE)
            return group.size();
        return group.size() * 2;
    }        
    
    /**
     * Checks if a given move of playing piece on board at the location specified
     * by row and col is valid. Check this before the piece has been added to the
     * board.
     * @param b - Board to check the piece 
     * @param p - Piece that will be played
     * @param row - row for the location of Piece p
     * @param col - col for the location of Piece p
     * @return if it is legal to play the piece on board at row col
     */
    public static boolean isLegal(Board<Piece> b, Piece p, int row, int col)
    {
        if(row == b.getNumRows()/2 - 1 && col == b.getNumCols()/2 - 1 && b.isEmpty(row, col))
            return true;            
        else if(!b.isEmpty(row, col))
            return false;
        
        List<Piece> vert = new ArrayList<>();
        if(row > 0)
            vert.addAll(getGroupVert(b, row-1, col));
        if(row < b.getNumRows()-1)
            vert.addAll(getGroupVert(b, row+1, col));
        List<Piece> horiz = new ArrayList();
        if(col > 0)
            horiz.addAll(getGroupHoriz(b, row, col-1));
        if(col < b.getNumCols()-1)
            horiz.addAll(getGroupHoriz(b, row, col+1));
        
        if(horiz.isEmpty() && vert.isEmpty())
            return false;
        
        horiz.add(p);
        vert.add(p);
        return isGroupValid(horiz) && isGroupValid(vert);
    }
    
    /**
     * Checks if a group is valid by qwirkle rules.
     * @param group - group of Pieces to check
     * @return if the group is valid
     */
    public static boolean isGroupValid(List<Piece> group)
    {
        if(group == null)
            return false;
        if(group.size() == 1)
            return true;
        
        for(int index1 = 0; index1 < group.size(); index1++)
            for(int index2 = 0; index2 < group.size(); index2++)
                if(index1 != index2)
                    if(!arePiecesInGroup(group.get(index1), group.get(index2)))
                        return false;
        
        return true;
    }
    
    /**
     * This checks if two pieces could be in the same group
     * @param piece1 - piece one to check
     * @param piece2 - piece two to check
     * @return if the two pieces could be in the same group
     */
    public static boolean arePiecesInGroup(Piece piece1, Piece piece2)
    {
        boolean sameColors = piece1.getColor().equals(piece2.getColor());
        boolean sameShapes = piece1.getShape().equals(piece2.getShape());
        return sameColors != sameShapes;
    }
    
    /**
     * Combination of the three methods for checking move legality.
     * @param played - pieces previously played
     * @param piece - piece currently being played
     * @param row - row of piece being played
     * @param col - column of piece being played
     * @param rows - rows of previous moves
     * @param cols - columns of previously played pieces
     * @param board - board that holds all the pieces
     * @return if a player, given the parameters conditions, can play a piece at the row and column, row col
     */
    public static boolean canPlayPiece(List<Piece> played, Piece piece, int row, int col, List<Integer> rows, List<Integer> cols,
            Board board)
    {
        int[] rowsCopy = new int[rows.size()];
        int[] colsCopy = new int[cols.size()];
        
        for(int i = 0; i < rows.size(); i++)
        {
            rowsCopy[i] = rows.get(i);
            colsCopy[i] = cols.get(i);
        }
        return canPlayPiece(played, piece) && canPlayPiece(board, rowsCopy, colsCopy, row, col) && isLegal(board, piece, row, col);
    }
    
    /**
     * If a player has played Pieces played, this checks if he/she can play the Piece next.
     * @param played - List of Piece that the player has already played
     * @param next - Piece that the player wants to play
     * @return if the player can play the Piece next under given conditions.
     */
    public static boolean canPlayPiece(List<Piece> played, Piece next)
    {
        Piece[] group = played.toArray(new Piece[played.size() + 1]);
        group[played.size()] = next;
        return isGroupValid(Arrays.asList(group));
    }
    
    /**
     * Checks if a player can play a piece in the location row and col if all previously played pieces
     * he/she has played were in rows and cols (Doesn't check if it's valid).
     * @param board - where the pieces are being played
     * @param rows - the rows of previously played pieces
     * @param cols - the columns of previously played pieces
     * @param row - the row of the location for the new piece
     * @param col - the colum of the location for the new piece
     * @return if the new piece can be played in the row and col if the last piece
     * was played in lastRow and lastCol
     */
    public static boolean canPlayPiece(Board<Piece> board, int[] rows, int[] cols, int row, int col)
    {
        if(rows.length == 0 && cols.length == 0)
            return true;
        
        boolean sameRow = true;
        boolean sameCol = true;
        
        for(int playedRow : rows)
            if(row != playedRow)
                sameRow = false;
        for(int playedCol : cols)
            if(col != playedCol)
                sameCol = false;
        
        if(sameRow == sameCol)
            return false;
        if(sameRow)
        {
            int lowestCol = col;
            int greatestCol = col; 
            for(int c : cols)
            {
                if(c < lowestCol)
                    lowestCol = c;
                if(c > greatestCol)
                    greatestCol = c;
            }
            
            if(lowestCol == col)
                lowestCol++;
            else if(greatestCol == col)
                greatestCol--;
            
            for(int c = lowestCol; c <= greatestCol; c++)
                if(board.isEmpty(rows[0], c))
                    return false;
        }
        else
        {
            int lowestRow = row;
            int greatestRow = row; 
            for(int r : rows)
            {
                if(r < lowestRow)
                    lowestRow = r;
                if(r > greatestRow)
                    greatestRow = r;
            }
            
            if(lowestRow == row)
                lowestRow++;
            else if(greatestRow == row)
                greatestRow--;
            
            for(int r = lowestRow; r <= greatestRow; r++)
                if(board.isEmpty(r, cols[0]))
                    return false;            
        }
        
        return true;
    }
    
    /**
     * Gets a horizontal group of pieces on Board of Piece b from anywhere in the
     * horizontal group that intersects row col
     * @param b - board to get the group from
     * @param row - that one piece in the group occupies
     * @param col - that one piece in the group occupies
     * @return the group that contains a piece at row col
     */
    public static List<Piece> getGroupHoriz(Board<Piece> b, int row, int col)
    {
        List<Piece> group = new ArrayList<>();
        if(b.isEmpty(row, col))
            return group;
        while(col > 0 && !b.isEmpty(row, col))
            col--;
        if(b.isEmpty(row, col))
            col++;
        while(col < b.getNumCols() && !b.isEmpty(row, col))
        {
            group.add(b.getPiece(row, col));
            col++;
        }
        return group;
    }
    
    /**
     * Gets a vertical group of pieces on Board of Piece b from anywhere in the
     * vertical group that intersects row col
     * @param b - board to get the group from
     * @param row - that one piece in the group occupies
     * @param col - that one piece in the group occupies
     * @return the group that contains a piece at row col
     */
    public static List<Piece> getGroupVert(Board<Piece> b, int row, int col)
    {
        List<Piece> group = new ArrayList<>();
        if(b.isEmpty(row, col))
            return group;
        while(row > 0 && !b.isEmpty(row, col))
            row--;
        if(b.isEmpty(row, col))
            row++;
        while(row < b.getNumRows() && !b.isEmpty(row, col))
        {
            group.add(b.getPiece(row, col));
            row++;
        }
        return group;
    }
    
    public static boolean isGameOver(QwirkleGame game)
    {
        Player p1 = game.getPlayer(0);
        Player p2 = game.getPlayer(1);
        
        return !canPlayerPlay(game, p1) && !canPlayerPlay(game, p2);
    }
    
    public static boolean canPlayerPlay(QwirkleGame game, Player player)
    {
        if(game.getDeck().getSize() > 0)
            return true;
        
        Board b = game.getBoard();
        List<Piece> pieces = new ArrayList<>();
        for(int i = 0; i < player.getHand().getSize(); i++)
            pieces.add(player.getHand().getPiece(i));
        
        for(int r = 1; r < b.getNumRows()-1; r++)
            for(int c = 1; c < b.getNumCols()-1; c++)
                for(Piece p : pieces)
                    if(isLegal(b, p, r, c))
                        return true;
        return false;
    }
}
