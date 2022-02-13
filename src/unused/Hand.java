

package unused;

import java.util.ArrayList;

/**
 *
 * @author emlemdi_m
 */
public class Hand 
{
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
    
    public void addPiece(Piece p)
    {
        pieces.add(p);
    }

    
}
