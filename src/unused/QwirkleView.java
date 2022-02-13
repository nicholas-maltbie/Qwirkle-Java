package unused;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author Master Coder - Nick Maltbie
 */
public class QwirkleView extends JFrame implements ActionListener
{
    private List<ViewListener> listeners;
    
    private QwirkleGame game;
    private BoardView boardView;
    private PlayerView player1;
    private PlayerView player2;
    private JButton endTurnButton;
    private JButton swapButton;
    private JButton undoButton;
    
    public QwirkleView(QwirkleGame game)
    {
        super("Qwirkle");
        this.game = game;
        listeners = new ArrayList<>();
        endTurnButton = new JButton("End");
        swapButton = new JButton("Swap");
        undoButton = new JButton("Undo");
        boardView = new BoardView(game.getBoard());
        player1 = new PlayerView(game.getPlayer1());
        player2 = new PlayerView(game.getPlayer2());
        
        player1.setPreferredSize(new Dimension(200,100));
        player1.setMinimumSize(new Dimension(200,100));
        player2.setPreferredSize(new Dimension(200,100));
        player2.setMinimumSize(new Dimension(200,100));
        boardView.setPreferredSize(new Dimension(450,450));
        boardView.setMinimumSize(new Dimension(450,450));
        
        endTurnButton.setPreferredSize(new Dimension(200,200));
        swapButton.setPreferredSize(new Dimension(200,200));
        undoButton.setPreferredSize(new Dimension(200,200));
        
        endTurnButton.addActionListener(this);
        swapButton.addActionListener(this);
        undoButton.addActionListener(this);
        
        System.out.println(endTurnButton.getActionListeners() + " " + this);
        
        Container cont = this.getContentPane();
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        cont.setLayout(layout);
        
        c.weightx = .5;
        //c.weighty = .5;
        
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        cont.add(player1, c);
        
        c.weightx = 0;
        c.gridx = 1;
        cont.add(Box.createHorizontalStrut(10), c);
        
        c.weightx = .5;
        c.gridx = 2;
        cont.add(endTurnButton);
        
        c.weightx = 0;
        c.gridx = 3;
        cont.add(Box.createHorizontalStrut(10), c);
        
        c.weightx = .5;
        c.gridx = 4;
        cont.add(swapButton);
        
        c.weightx = 0;
        c.gridx = 5;
        cont.add(Box.createHorizontalStrut(10), c);
        
        c.weightx = .5;
        c.gridx = 6;
        cont.add(player2, c);
        
        c.weightx = 0;
        c.gridx = 0;
        c.gridwidth = 4;
        c.gridy = 1;
        cont.add(Box.createVerticalStrut(10), c);
        
        c.weightx = .5;
        c.gridx = 0;
        c.gridwidth = 3;
        c.gridy = 1;
        cont.add(Box.createVerticalStrut(50), c);
        
        c.weightx = .5;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 3;
        c.gridwidth = 1;
        c.gridy = 1;
        cont.add(undoButton, c);
        
        c.weightx = .5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 5;
        c.gridwidth = 3;
        c.gridy = 1;
        cont.add(Box.createVerticalStrut(50), c);
        
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 2.5;
        c.gridx = 0;
        c.gridwidth = 7;
        c.gridy = 2;
        c.gridheight = 3;
        cont.add(boardView, c);
    }
    
    public void setBoardHighlight(int row, int col, Color tint)
    {
        boardView.highlightPiece(row, col, tint);
    }
    
    public Color getHighlight(int row, int col)
    {
        return boardView.getHighlight(row, col);
    }
    
    public void update()
    {
        this.getContentPane().repaint();
        boardView.update();
    }
    
    public void addEndTurnListener(ActionListener listener)
    {
        endTurnButton.addActionListener(listener);
    }
    
    public void removeEndTurnListener(ActionListener listener)
    {
        endTurnButton.removeActionListener(listener);
    }
    
    public void addUndoButtonListener(ActionListener listener)
    {
        undoButton.addActionListener(listener);
    }
    
    public void removeUndoButtonListener(ActionListener listener)
    {
        undoButton.removeActionListener(listener);
    }
    
    public void addSwapButtonListener(ActionListener listener)
    {
        swapButton.addActionListener(listener);
    }
    
    public void removeSwapButtonListener(ActionListener listener)
    {
        swapButton.removeActionListener(listener);
    }
    
    public void addBoardListener(BoardListener listener)
    {
        boardView.addBoardListener(listener);
    }
    
    public void removeBoardListener(BoardListener listener)
    {
        boardView.removeBoardListener(listener);
    }
    
    public Point getLastBoardButtonPressed()
    {
        return boardView.getLastPressed();
    }
    
    public void addPlayer1HandListener(HandListener listener)
    {
        player1.addHandListener(listener);
    }
    
    public void addPlayer2HandListener(HandListener listener)
    {
        player2.addHandListener(listener);
    }
    
    public void removePlayer1HandListener(HandListener listener)
    {
        player1.removeHandListener(listener);
    }
    
    public void removePlayer2HandListener(HandListener listener)
    {
        player2.removeHandListener(listener);
    }
    
    public int getLastPlayer1Pressed()
    {
        return player1.getLastPressed();
    }
    
    public int getLastPlayer2Pressed()
    {
        return player2.getLastPressed();
    }

    public void addButtonListener(ViewListener listener)
    {
        listeners.add(listener);
    }
    
    public void removeButtonListener(ViewListener listener)
    {
        listeners.remove(listener);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == endTurnButton)
        {
            for(ViewListener listener : listeners)
                listener.endTurnButtonPressed();
        }
        else if(e.getSource() == undoButton)
        {
            for(ViewListener listener : listeners)
                listener.undoButtonPressed();
        }
        else if(e.getSource() == swapButton)
        {
            for(ViewListener listener : listeners)
                listener.swapButtonPressed();
        }
    }
    
    /*public static void main(String[] args)
    {
        //System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        QwirkleGame game = new QwirkleGame(new Player("RED"), new Player("BLU"));
        QwirkleView view = new QwirkleView(game);
        Board<Piece> board = game.getBoard();
        board.setPiece(0, 0, new Piece());
        view.setBoardHighlight(0, 0, Color.RED);
        
        
        view.setVisible(true);
        view.setBounds(100,100,700,700);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        view.update();
    }*/
}
