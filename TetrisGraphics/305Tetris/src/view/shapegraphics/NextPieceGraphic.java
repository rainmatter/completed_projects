/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view.shapegraphics;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Scanner;
import model.AbstractPiece;

/**
 *
 * @author Muna
 */
public class NextPieceGraphic {
    private ArrayList<BlockGraphic> myGraphic;
    private AbstractPiece myPiece;
    private Color myColor;
    private int mySize;
    private int myStartX;
    private int myStartY;
    
    public NextPieceGraphic(AbstractPiece thePiece, Color theColor, int theStartX, int theStartY, int theSize) {
        myPiece = thePiece;
        myColor = theColor;
        myStartX = theStartX;
        myStartY = theStartY;
        mySize = theSize;
        myGraphic = new ArrayList<>();
        createShape();
    }
    
    public void createShape() {
        String piece = myPiece.toString();
        Scanner s = new Scanner(piece);
        int y = 0;
        while (s.hasNextLine()) {
            String sLine = s.nextLine();
            for (int i = 0; i < sLine.length(); i++) {
                
                if (sLine.charAt(i) != ' ') {
                    
                    myGraphic.add(new BlockGraphic(myColor, myStartX + i * mySize, myStartY + y * mySize, mySize));
                } 
            }
            y++;
        }

    }
    
    public ArrayList<BlockGraphic> getPieceGraphic() {
        return myGraphic;
    }
    
}
