/*
 * TCSS 305 - Winter 2014
 * Assignment 5 - PowerPaint
 * 
 * PowerPaintMain
 */

package gui;

import java.awt.EventQueue;

/**
 * Digital Paint program.
 * <p)
 * Main class that runs the program.
 * <p>
 * This program opens a frame with tools that allow a user to draw on a panel.
 * 
 * @author Kimberly Stewart
 * @version 5 March 2014
 */
public final class PowerPaintMain {
    /**
     * Private constructor, to prevent instantiation of this class.
     */
    private PowerPaintMain() {
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
                final PowerPaintFrame frame = new PowerPaintFrame();
                frame.start();    
            }
        });
    }
    
}