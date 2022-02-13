/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unused;

/**
 *
 * @author Maltbie_N
 */
public class QwirkleGame 
{
    private Board<Piece> board;
    private Player player1, player2;
    private Deck deck;
    
    public QwirkleGame(Player player1, Player player2)
    {
        board = new Board<>(51,51);
        this.player1 = player1;
        this.player2 = player2;
        deck = new Deck();
        deck.shuffle();
    }
    
    public Board<Piece> getBoard()
    {
        return board;
    }
    
    public Player getPlayer1()
    {
        return player1;
    }
    
    public Player getPlayer2()
    {
        return player2;
    }
    
    public Deck getDeck()
    {
        return deck;
    }
}
