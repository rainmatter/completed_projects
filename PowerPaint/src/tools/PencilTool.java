/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tools;

import java.awt.Shape;
import java.awt.geom.GeneralPath;

/**
 *
 * @author Muna
 */
public class PencilTool extends PowerTool {
    /**
     * The shape.
     */
    private GeneralPath myShape;
    
    /**
     * The start x.
     */
    private int myStartX;
    
    /**
     * The start Y.
     */
    private int myStartY;
    
    /**
     * The end X.
     */
    private int myEndX;
    
    /**
     * The end y.
     */
    private int myEndY;
    
    public PencilTool() {
        myShape = new GeneralPath();
        myStartX = 0;
        myStartY = 0;
    }
    
    @Override
    public void createShape(final int theStartX, final int theStartY, final int theEndX, 
                      final int theEndY) {
        if (myStartX == 0 && myEndX == 0) {
            myStartX = theStartX;
            myStartY = theStartY;
            myShape.moveTo(myStartX, myStartY);
        } 
        myEndX = theEndX;
        myEndY = theEndY;
        myShape.lineTo(myEndX, myEndY);
    }
    
    @Override
    public Shape getShape() {
        return myShape;
    }

}
