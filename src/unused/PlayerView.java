/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unused;

import java.awt.Graphics;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author laritz_m
 */
public class PlayerView extends JPanel
{
    private Player player;
    
    private HandView handView;
    private JLabel score;
    private JLabel name;
    
    public PlayerView(Player player)
    {
        super();
        this.player = player;
        handView = new HandView(this.player.getHand());
        score = new JLabel(Integer.toString(player.getScore()));
        name = new JLabel(player.getName());
        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        this.add(name);
        this.add(score);
        this.add(handView);
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        score.setText(Integer.toString(player.getScore()));
        name.setText(player.getName());
    }
    
    public int getLastPressed()
    {
        return handView.getLastPressed();
    }
    
    public void addHandListener(HandListener listener)
    {
        handView.addHandListener(listener);
    }
    
    public void removeHandListener(HandListener listener)
    {
        handView.removeHandListener(listener);
    }    
}
