package unused;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
/**
 *
 * @author laritz_m
 */
public class HandView extends JPanel implements ActionListener
{
    private List<HandListener> listeners;
    
    private int lastPressed;
    private Hand hand;
    private PieceButton[] handButtons;
    
    public HandView(Hand hand)
    {
        super();
        this.hand = hand;
        handButtons = new PieceButton[6];
        listeners = new ArrayList<>();
        
        this.setLayout(new GridLayout(1, 6));
        
        for (int i=0; i < 6; ++i)
        {
            PieceButton button = new PieceButton(null);
            this.add(button);
            button.addActionListener(this);
            handButtons[i] = button;
        }
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        for (int i=0; i < 6; ++i)
        {
            PieceButton button = handButtons[i];
            if(i < hand.getSize())
                button.setPiece(hand.getPiece(i));
            else
                button.setPiece(null);
        }
        super.paintComponent(g);
    }
    
    public Hand getHand()
    {
        return hand;
    }

    public int getLastPressed()
    {
        return lastPressed;
    }
    
    public void addHandListener(HandListener listener)
    {
        listeners.add(listener);
    }
    
    public void removeHandListener(HandListener listener)
    {
        listeners.remove(listener);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof PieceButton)
        {
            for(HandListener listener : listeners)
            {
                int index = 0; 
                for(int button = 1; button < handButtons.length; button++)
                    if(e.getSource() == handButtons[button])
                        index = button;
                lastPressed = index;
                listener.handClicked(index);
            }
        }
    }
}
