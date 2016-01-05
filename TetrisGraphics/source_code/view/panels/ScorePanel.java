/*
 * TCSS 305 - Winter 2014
 * Final - Tetris
 * 
 * ScorePanel
 */

package view.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import model.Board;

/**
 * Tetris Game
 * 
 * Game where set shapes are stacked on a board.
 * Points are earned by filling and clearing lines.
 * 
 * This class is the Tetris score board.
 * It displays the current score and current level.
 * 
 * @author Kimberly Stewart
 * @version 12 March 2014
 */
public class ScorePanel extends JPanel  implements Observer {
    /**
     * Serial version ID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * The Panel width multiple.
     */
    private static final int PANEL_WIDTH = 4;
    /**
     * The Box width multiple.
     */
    private static final int BOX_WIDTH = 6;
    /**
     * The Box height multiple.
     */
    private static final int BOX_HEIGHT = 5;
    /**
     * The Box height multiple.
     */
    private static final int RELATIVE_OFFSET = 2;
    /**
     * The Box height multiple.
     */
    private static final int BOX_SPACING = 7;
    /**
     * The board of the current game.
     */
    private Board myBoard;
    /**
     * The 2D graphics used to draw.
     */
    private Graphics2D myGraphics2D;
    /**
     * The current size setting.
     */
    private int mySize;

    /**
     * Constructs a new score panel.
     * 
     * @param theBoard the current game board
     * @param theSize the size
     */
    public ScorePanel(final Board theBoard, final int theSize) {
        super();
        myBoard = theBoard;
        mySize = theSize;
    }
    
    /**
     * Sets the size of the panel.
     * 
     * @return the dimension
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(myBoard.getWidth() * mySize, 
                             myBoard.getHeight() * mySize + (PANEL_WIDTH * mySize));
    }
    
    /**
     * The paint component.
     * @param theGraphics the graphics
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        myGraphics2D = (Graphics2D) theGraphics;
        myGraphics2D.setColor(Color.BLUE);
        myGraphics2D.fillRect(0, 0, this.getWidth(), this.getHeight());
        myGraphics2D.setColor(Color.BLACK);
        myGraphics2D.fillRect(RELATIVE_OFFSET * mySize, 
                              RELATIVE_OFFSET * mySize, 
                              mySize * BOX_WIDTH, mySize * BOX_HEIGHT);
        myGraphics2D.fillRect(RELATIVE_OFFSET * mySize, 
                              (RELATIVE_OFFSET + BOX_SPACING) * mySize, 
                              mySize * BOX_WIDTH, mySize * BOX_HEIGHT);
        myGraphics2D.fillRect(RELATIVE_OFFSET * mySize, 
                              (RELATIVE_OFFSET + RELATIVE_OFFSET * BOX_SPACING) * mySize, 
                              mySize * BOX_WIDTH, mySize * BOX_HEIGHT);
        myGraphics2D.setColor(Color.LIGHT_GRAY);
        myGraphics2D.drawRect(RELATIVE_OFFSET * mySize, 
                              RELATIVE_OFFSET * mySize, 
                              mySize * BOX_WIDTH, 
                              mySize * BOX_HEIGHT);
        myGraphics2D.drawRect(RELATIVE_OFFSET * mySize, 
                              (RELATIVE_OFFSET + BOX_SPACING) * mySize, 
                              mySize * BOX_WIDTH, mySize * BOX_HEIGHT);
        myGraphics2D.drawRect(RELATIVE_OFFSET * mySize, 
                              (RELATIVE_OFFSET + RELATIVE_OFFSET * BOX_SPACING) * mySize, 
                              mySize * BOX_WIDTH, mySize * BOX_HEIGHT);
    }

    /**
     * Updates when called by the observed object.
     * 
     * @param theO the Observed
     * @param theArg the arg
     */
    @Override
    public void update(final Observable theO, final Object theArg) {
        repaint();
    }
    
    /**
     * Sets the size.
     * 
     * @param theSize the size setting
     */
    public void setBlockSize(final int theSize) {
        mySize = theSize;
        repaint();
    }
}
