/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import model.Piece;

/**
 *
 * @author emlemdi_m
 */
public class PieceButton extends JButton
{
    private boolean blocked;
    private BufferedImage image;
    private Color tint;
    private Piece piece;
    
    public PieceButton(Piece piece)
    {
        super();
        this.piece = piece;
    }
    
    public Color getTint()
    {
        return tint;
    }
    
    public void setTint(Color color)
    {
        this.tint = color;
    }
    
    public void block()
    {
        blocked = true;
    }
    
    public void reveal()
    {
        blocked = false;
    }
    
    public boolean isBlocked()
    {
        return blocked;
    }
    
    public Piece getPiece()
    {
        return piece;
    }
    
    public void setPiece(Piece piece)
    {
        this.piece = piece;
        updateButton();
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if(!blocked)
        {
            if(tint != null)
            {
                g2.setColor(new Color(tint.getRed(), tint.getGreen(), tint.getBlue(), 130));
                g2.fill(new Rectangle2D.Float(0, 0, this.getWidth(), this.getHeight()));
            }
            if(image != null)
            {
                int size = (int)(this.getWidth()*.8);
                if(size > this.getHeight() *.8)
                    size = (int)(this.getHeight()*.8);
                AffineTransform trans = new AffineTransform(size*1.0/image.getWidth(), 
                        0, 0, size*1.0/image.getHeight(),
                        this.getWidth()*1.0/2 - size/2, 
                        this.getHeight()*1.0/2 - size/2);
                //AffineTransform.getScaleInstance(size*1.0/image.getWidth(), size*1.0/image.getHeight());
                g2.drawImage(image, trans, null);
            }
        }
        else
        {
            if(this.getPiece() != null)
            {
                g2.setColor(Color.BLACK);
                g2.fill(new Rectangle2D.Float(0, 0, this.getWidth(), this.getHeight()));
            }
        }
    }
    
    private Piece lastPiece;
    
    public void updateButton()
    {
        if(piece != null && piece != lastPiece)
        {
            try {
                image = ImageProcesses.outlineImage(
                            ImageProcesses.getTintedImageIgnoreAlpha(
                                ImageIO.read(new File("assets\\" + piece.getShape().toString() + ".png")), piece.getColor()),
                            Color.BLACK);
            } catch (IOException ex) {
                Logger.getLogger(PieceButton.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(piece == null)
            image = null;
        lastPiece = piece;
    }
    
}
