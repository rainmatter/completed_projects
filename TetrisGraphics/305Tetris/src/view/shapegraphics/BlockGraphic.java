/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package view.shapegraphics;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import model.Point;

/**
 *
 * @author Muna
 */
public class BlockGraphic {
    /**
     * The shape.
     */
    private Rectangle2D myBlock;
    
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
    private int mySize;
    
    /**
     * The height.
     */
    private int myHeight;
    
    private Color myColor;
    
    public BlockGraphic(Color theColor, int theStartX, int theStartY, int theSize) {
        myColor = theColor;
        myStartX = theStartX;
        myStartY = theStartY;
        mySize = theSize;
        myBlock = new Rectangle2D.Double(myStartX, myStartY, mySize, mySize);
        
    }

    
    public Shape getShape() {
        return myBlock;
    }
    
    public Color getColor() {
        return myColor;
    }
    
    public String toString() {
        return myBlock.toString();
    }

}

