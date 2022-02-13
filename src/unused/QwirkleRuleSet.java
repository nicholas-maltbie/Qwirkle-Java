/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unused;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Representation of the Qwirkle rules by using static methods to check validity of
 * game actions.
 * @author Maltbie_N
 */
public class QwirkleRuleSet 
{    
    public static final int QWIRKLE_GROUP_SIZE = 6;
    
    /**
     * Get the score of a group of pieces played horizontally. Must be called after
     * the pieces have been added to the board.
     * @param b - Board that the pieces are located in
     * @param played - number of pieces played
     * @param row - starting (lowest) row located in the top row that pieces were played in
     * @param col - starting (lowest) col located in the leftmost row that pieces were played in
     * @return the score the move earned
     */
    public static int getMoveScoreHoriz(Board<Piece> b, int played, int row, int col)
    {
        List<List<Piece>>extraGroups = new ArrayList<>();
        for(int index = 0; index < played; index++)
            if(col+index < b.getNumCols())
                extraGroups.add(getGroupVert(b, row, col+index));
        
        int score = 0;
        for(List<Piece> group : extraGroups)
            if(group.size() > 1)
                score += getGroupScore(group);
        
        List<Piece> main = getGroupHoriz(b,row,col);
        if(main.size() > 1)
            score += getGroupScore(main);
        
        return score;
    }
    
    /**
     * Get the score of a group of pieces played vertically. Must be called after
     * the pieces have been added to the board.
     * @param b - Board that the pieces are located in
     * @param played - number of pieces played
     * @param row - starting (lowest) row located in the top row that pieces were played in
     * @param col - starting (lowest) col located in the leftmost row that pieces were played in
     * @return the score the move earned
     */
    public static int getMoveScoreVert(Board<Piece> b, int played, int row, int col)
    {        
        List<Piece>[] extraGroups = new ArrayList[played];
        for(int index = 0; index < played; index++)
            if(row + index < b.getNumRows())
                extraGroups[index] = getGroupHoriz(b, row+index, col);
        
        int score = 0;
        for(List<Piece> group : extraGroups)
            if(group.size() > 1)
                score += getGroupScore(group);
        
        List<Piece> main = getGroupVert(b,row,col);
        if(main.size() > 1)
            score += getGroupScore(main);
        
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
        if(!b.isEmpty(row, col))
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
        boolean sameColors = false;
        boolean sameShapes = false;
        
        for(int index1 = 0; index1 < group.size(); index1++)
            for(int index2 = index1+1; index2 < group.size(); index2++)
            {
                Piece piece1 = group.get(index1);
                Piece piece2 = group.get(index2);
                if(piece1.getColor().equals(piece2.getColor()))
                    sameColors = true;
                else if(piece1.getShape().equals(piece2.getShape()))
                    sameShapes = true;
            }
        
        return sameColors != sameShapes;
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
     * Checks if a player can play a piece in the location row and col if the last piece
     * he has played was in lastRow and lastCol (Doesn't check if it's valid).
     * @param lastRow - the row the last piece was played in
     * @param lastCol - the colum that the last piece was played in
     * @param row - the row of the location for the new piece
     * @param col - the colum of the location for the new piece
     * @return if the new piece can be played in the row and col if the last piece
     * was played in lastRow and lastCol
     */
    public static boolean canPlayPiece(int lastRow, int lastCol, int row, int col)
    {
        return (lastRow == row) != (lastCol == col);
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
        while(!b.isEmpty(row, col) && col > 0)
            col--;
        if(b.isEmpty(row, col))
            col++;
        while(!b.isEmpty(row, col) && col < b.getNumCols())
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
        while(!b.isEmpty(row, col) && row > 0)
            row--;
        if(b.isEmpty(row, col))
            row++;
        while(!b.isEmpty(row, col) && row < b.getNumRows())
        {
            group.add(b.getPiece(row, col));
            row++;
        }
        return group;
    }
}
