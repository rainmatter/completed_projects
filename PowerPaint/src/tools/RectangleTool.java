/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tools;

import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Muna
 */
public class RectangleTool extends PowerTool {
    /**
     * The shape.
     */
    private Shape myShape;
    
    /**
     * The start x.
     */
    private int myStartX;
    
    /**
     * The start Y.
     */
    private int myStartY;
    
    /**
     * The width.
     */
    private int myWidth;
    
    /**
     * The height.
     */
    private int myHeight;
    
    public RectangleTool() {
        myShape = null;
    }
    
    @Override
    public void createShape(final int theStartX, final int theStartY, final int theEndX, 
                      final int theEndY) {
        if (theStartX < theEndX && theStartY < theEndY) {
            myStartX = theStartX;
            myStartY = theStartY;
            myWidth = theEndX - theStartX;
            myHeight = theEndY - theStartY;
        } else if (theStartX > theEndX && theStartY < theEndY) {
            myStartX = theEndX;
            myStartY = theStartY;
            myWidth = theStartX - theEndX;
            myHeight = theEndY - theStartY;
        } else if (theStartX < theEndX && theStartY > theEndY) {
            myStartX = theStartX;
            myStartY = theEndY;
            myWidth = theEndX - theStartX;
            myHeight = theStartY - theEndY;
        } else {
            myStartX = theEndX;
            myStartY = theEndY;
            myWidth = theStartX - theEndX;
            myHeight = theStartY - theEndY;
        }
        myShape = createRectangleShape();
    }
    
    private Rectangle2D createRectangleShape() {
        return new Rectangle2D.Double(myStartX, myStartY, myWidth, myHeight);
    }
    
    @Override
    public Shape getShape() {
        return myShape;
    }

}
