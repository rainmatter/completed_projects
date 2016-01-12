/*
 * TCSS 305 - Winter 2014
 * Final - Tetris
 * 
 * TetrisMain
 */

package view;

import java.awt.EventQueue;

/**
 * Tetris Game
 * 
 * Game where set shapes are stacked on a board.
 * Points are earned by filling and clearing lines.
 * 
 * @author Kimberly Stewart
 * @version 12 March 2014
 */
public final class TetrisMain {
    /**
     * Private constructor, to prevent instantiation of this class.
     */
    private TetrisMain() {
        throw new IllegalStateException();
    }
    
    /**
     * Main method of paint program.
     * 
     * @param args0 String array for main.
     */
    public static void main(final String[] args0) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                final TetrisFrame frame = new TetrisFrame();
                frame.start();    
            }
        });
    }
    
}