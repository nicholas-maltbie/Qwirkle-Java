/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qwirkle;

import controller.Computer;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.Board;
import model.Deck;
import model.Hand;
import model.Piece;
import model.Piece.Shape;
import model.Player;
import view.BoardListener;
import view.HandListener;

/**
 *
 * @author Nick_Pro
 */
public class TestGame implements BoardListener, HandListener, ActionListener
{

    private TestView view;
    private Board<Piece> board;
    private Player computer;
    
    public TestGame()
    {
        board = new Board<>(150, 150);
        computer = new Player("Computer", true);
        
        view = new TestView(board, computer);
        
        view.getBoardView().addBoardListener(this);
        view.getComputerView().addHandListener(this);
        view.getActionButton().addActionListener(this);
        
        view.setVisible(true);
    }
    
    @Override
    public void buttonClicked(int row, int col) 
    {
        for(int r = 0; r < board.getNumRows(); r++)
            for(int c = 0; c < board.getNumCols(); c++)
                view.getBoardView().highlightPiece(r, c, null);
        view.update();
        view.repaint();
        Piece p = getPieceFromUser();
        board.setPiece(row, col, p);   
        view.update();
        view.repaint();     
    }

    @Override
    public void handClicked(int index) 
    {
        for(int r = 0; r < board.getNumRows(); r++)
            for(int c = 0; c < board.getNumCols(); c++)
                view.getBoardView().highlightPiece(r, c, null);
        view.update();
        view.repaint();
        Piece p = getPieceFromUser();
        Hand h = computer.getHand();
        if(h.getSize() > index)
            h.removePiece(index);
        if(p != null)
            h.addPiece(p);
        view.update();
        view.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        Deck d = new Deck();
        d.empty();
        Computer.makeMove(board, d, computer, view.getBoardView());
        view.update();
        view.repaint();
    }
    
    public Piece getPieceFromUser()
    {
        Color color = Color.RED;
        String colorName = (String)JOptionPane.showInputDialog(
                            view,
                            "Chose a color",
                            "Color choice",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            Piece.colorNames,
                            Piece.colorNames[0]);
        
        for(int c = 0; c < Piece.colorNames.length; c++)
        {
            if(Piece.colorNames[c].equals(colorName))
                color = Piece.colors[c];
        }
        
        if(colorName == null)
            return null;
        
        return new Piece(color, (Shape)JOptionPane.showInputDialog(
                            view,
                            "Chose a shape",
                            "Shape choice",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            Piece.shapes,
                            Piece.shapes[0]));

    }
    
    public static void main(String[] args)
    {
        TestGame game = new TestGame();
    }
}
