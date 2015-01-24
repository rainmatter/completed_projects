/*
 * TCSS 305 - Winter 2014
 * Assignment 5 - PowerPaint
 * 
 * PowerShape
 */

package model;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

/**
 * Digital Paint program.
 * <p>
 * Draws shapes of different type.
 * Stores a shape with a color and thickness setting.
 * 
 * @author Kimberly Stewart
 * @version 5 March 2014
 */
public class PowerShape {
    /**
     * The shape.
     */
    private final Shape myShape;
    
    /**
     * The color.
     */
    private final Color myColor;
    
    /**
     * The thickness.
     */
    private final int myThickness;
    

    
    
    /**
     * Constructs a new shape.
     * 
     * @param theTool current tool
     * @param theColor current color
     * @param theThickness current thickness
     */
    public PowerShape(final Shape theShape, final Color theColor, 
                      final int theThickness) {

        myColor = theColor;
        myThickness = theThickness;
        myShape = theShape;
    }
    
    /**
     * Gets the shape.
     * 
     * @return shape
     */
    public Shape getShape() {
        return myShape;
    }
    
    /**
     * gets the color.
     * 
     * @return the color
     */
    public Color getColor() {
        return myColor;
    }
    
    /**
     * gets the thickness.
     * 
     * @return the thickness
     */
    public int getThickness() {
        return myThickness;
    }
    
    /**
     * To string method.
     * 
     * @return a string representing this shape.
     */
    @Override
    public String toString() {
        return "Shape of type: Line, "
                + "of color: " + myColor + ", "
                + "of thickness: " + myThickness + ", "
                + "with rectangle bounds: " + myShape.getBounds2D();
    }
    
}
