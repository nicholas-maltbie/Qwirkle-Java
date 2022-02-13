/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import qwirkle.*;
import java.awt.Color;

/**
 * Class that represents a Piece in the game Quirkle. It has the ability to 
 * hold a color and shape. It is an immutable class.
 * 
 * @author Maltbie_N
 */
public class Piece 
{
    /**
     * The length of the color name if it is one of the Color(s) in the Color[]
     * colors.
     */
    public static final int COLOR_NAME_LENGTH = 3;
    
    /**
     * Possible shapes for the Piece stored in an enum. They must be one of these types.
     */
    public static enum Shape {SQUARE,TRIANGLE,CIRCLE,PLUS,STAR,DIAMOND};
    
    /**
     * Possible shapes saved in an array.
     */
    public static final Shape[] shapes = Shape.values();
    
    /**
     * Strings used to retrieve the names of colors if they are sampled
     * from the Color[] colors in the toString() method 
     */
    public static final String[] colorNames = {"RED", "GREEN", "BLUE",
        "MAGENTA", "YELLOW", "CYAN"};
    
    /**
     * Optional, default colors 
     */
    public static final Color[] colors = {Color.RED, Color.GREEN,
        Color.BLUE, Color.MAGENTA, Color.YELLOW, Color.CYAN};
    
    /**Color of the piece**/
    private Color color;
    /**Shape of the piece**/
    private Shape shape;
    
    /**
     * Creates a new Piece with a random color and shape
     */
    public Piece()
    {
        this(colors[(int)(Math.random()*colors.length)] ,shapes[(int)(Math.random()*shapes.length)]);
    }
    
    /**
     * Creates a Piece with specified Shape and Color
     * @param color of the piece, should be one of the Color[] colors, but
     * can be set to any value.
     * @param shape of the piece. It's an enum defined in this class.
     */
    public Piece(Color color, Shape shape)
    {
        this.color = color;
        this.shape = shape;
    }
    
    /**
     * Gets the Shape of the piece
     * @return Shape of the piece
     */
    public Shape getShape()
    {
        return shape;
    }
    
    /**
     * Gets the color of the piece
     * @return Color of piece
     */
    public Color getColor()
    {
        return color;
    }
    
    /**
     * Returns a string containing the shape as a Unicode character and 
     * the color of the piece. If the piece uses a color from Color[] colors,
     * it will return a word for the color name, if not: it will return the
     * rgb values from Color.toString().
     * 
     * @return string that represents the piece
     */
    @Override
    public String toString()
    {
        //getting the shape
        String toString = new String();
        switch(shape)
        {
            case SQUARE:
                toString += "\u25A0";
                break;
            case TRIANGLE:
                toString += "\u25B2";
                break;
            case CIRCLE:
                toString += "\u25CF";
                break;
            case STAR:
                toString += "\u2605";
                break;
            case DIAMOND:
                toString += "\u25C6";
                break;
            case PLUS:
                toString += "+";
                break;
            default:
                toString += "n"; 
        }
        
        //getting the color
        StringBuffer colorName = null;
        for(int index = 0; index < colors.length; index++)
            if(this.color.equals(colors[index]))
            {
                colorName = new StringBuffer(colorNames[index]);
                colorName.setLength(COLOR_NAME_LENGTH);
            }
        if(colorName == null)
            colorName = new StringBuffer(color.toString());
        
        toString += colorName;
        return toString;
    }
    
}
