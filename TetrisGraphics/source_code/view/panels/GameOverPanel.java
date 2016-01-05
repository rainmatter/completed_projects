/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Muna
 */
public class GameOverPanel extends JPanel {
    private int mySize;
    private Graphics2D myGraphics2D;
    
    public GameOverPanel(int theSize) {
        mySize = theSize;
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(25 * mySize, 15 * mySize);
    }
    
    @Override
    public void paintComponent(final Graphics theGraphics) {
        myGraphics2D = (Graphics2D) theGraphics;
        myGraphics2D.setColor(Color.BLUE);
        myGraphics2D.fillRect(0, 0, this.getWidth(), this.getHeight());
        myGraphics2D.setColor(Color.BLACK);
        myGraphics2D.fillRect(2 * mySize, 2 * mySize, mySize * 6, mySize * 5);
        myGraphics2D.fillRect(2 * mySize, 9 * mySize, mySize * 6, mySize * 5);
        myGraphics2D.fillRect(2 * mySize, 16 * mySize, mySize * 6, mySize * 5);
        myGraphics2D.setColor(Color.LIGHT_GRAY);
        myGraphics2D.drawRect(2 * mySize, 2 * mySize, mySize * 6, mySize * 5);
        myGraphics2D.drawRect(2 * mySize, 9 * mySize, mySize * 6, mySize * 5);
        myGraphics2D.drawRect(2 * mySize, 16 * mySize, mySize * 6, mySize * 5);
  
    }
    
    public void over() {
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.add(this);
        frame.pack();
    }
}
