/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Player;

/**
 *
 * @author laritz_m
 */
public class PlayerView extends JPanel
{
    private Player player;
    
    private HandView handView;
    private JLabel score;
    private String statusMessage = "nothing";
    private JLabel status;
    private JLabel name;
    
    public PlayerView(Player player)
    {
        super();
        this.player = player;
        handView = new HandView(this.player.getHand());
        score = new JLabel(Integer.toString(player.getScore()));
        name = new JLabel(player.getName());
        status = new JLabel("Status : ");
        
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        this.setLayout(layout);
        
        c.weightx = .5;
        //c.weighty = .5;
        
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        this.add(name, c);
        
        c.weightx = 0;
        c.gridx = 1;
        this.add(Box.createHorizontalStrut(10), c);
        
        c.weightx = .5;
        c.gridx = 2;
        this.add(score,c);
        score.setFont(new Font("TimesNewRoman", Font.BOLD, 20));
        
        c.weighty = 1;
        c.gridy = 1;
        c.gridx = 0;
        c.gridwidth = 3;
        this.add(handView,c);
        
        c.gridy = 2;
        this.add(status,c);
    }
    
    public void highlightPiece(int index, Color color)
    {
        handView.highlightPiece(index, color);
    }
    
    public Color getHighlight(int index)
    {
        return handView.getHighlight(index);
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        score.setText(Integer.toString(player.getScore()));
        name.setText(player.getName());
        status.setText("Status : " + statusMessage);
    }
    
    public String getStatus()
    {
        return statusMessage;
    }
    
    public void setStatus(String message)
    {
        statusMessage = message;
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
    
    public void blockHand()
    {
        handView.block();
    }
    
    public void revealHand()
    {
        handView.reveal();
    }
}
