/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 *
 * @author Maltbie_N
 */
public class QwirkleGame 
{
    private Board<Piece> board;
    private Player player1, player2;
    private Deck deck;
    private int currentPlayer;
    
    public QwirkleGame(Player player1, Player player2, int startPlayer)
    {
        board = new Board<>(100,100);
        this.player1 = player1;
        this.player2 = player2;
        currentPlayer = startPlayer;
        deck = new Deck();
        deck.shuffle();
        
        Hand hand1 = player1.getHand();
        Hand hand2 = player2.getHand();
        
        for(int i = 0; i < Hand.MAX_SIZE; i++)
        {
            hand1.addPiece(deck.draw());
            hand2.addPiece(deck.draw());
        }
    }
    
    public Board<Piece> getBoard()
    {
        return board;
    }
    
    public Player getPlayer(int player)
    {
        if(player == 0)
            return player1;
        return player2;
    }
    
    public Deck getDeck()
    {
        return deck;
    }
    
    public int getCurrentPlayer()
    {
        return currentPlayer;
    }
    
    public void nextPlayer()
    {
        currentPlayer++;
        currentPlayer %=2;
    }
}
