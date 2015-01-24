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
public class PowerShapeDummy {
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
     * the type of shape being drawn.
     */
    private final String myCurrentTool;
    
    /**
     * The start x.
     */
    private final int myStartX;
    
    /**
     * The start Y.
     */
    private final int myStartY;
    
    /**
     * The end X.
     */
    private final int myEndX;
    
    /**
     * The end y.
     */
    private final int myEndY;
    
    /**
     * Constructs a new shape.
     * 
     * @param theStartX start X
     * @param theStartY start Y
     * @param theEndX end x
     * @param theEndY end y
     * @param theTool current tool
     * @param theColor current color
     * @param theThickness current thickness
     */
    public PowerShapeDummy(final int theStartX, final int theStartY, final int theEndX, 
                      final int theEndY, final String theTool, final Color theColor, 
                      final int theThickness) {
        myStartX = theStartX;
        myStartY = theStartY;
        myEndX = theEndX;
        myEndY = theEndY;
        myCurrentTool = theTool;
        myColor = theColor;
        myThickness = theThickness;
        
        myShape = createShape();
    }
    
    /**
     * Creates the shape.
     * 
     * @return the shape
     */
    private Shape createShape() {
        switch (myCurrentTool) {
            case "Line":
                return createLine();
            case "Pencil":
                return createLine();
            case "Rectangle":
                return createRectangle();
            default:
                return createEllipse();
        }
    }
    
    /**
     * Creates a line.
     * 
     * @return Line2D
     */
    private Line2D createLine() {
        return new Line2D.Double(myStartX, myStartY, myEndX, myEndY);
    }
    
    /**
     * Create a rectangle.
     * 
     * @return Rectangle2D
     */
    private Shape createRectangle() {
        if (myStartX < myEndX && myStartY < myEndY) {
            return new Rectangle2D.Double(myStartX, myStartY, 
                                          myEndX - myStartX, myEndY - myStartY);
        } else if (myStartX > myEndX && myStartY < myEndY) {
            return new Rectangle2D.Double(myEndX, myStartY, 
                                          myStartX - myEndX, myEndY - myStartY);
        } else if (myStartX < myEndX && myStartY > myEndY) {
            return new Rectangle2D.Double(myStartX, myEndY, 
                                          myEndX - myStartX, myStartY - myEndY);
        } else {
            return new Rectangle2D.Double(myEndX, myEndY, 
                                          myStartX - myEndX, myStartY - myEndY);
        }
    }
    
    /**
     * Creates an ellipse.
     * 
     * @return Ellipse2D
     */
    private Ellipse2D createEllipse() {
        if (myStartX < myEndX && myStartY < myEndY) {
            return new Ellipse2D.Double(myStartX, myStartY, 
                                        myEndX - myStartX, myEndY - myStartY);
        } else if (myStartX > myEndX && myStartY < myEndY) {
            return new Ellipse2D.Double(myEndX, myStartY, 
                                        myStartX - myEndX, myEndY - myStartY);
        } else if (myStartX < myEndX && myStartY > myEndY) {
            return new Ellipse2D.Double(myStartX, myEndY, 
                                        myEndX - myStartX, myStartY - myEndY);
        } else {
            return new Ellipse2D.Double(myEndX, myEndY, myStartX - myEndX, myStartY - myEndY);
        }
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
        return "Shape of type: " + myCurrentTool + ", "
                + "of color: " + myColor + ", "
                + "of thickness: " + myThickness + ", "
                + "with rectangle bounds: " + myShape.getBounds2D();
    }
    
}
