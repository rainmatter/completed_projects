/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Muna
 */
public class ColorIcon {
    private final Shape myShape;
    private final Color myColor;
    private Image myImage;
    
    public ColorIcon(Color theColor) {
        myColor = theColor;
        myShape = new Rectangle2D.Double(0, 0, 15, 13);    
    }
    
    public Image getIcon() {
        Rectangle r = myShape.getBounds();
        myImage = new BufferedImage(r.width,r.height, BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D gr = (Graphics2D) myImage.getGraphics();
        gr.setPaint(myColor);
        gr.translate(-r.x, -r.y);
        gr.fill(myShape);
        gr.setPaint(Color.BLACK);
        gr.draw(myShape);
        return myImage;
    }
    
    
}
