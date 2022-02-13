
package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import model.Board;
import model.Piece;

/**
 *
 * @author emlemdi_m
 */
public class BoardView extends JPanel implements ActionListener
{
    private Point lastPressed;
    private List<BoardListener> listeners;
    private PieceButton[][] buttons;
    private Board<Piece> board;
    
    private Rectangle dimensions;
    
    public BoardView(Board<Piece> board)
    {
        super();
        this.board = board;
        buttons = new PieceButton[board.getNumRows()][board.getNumCols()];
        listeners = new ArrayList<>();
        
        dimensions = new Rectangle(board.getNumCols()/2 - 5, board.getNumRows()/2 - 5, 9, 9);
        
        for(int r=0; r<board.getNumRows(); ++r)
        {
            for(int c=0; c<board.getNumCols(); ++c)
            {
                PieceButton piece = new PieceButton(board.getPiece(r, c));
                piece.addActionListener(this);
                buttons[r][c] = piece;
            }
        }
        
        updateButtons();
    }
    
    public final void updateButtons()
    {
        List<Component> comps = new ArrayList<>(Arrays.asList(this.getComponents()));
        for(PieceButton[] row : buttons)
            for(PieceButton button : row)
                if(comps.contains(button))
                    this.remove(button);
        
        this.setLayout(new GridLayout(dimensions.height, dimensions.width));
        for(int row = 0; row < dimensions.height; row++)
            for(int col = 0; col < dimensions.width; col++)
            {
                this.add(buttons[row + dimensions.y][col + dimensions.x]);
                buttons[row + dimensions.y][col + dimensions.x].repaint();
                buttons[row + dimensions.y][col + dimensions.x].setIcon(new ImageIcon(new BufferedImage(64,64,BufferedImage.TYPE_4BYTE_ABGR)));
            }
    }
    
    public final void updateDimensions()
    {
        int up = getUpper(dimensions.y + 1);
        int down = getLowest(dimensions.y + dimensions.height - 2);
        int left = getLeftmost(dimensions.x + 1);
        int right = getRightmost(dimensions.x + dimensions.width - 2);        
        
        Rectangle dims = new Rectangle(left, up, right - left, down - up);
        this.dimensions = dims;
    }
    
    public int getUpper(int lowest)
    {
        int uppermost = lowest;
        for(int row = 0; row < board.getNumRows(); row++)
            for(int col = 0; col < board.getNumCols(); col++)
                if(!board.isEmpty(row, col))
                    if(row < uppermost)
                        uppermost = row;
        if(uppermost != 0)
            return uppermost - 1;
        return uppermost;
    }
    
    public int getLowest(int upper)
    {
        int lowest = upper;
        for(int row = 0; row < board.getNumRows(); row++)
            for(int col = 0; col < board.getNumCols(); col++)
                if(!board.isEmpty(row, col))
                    if(row > lowest)
                        lowest = row;
        if(lowest != board.getNumRows())
            return lowest + 2;
        return lowest;
    }
    
    public int getRightmost(int leftmost)
    {
        int rightmost = leftmost;
        for(int row = 0; row < board.getNumRows(); row++)
            for(int col = 0; col < board.getNumCols(); col++)
                if(!board.isEmpty(row, col))
                    if(col > rightmost)
                        rightmost = col;
        if(rightmost != board.getNumCols())
            return rightmost + 2;
        return rightmost;
    }
    
    public int getLeftmost(int rightmost)
    {
        int leftmost = rightmost;
        for(int row = 0; row < board.getNumRows(); row++)
            for(int col = 0; col < board.getNumCols(); col++)
                if(!board.isEmpty(row, col))
                    if(col < leftmost)
                        leftmost = col;
        if(leftmost != 0)
            return leftmost - 1;
        return leftmost;
    }
       
    public void highlightPiece(int row, int col, Color tint)
    {
        buttons[row][col].setTint(tint);
    }
    
    public Color getHighlight(int row, int col)
    {
        return buttons[row][col].getTint();
    }

    public void update()
    {
        updateDimensions();
        updateButtons();
        for(int row = 0; row < board.getNumRows(); row++)
            for(int col = 0; col < board.getNumCols(); col++)
            {
                PieceButton button = buttons[row][col];
                if((board.getPiece(row, col) == null && button.getPiece() != null) ||
                        (board.getPiece(row, col) != null && button.getPiece() == null) ||
                        (board.getPiece(row, col) != null && button.getPiece() != null && !board.getPiece(row, col).equals(board.getPiece(row, col))))
                {
                    button.setPiece(board.getPiece(row, col));
                }
            }
    }
    
    public Point getLastPressed()
    {
        if(lastPressed != null)
            return new Point(lastPressed);
        return null;
    }
    
    public void addBoardListener(BoardListener listener)
    {
        listeners.add(listener);
    }
    
    public void removeBoardListener(BoardListener listener)
    {
        listeners.remove(listener);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() instanceof PieceButton)
        {
            GridLayout grid = (GridLayout) this.getLayout();
            int row = 0;
            int col = 0;
            for(int r = 0; r < board.getNumRows(); r++)
                for(int c = 0; c < board.getNumCols(); c++)
                    if(buttons[r][c] == e.getSource())
                    {
                        row = r; 
                        col = c;
                    }            
            lastPressed = new Point(col, row);
            //board.setPiece(row, col, new Piece());
            //update();
            for(BoardListener listener : listeners)
            {
                listener.buttonClicked(row, col);
            }
        }
    }
    
}
