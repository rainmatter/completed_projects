/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view.panels;

import java.awt.BasicStroke;
import view.shapegraphics.BlockGraphic;
import view.shapegraphics.PieceGraphic;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.util.Observable;
import java.util.Observer;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import model.AbstractPiece;
import model.Board;
import model.ImmutablePiece;
import view.panels.GameOverPanel;

/**
 *
 * @author Muna
 */
public class TetrisPanel extends JPanel implements Observer {
    private Board myBoard;
    private Graphics2D myGraphics2D;
    private final Timer myMoveTimer;
    private KeyAdapter myKeyAdapter;
    private int mySize;
    
    public TetrisPanel(Board theBoard, int theSize) {
        super();
        myBoard = theBoard;
        mySize = theSize;
        myMoveTimer = new Timer(500, new MoveListener());
        myMoveTimer.setInitialDelay(2000);
        this.addKeyListener(myKeyAdapter);
        this.setFocusable(true);
        PressAction press = new PressAction();
        JButton button = new JButton();
        button.addActionListener(press);
        button.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).
                put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P,0), "P_pressed");
        this.add(button);
        button.setVisible(false);
        
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
        myGraphics2D.fillRect(0, (2 * mySize), myBoard.getWidth() * mySize, (myBoard.getHeight() + 1) * mySize);
        for (int y = 0; y <= myBoard.getHeight(); y++) {
            Color[] row = myBoard.getRowAt(y);
            for (int x = 0; x < row.length; x++) {
                if (row[x] != null) {
                    Color c = row[x];
                    BlockGraphic b = new BlockGraphic(c, 0 + x * mySize, (myBoard.getHeight() + 3) * mySize - (y + 1) * mySize, mySize);
                    myGraphics2D.setColor(b.getColor());
                    myGraphics2D.fill(b.getShape());
                    myGraphics2D.setColor(Color.BLACK);
                    myGraphics2D.draw(b.getShape());
                }
                
            }
        }
        myGraphics2D.setColor(Color.LIGHT_GRAY);
        myGraphics2D.setStroke(new BasicStroke(2));
        myGraphics2D.drawRect(0, (2 * mySize), myBoard.getWidth() * mySize, (myBoard.getHeight() + 1) * mySize);
        myGraphics2D.setStroke(new BasicStroke(1));
        AbstractPiece myPiece = (AbstractPiece) myBoard.getCurrentPiece();
        PieceGraphic pg = new PieceGraphic(myPiece, myPiece.getColor(), mySize);
        ArrayList<BlockGraphic> bg = pg.getPieceGraphic();
        for (int i = 0; i < bg.size(); i++) {
            Shape s = bg.get(i).getShape();
            myGraphics2D.setColor(bg.get(i).getColor());
            myGraphics2D.fill(s);
            myGraphics2D.setColor(Color.BLACK);
            myGraphics2D.draw(s);
        }
        
    }
    

    @Override
    public void update(Observable o, Object arg) {
        //System.out.println(myBoard.toString());
        repaint();
        
    }
    
    /** Starts the timer. */
    public void start() {
        myMoveTimer.start();
    }
    
    public void pause() {
        myMoveTimer.stop();
    }
    
    
    public void setBlockSize(int theSize) {
        mySize = theSize;
        repaint();
    }
    
    
    
    /**
     * A class that listens for timer events and moves the shape, checking for
     * the window boundaries and changing direction as appropriate.
     */
    private class MoveListener implements ActionListener {
        /**
         * Moves the shape appropriately.
         * 
         * @param theEvent The event triggering the action.
         */
        public void actionPerformed(final ActionEvent theEvent) {
            if (!myBoard.isFull()) {
                myBoard.moveDown();
            } else {
               myMoveTimer.stop();
               final JFrame frame = new JFrame();
               frame.setLocation(100, 100);
               JOptionPane.showMessageDialog(frame, "GameOver!");
               
               /*
               GameOverPanel gameOver = new GameOverPanel(mySize);
               gameOver.over();
               */
            }
        }

    }
    
    
    
    public class KeyEventDemo extends KeyAdapter {

        /**
         * Handle the key-pressed event from the text field.
         */
        public void keyPressed(KeyEvent e) {
            System.out.println("A key was pressed");
        }
    }
    
    
    /**
     * Inner class
     * 
     * @author stewak5
     *
     */
    public class PressAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent theEvent) {
            System.out.println("A key was pressed");
        }
        
    }

}
