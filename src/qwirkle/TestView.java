/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qwirkle;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import model.Board;
import model.Piece;
import model.Player;
import view.BoardView;
import view.PlayerView;

/**
 *
 * @author Nick_Pro
 */
public class TestView extends JFrame
{
    private PlayerView computer;
    private BoardView board;
    private JButton decide;
    
    public TestView(Board<Piece> b, Player comp)
    {
        super("Test Game");
        
        board = new BoardView(b);
        computer = new PlayerView(comp);
        decide = new JButton("Make move");
        
        Container cont = this.getContentPane();
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        cont.setLayout(layout);
        
        this.setBounds(100, 100, 700, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        board.setPreferredSize(new Dimension(400,200));
        board.setMinimumSize(new Dimension(400, 200));
        
        computer.setPreferredSize(new Dimension(400,200));
        computer.setMinimumSize(new Dimension(400, 200));

        c.fill = GridBagConstraints.BOTH;
        
        c.weightx = 1;
        c.weighty = 1;
        cont.add(computer, c);
        
        c.gridx = 1;
        cont.add(decide, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        cont.add(board,c);
    }
    
    public BoardView getBoardView()
    {
        return board;
    }
    
    public JButton getActionButton()
    {
        return decide;
    }
    
    public PlayerView getComputerView()
    {
        return computer;
    }
    
    public void update()
    {
        board.update();
    }
}
