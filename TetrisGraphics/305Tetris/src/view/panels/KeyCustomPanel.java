/*
 * TCSS 305 - Winter 2014
 * Final - Tetris
 * 
 * ScorePanel
 */

package view.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;

import javax.swing.JPanel;

import model.Board;
import view.KeyBindings;

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
public class KeyCustomPanel extends JPanel {
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
    private ArrayList<KeyBindings> myKeys;

    /**
     * Constructs a new score panel.
     * 
     * @param theBoard the current game board
     * @param theSize the size
     */
    public KeyCustomPanel(ArrayList<KeyBindings> theKeys, final int theSize) {
        super();
        myKeys = theKeys;
        mySize = theSize;
    }
    
    /**
     * Sets the size of the panel.
     * 
     * @return the dimension
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 300);
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
    
    /**
     * Sets up & starts the current frame.
     */
    public String start() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame();
                frame.setTitle("Customize the Keys...");
                frame.setVisible(true);
                /*
                final Image img = new ImageIcon(getClass().
                                                getResource(ICON_IMAGE)).
                                                getImage();
                myFrame.setIconImage(img);
                */
                //frame.setResizable(false);
                //myFrame.run();
                KeyCustomPanel kc = new KeyCustomPanel(myKeys, mySize);
                frame.add(kc);
                frame.pack();
            }
        });
        return "Finished!";
    }
}
