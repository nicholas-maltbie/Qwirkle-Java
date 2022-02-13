/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unused;

import java.util.Comparator;

/**
 *
 * @author emlemdi_m
 */
public class PieceComparator implements Comparator<Piece>
{
    @Override
    public int compare(Piece p1, Piece p2)
    {
        if(!p1.getShape().equals(p2.getShape()))
            return p1.getShape().compareTo(p2.getShape());
        return 0;
    }
}
