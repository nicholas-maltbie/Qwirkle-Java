package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 *
 * @author emlemdi_m
 */
public class Deck 
{
    private List<Piece> pieces;
    
    public Deck()
    {
        pieces = new ArrayList<>();
        for(int i = 0; i < 3; i++)
        {
            for(int color = 0; color < Piece.colors.length; color++)
            {
                for(int shape = 0; shape < Piece.shapes.length; shape++)
                {
                    Piece piece = new Piece(Piece.colors[color], Piece.shapes[shape]);
                    addPiece(piece);
                }
            }
        }
    }
    
    public void addPiece(Piece p)
    {
        pieces.add(p);
    }
    
    public void empty()
    {
        pieces.removeAll(pieces);
    }
    
    public void shuffle()
    {
        Collections.shuffle(pieces);
    }
    
    public int getSize()
    {
        return pieces.size();
    }
    
    public Piece getPiece(int index)
    {
        return pieces.get(index);
    }
    
    public void sort()
    {
        Piece[] piecesCopy = pieces.toArray(new Piece[pieces.size()]);
        Arrays.sort(piecesCopy, new PieceComparator());
        pieces = new ArrayList<>(Arrays.asList(piecesCopy));
    }
    
    public Piece removePiece(int index)
    {
        return pieces.remove(index);
    }
    
    
    public Piece draw()
    {
        return removePiece(0);
    }
    
    @Override
    public String toString()
    {
        return pieces.toString();
    }
}
