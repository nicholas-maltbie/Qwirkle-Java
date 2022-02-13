/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;

/**
 *
 * @author Nicholas
 */
public class ImageProcesses 
{
       
    public static BufferedImage getTintedImageIgnoreAlpha(BufferedImage base, Color tint)
    {
        BufferedImage tintedImage = new BufferedImage(base.getWidth(), base.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        for(int x =0; x < base.getWidth(); x++)
            for(int y = 0; y < base.getHeight(); y++)
            {
                Color c = new Color(base.getRGB(x, y));
                if(c.getRed() <= 200 || c.getGreen() <= 200 ||  c.getBlue() <= 200)
                    tintedImage.setRGB(x, y, tint.getRGB());
                else
                    tintedImage.setRGB(x, y, new Color(c.getRed(),c.getBlue(),c.getGreen(),0).getRGB());
            }
        return tintedImage;
    }
    
    public static BufferedImage getTintedImage(BufferedImage base, Color tint)
    {
        BufferedImage tintedImage = new BufferedImage(base.getWidth(), base.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        for(int x =0; x < base.getWidth(); x++)
            for(int y = 0; y < base.getHeight(); y++)
                tintedImage.setRGB(x, y, tint.getRGB());
        return tintedImage;
    }
    
    public static Color blend(Color c0, Color c1)
    {
        double totalAlpha = c0.getAlpha() + c1.getAlpha();
        double weight0 = c0.getAlpha() / totalAlpha;
        double weight1 = c1.getAlpha() / totalAlpha;
        
        return blend(c0,c1, weight0, weight1);
    }
    
    
    public static Color blend(Color c0, Color c1, double weight0, double weight1) {

        double r = weight0 * c0.getRed() + weight1 * c1.getRed();
        double g = weight0 * c0.getGreen() + weight1 * c1.getGreen();
        double b = weight0 * c0.getBlue() + weight1 * c1.getBlue();
        double a = Math.max(c0.getAlpha(), c1.getAlpha());

        return new Color((int) r, (int) g, (int) b, (int) a);
    }
    
    public static BufferedImage outlineImage(BufferedImage image, Color outline)
    {
        BufferedImage copy = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) copy.getGraphics();
        AffineTransform trans = new AffineTransform(1, 0, 0, 1, 0, 0);
        g2.drawImage(getTintedImageIgnoreAlpha(image, Color.BLACK), trans, null);
        trans = new AffineTransform(.9, 0, 0, .9, image.getWidth()*.05, image.getHeight()*.05);
        g2.drawImage(image, trans, null);
        return copy;
    }
}
