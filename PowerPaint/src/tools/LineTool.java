/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tools;

import java.awt.Shape;
import java.awt.geom.Line2D;

/**
 *
 * @author Muna
 */
public class LineTool extends PowerTool {
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
     * The end X.
     */
    private int myEndX;
    
    /**
     * The end y.
     */
    private int myEndY;
    
    public LineTool() {
        myShape = null;
    }
    
    @Override
    public void createShape(final int theStartX, final int theStartY, final int theEndX, 
                      final int theEndY) {
        myStartX = theStartX;
        myStartY = theStartY;
        myEndX = theEndX;
        myEndY = theEndY;
        myShape = createLine();
    }
    
    private Line2D createLine() {
        return new Line2D.Double(myStartX, myStartY, myEndX, myEndY);
    }
    
    @Override
    public Shape getShape() {
        return myShape;
    }

}
