/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view.shapegraphics;

import java.awt.Color;
import java.util.ArrayList;
import model.AbstractPiece;
import model.ImmutablePiece;
import model.Point;

/**
 *
 * @author Muna
 */
public class PieceGraphic {
    private ArrayList<BlockGraphic> myGraphic;
    private AbstractPiece myPiece;
    private Color myColor;
    private int mySize;
    
    public PieceGraphic(AbstractPiece thePiece, Color theColor, int theSize) {
        myPiece = thePiece;
        myColor = theColor;
        mySize = theSize;
        myGraphic = new ArrayList<>();
        createShape();
    }
    
    public void createShape() {
        // we'll generate a String by going line by line and checking for blocks
        for (int y = 3; y >= 0; y--) { // output rows
            for (int x = 0; x < 4; x++) {  // output columns
                for (int i = 0; i < 4; i++) { // check the 4 blocks
                    final Point pos = myPiece.getPoints()[i];
                    if (pos.getX() == x && pos.getY() == y) {
                        Point p = myPiece.getAbsolutePosition(i);
                        BlockGraphic bg = new BlockGraphic(myPiece.getColor(), (p.getX() * mySize), (23 * mySize) - ((p.getY() + 1) * mySize), mySize);
                        myGraphic.add(bg);
                        //break;
                    }
                }
            }
        }

    }
    
    public ArrayList<BlockGraphic> getPieceGraphic() {
        return myGraphic;
    }
    
    public Color getColor() {
        return myColor;
    }
}
    

