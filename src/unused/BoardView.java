
package unused;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author emlemdi_m
 */
public class BoardView extends JPanel implements ActionListener
{
    private Point lastPressed;
    private List<BoardListener> listeners;
    private GridLayout grid;
    private PieceButton[][] buttons;
    private Board<Piece> board;
    
    public BoardView(Board<Piece> board)
    {
        super();
        this.board = board;
        grid = new GridLayout();
        grid.setRows(board.getNumRows());
        grid.setColumns(board.getNumCols());
        buttons = new PieceButton[grid.getRows()][grid.getColumns()];
        listeners = new ArrayList<>();
        this.setLayout(grid);
        
        for(int r=0; r<board.getNumRows(); ++r)
        {
            for(int c=0; c<board.getNumCols(); ++c)
            {
                PieceButton piece = new PieceButton(board.getPiece(r, c));
                this.add(piece);
                piece.addActionListener(this);
                buttons[r][c] = piece;
            }
        }
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
        for(int row = 0; row < board.getNumRows(); row++)
            for(int col = 0; col < board.getNumCols(); col++)
                buttons[row][col].setPiece(board.getPiece(row, col));
        this.repaint();
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
            for(BoardListener listener : listeners)
            {
                int row = 0;
                int col = 0;
                for(int r = 0; r < grid.getRows(); r++)
                    for(int c = 0; c < grid.getColumns(); c++)
                        if(buttons[r][c] == e.getSource())
                        {
                            row = r; 
                            col = c;
                        }
                lastPressed = new Point(col, row);
                listener.buttonClicked(row, col);
            }
        }
    }
    
}
