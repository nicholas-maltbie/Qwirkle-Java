/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unused;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;

/**
 *
 * @author emlemdi_m
 */
public class PieceButton extends JButton
{
    private Image image;
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
        if(tint != null)
        {
            g2.setColor(new Color(tint.getRed(), tint.getBlue(), tint.getGreen(), 130));
            g2.fill(new Rectangle2D.Float(0, 0, this.getWidth(), this.getHeight()));
        }
        if(image != null && this.getWidth() != 0 && this.getHeight() != 0)
        {
            AffineTransform trans = new AffineTransform();
            double xScale = (double)this.getWidth()/image.getWidth(null);
            double yScale = (double)this.getHeight()/image.getHeight(null);
            double scale = yScale;
            if(xScale < yScale)
                scale = xScale;
            trans.scale(scale *= .8, scale);
            trans.translate(scale*image.getWidth(null), scale*image.getHeight(null));
            g2.drawImage(image, trans, this);
        }
    }
    
    public void updateButton()
    {
        image = null;
        if(piece != null)
        {
            try {
                image = ImageProcesses.getTintedImageIgnoreAlpha(ImageIO.read(new File("P:\\Qwirkle\\src\\assets\\" + piece.getShape().toString() + ".png")), 
                        piece.getColor());
            } catch (IOException ex) {
                Logger.getLogger(PieceButton.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
