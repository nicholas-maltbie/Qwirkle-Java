
package model;

/**
 *
 * @author emlemdi_m
 */
public class Player 
{
    private String name;
    private Hand hand;
    private int score;
    private boolean isComputer;
    
    public Player(String name, boolean isComputer)
    {
        hand = new Hand();
        this.name = name;
        this.isComputer = isComputer;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public boolean isComputer()
    {
        return isComputer;
    }
    
    public Hand getHand()
    {
        return hand;
    }
    
    public int getScore()
    {
        return score;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setScore(int amount)
    {
        score = amount;
    }
    
    public void addScore(int amount)
    {
        score+=amount;
    }
    
    public void fillHand(Deck deck)
    {
        while(hand.getSize() < Hand.MAX_SIZE && deck.getSize() > 0)
        {
            hand.addPiece(deck.draw());
        }
    }
}
