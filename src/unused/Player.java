
package unused;

/**
 *
 * @author emlemdi_m
 */
public class Player 
{
    private String name;
    private Hand hand;
    private int score;
    
    public Player(String name)
    {
        hand = new Hand();
        this.name = name;
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
}
