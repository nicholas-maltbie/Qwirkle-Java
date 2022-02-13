

package model;

import qwirkle.*;
import java.util.ArrayList;

/**
 *
 * @author emlemdi_m
 */
public class Hand 
{
    public static final int MAX_SIZE = 6;
    private ArrayList<Piece> pieces;
    
    public Hand ()    
    {
        pieces = new ArrayList<>();
    }
    
    public Piece getPiece(int index)
    {
        return pieces.get(index);
    }
    
    public int getSize()
    {
        return pieces.size();
    }
    
    public Piece removePiece(int index)
    {
        return pieces.remove(index);
    }
    
    public boolean removePiece(Piece piece)
    {
        for(int i = 0; i < getSize(); i++)
        {
            if(getPiece(i).equals(piece))
            {
                removePiece(i);
                return true;
            }
        }
        return false;
    }
    
    public void addPiece(Piece p)
    {
        pieces.add(p);
    }

    
}
