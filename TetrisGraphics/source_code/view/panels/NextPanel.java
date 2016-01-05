/*
 * TCSS 305 - Winter 2014
 * Final - Tetris
 * 
 * NextPanel
 */

package view.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import model.AbstractPiece;
import model.Board;
import view.shapegraphics.BlockGraphic;
import view.shapegraphics.NextPieceGraphic;

/**
 * Tetris Game
 * 
 * Game where set shapes are stacked on a board.
 * Points are earned by filling and clearing lines.
 * 
 * This class is the Tetris game board.
 * It displays the board, the frozen blocks and the current piece.
 * 
 * @author Kimberly Stewart
 * @version 12 March 2014
 */
public class NextPanel extends JPanel  implements Observer {
    /**
     * Serial version ID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * The current game board.
     */
    private Board myBoard;
    private Graphics2D myGraphics2D;
    private AbstractPiece myNextPiece;
    private int mySize;
   
    
    public NextPanel(Board theBoard, int theSize) {
        super();
        myBoard = theBoard;
        mySize = theSize;
        //myMoveTimer = new Timer(500, new MoveListener());
        //myMoveTimer.setInitialDelay(500);
        
        
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(myBoard.getWidth() * mySize, myBoard.getHeight() * mySize + (4 * mySize));
    }
    
    @Override
    public void paintComponent(final Graphics theGraphics) {
        myGraphics2D = (Graphics2D) theGraphics;
        myGraphics2D.setColor(Color.BLUE);
        myGraphics2D.fillRect(0, 0, this.getWidth(), this.getHeight());
        myGraphics2D.setColor(Color.BLACK);
        myGraphics2D.fillRect(2 * mySize, 2 * mySize, mySize * 6, mySize * 5);
        myGraphics2D.fillRect(1 * mySize, 9 * mySize, mySize * 8, mySize * 12);
        myGraphics2D.setColor(Color.LIGHT_GRAY);
        myGraphics2D.drawRect(2 * mySize, 2 * mySize, mySize * 6, mySize * 5);
        myGraphics2D.drawRect(1 * mySize, 9 * mySize, mySize * 8, mySize * 12);
        
        myGraphics2D.setColor(Color.LIGHT_GRAY);
        Font f = new Font("Serif", Font.ITALIC, mySize);
        myGraphics2D.setFont(f);
        myGraphics2D.drawString("Next Piece", 3 * mySize, 3 * mySize + 5);
        
        AbstractPiece myNextPiece = (AbstractPiece) myBoard.getNextPiece();
        
        
        int startX = 3 * mySize;
        int startY = 3 * mySize;
        NextPieceGraphic pg = new NextPieceGraphic(myNextPiece, myNextPiece.getColor(), startX, startY, mySize);
        ArrayList<BlockGraphic> myGraphic = pg.getPieceGraphic();
        for (int i = 0; i < myGraphic.size(); i++) {
            if (!myGraphic.get(i).equals(null)) {
                myGraphics2D.setColor(myGraphic.get(i).getColor());
                myGraphics2D.fill(myGraphic.get(i).getShape());
                myGraphics2D.setColor(Color.BLACK);
                myGraphics2D.draw(myGraphic.get(i).getShape());
            }
        }
        
        myGraphics2D.setColor(Color.LIGHT_GRAY);
        myGraphics2D.drawString("Key Controls", 2 * mySize + 6, 10 * mySize + 5);
        
        Font f2 = new Font("Sans-serif", Font.PLAIN, (int)(mySize / 2));
        myGraphics2D.setFont(f2);
        myGraphics2D.drawString("Move right", (int) (1.5 * mySize), 12 * mySize + 5);
        myGraphics2D.drawString("Right arrow", (int) (6 * mySize), 12 * mySize + 5);
        myGraphics2D.drawString("Move left", (int) (1.5 * mySize), 13 * mySize + 5);
        myGraphics2D.drawString("Left arrow", (int) (6 * mySize), 13 * mySize + 5);
        myGraphics2D.drawString("Move down", (int) (1.5 * mySize), 14 * mySize + 5);
        myGraphics2D.drawString("Down arrow", (int) (6 * mySize), 14 * mySize + 5);
        myGraphics2D.drawString("Rotate CW", (int) (1.5 * mySize), 15 * mySize + 5);
        myGraphics2D.drawString("X", (int) (6 * mySize), 15 * mySize + 5);
        myGraphics2D.drawString("Rotate CCW", (int) (1.5 * mySize), 16 * mySize + 5);
        myGraphics2D.drawString("C", (int) (6 * mySize), 16 * mySize + 5);
        myGraphics2D.drawString("Drop", (int) (1.5 * mySize), 17 * mySize + 5);
        myGraphics2D.drawString("D", (int) (6 * mySize), 17 * mySize + 5);
        
    }

    @Override
    public void update(Observable o, Object arg) {     
            myNextPiece = (AbstractPiece)myBoard.getNextPiece();
            //System.out.println(myNextPiece.toString());
            repaint();
    }
    
    public void setBlockSize(int theSize) {
        mySize = theSize;
        repaint();
    }
}
