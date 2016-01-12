/*
 * TCSS 305 - Winter 2014
 * Final - Tetris
 * 
 * TetrisFrame
 */

package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import model.Board;
import view.panels.KeyCustomPanel;
import view.panels.NextPanel;
import view.panels.ScorePanel;
import view.panels.TetrisPanel;



/**
 * Tetris Game
 * 
 * Game where set shapes are stacked on a board.
 * Points are earned by filling and clearing lines.
 * 
 * This class assembles the panels.
 * It also sets up the key bindings and the menu options.
 * 
 * @author Kimberly Stewart
 * @version 12 March 2014
 */
public class TetrisFrame extends JFrame {
    /**
     * Serial version ID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Default board width in number of blocks.
     */
    private static final int DEFAULT_BOARD_WIDTH = 10;
    /**
     * Default board Height in number of blocks.
     */
    private static final int DEFAULT_BOARD_HEIGHT = 20;
    /**
     * Default block size.
     */
    private static final int DEFAULT_SIZE = 25;
    /**
     * Frame icon image path.
     */
    private static final String ICON_IMAGE = "/view/icons/block.gif";
    /**
     * Maint panel of the game board.
     */
    private JPanel myMainPanel;
    /**
     * The Tetris board used for this game.
     */
    private Board myBoard;
    /**
     * Current board width in blocks.
     */
    private int myBoardWidth;
    /**
     * Current board height in blocks.
     */
    private int myBoardHeight;
    /**
     * The Panel that holds the Tetris board.
     */
    private TetrisPanel myBoardPanel;
    /**
     * The panel that displays the next pieces and key settings.
     */
    private NextPanel myNextPanel;
    /**
     * The panel that displays the score & level.
     */
    private ScorePanel myScorePanel;
    /**
     * The current size.
     */
    private int mySize;
    /**
     * Current instance of this frame.
     */
    private TetrisFrame myFrame;
    
    private ArrayList<KeyBindings> myKeys;
    
    /**
     * Constructs a new Tetris frame.
     */
    public TetrisFrame() {
        myBoardWidth = DEFAULT_BOARD_WIDTH;
        myBoardHeight = DEFAULT_BOARD_HEIGHT;
        mySize = DEFAULT_SIZE;
        setUpBoard();
        myMainPanel = new JPanel();
        setUpLayout();
        add(myMainPanel);
        addKeyBindings();
        pack();
    }
    
    /**
     * Creates a new Tetris board.
     */
    private void setUpBoard() {
        myBoard = new Board(myBoardWidth, myBoardHeight);
        setUpPanels();
        setObservers();
    }
    
    /**
     * Sets up the three main panels.
     */
    public void setUpPanels() {
        myBoardPanel = new TetrisPanel(myBoard, mySize);
        myNextPanel = new NextPanel(myBoard, mySize);
        myScorePanel = new ScorePanel(myBoard, mySize);
        
    }
    
    /**
     * Adds the panels as observers.
     */
    public void setObservers() {
        myBoard.addObserver(myBoardPanel);
        myBoard.addObserver(myNextPanel);
        myBoard.addObserver(myScorePanel);
    }
    
    /**
     * Sets up the layout of the main panel.
     */
    private void setUpLayout() {
        myMainPanel.setLayout(new BorderLayout());
        myMainPanel.add(myBoardPanel, BorderLayout.CENTER);
        myMainPanel.add(myNextPanel, BorderLayout.EAST);
        myMainPanel.add(myScorePanel, BorderLayout.WEST);
    }
    
    /**
     * Adds the key bindings to the main panel.
     * Sets the keys used to control the game.
     */
    private void addKeyBindings() {
        myKeys = new ArrayList<>();
         KeyCustomAction kc = new KeyCustomAction(myKeys);
        myKeys.add(new KeyBindings(kc, "Test", KeyEvent.VK_Y));
       
        System.out.println(myKeys.get(0).getName());
        System.out.println(myKeys.get(0).getKey());
        System.out.println(myKeys.get(0).getText());

        
        
        myMainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).
                put(KeyStroke.getKeyStroke(myKeys.get(0).getKey(), 0), myKeys.get(0).getName());
        myMainPanel.getActionMap().put(myKeys.get(0).getName(), myKeys.get(0).getAction());
        
        myMainPanel.getActionMap().remove(myKeys.get(0).getKey());
        myKeys.get(0).setKey(KeyEvent.VK_W);
        
        myMainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).
                put(KeyStroke.getKeyStroke(myKeys.get(0).getKey(), 0), myKeys.get(0).getName());
        myMainPanel.getActionMap().put(myKeys.get(0).getName(), myKeys.get(0).getAction());
        
        
        final LeftAction leftAction = new LeftAction();
        myMainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).
                put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "Left_pressed");
        myMainPanel.getActionMap().put("Left_pressed", leftAction);
        
        final RotateClockAction clockAction = new RotateClockAction();
        myMainPanel.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).
                put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, 0), "X_pressed");
        myMainPanel.getActionMap().put("X_pressed", clockAction);
        
        final RightAction rightAction = new RightAction();
        myMainPanel.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).
                put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_RIGHT, 0), "Right_pressed");
        myMainPanel.getActionMap().put("Right_pressed", rightAction);
        
        final DownAction downAction = new DownAction();
        myMainPanel.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).
                put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DOWN, 0), "Down_pressed");
        myMainPanel.getActionMap().put("Down_pressed", downAction);
        
        final RotateCounterAction counterAction = new RotateCounterAction();
        myMainPanel.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).
                put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, 0), "C_pressed");
        myMainPanel.getActionMap().put("C_pressed", counterAction);
        
        final DropAction dropAction = new DropAction();
        myMainPanel.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).
                put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, 0), "D_pressed");
        myMainPanel.getActionMap().put("D_pressed", dropAction);
    }
    
    /**
     * Starts the game in the Tetris board panel.
     */
    public void run() {
        myBoardPanel.start();
    }
    
    /**
     * Sets up & starts the current frame.
     */
    public void start() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                myFrame = new TetrisFrame();
                myFrame.setTitle("TCSS 305 Tetris");
                myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                myFrame.setVisible(true);
                final Image img = new ImageIcon(getClass().
                                                getResource(ICON_IMAGE)).
                                                getImage();
                myFrame.setIconImage(img);
                myFrame.setJMenuBar(myFrame.createMenuBar());
                myFrame.setResizable(false);
                myFrame.run();
            }
        });
    }
    
    /**
     * Creates the menu bar.
     * 
     * @return the menu bar.
     */
    public JMenuBar createMenuBar() {
        final JMenuBar menuBar = new JMenuBar();
        //menuBar.add(addFileMenu());
        menuBar.add(addOptionsMenu());
        //menuBar.add(addHelpMenu());
        return menuBar;
    }
    
    /**
     * Creates the options section of the menu.
     * 
     * @return menu for options
     */
    private JMenu addOptionsMenu() {
        final JMenu optionsMenu = new JMenu("Options");
        optionsMenu.setMnemonic('O');
        //Add Grid item menu
        //Size submenu
        final JMenu sizeMenu = new JMenu("Select Size");
        sizeMenu.setMnemonic('S');
        optionsMenu.add(sizeMenu);
        //Thickness radios
        final ButtonGroup sizeRadios = new ButtonGroup();
        final JRadioButtonMenuItem sizeMini = new JRadioButtonMenuItem("Mini");
        final JRadioButtonMenuItem sizeSmall = new JRadioButtonMenuItem("Small");
        final JRadioButtonMenuItem sizeMedium = new JRadioButtonMenuItem("Medium");
        final JRadioButtonMenuItem sizeLarge = new JRadioButtonMenuItem("Large");
        sizeRadios.add(sizeMini);
        sizeRadios.add(sizeSmall);
        sizeRadios.add(sizeMedium);
        sizeRadios.add(sizeLarge);
        sizeMenu.add(sizeMini);
        sizeMenu.add(sizeSmall);
        sizeMenu.add(sizeMedium);
        sizeMenu.add(sizeLarge);
        final SizeAction mini = new SizeAction(15);
        final SizeAction small = new SizeAction(25);
        final SizeAction medium = new SizeAction(30);
        final SizeAction large = new SizeAction(35);
        sizeMini.addActionListener(mini);
        sizeSmall.addActionListener(small);
        sizeMedium.addActionListener(medium);
        sizeLarge.addActionListener(large);
        final JMenuItem keyCustomMenu = new JMenuItem("Customize Keys...");
        sizeMenu.setMnemonic('K');
        optionsMenu.add(keyCustomMenu);
        KeyCustomAction keyCustom = new KeyCustomAction(myKeys);
        keyCustomMenu.addActionListener(keyCustom);
        return optionsMenu;
    }

    /**
     * Inner class Right action.
     * Moves the piece to the right.
     * 
     * @author Kimberly Stewart
     *
     */
    public class RightAction extends AbstractAction {

        /**
         * Serial version ID.
         */
        private static final long serialVersionUID = 1L;

        /**
         * The action performed.
         * 
         * @param theEvent the Event
         */
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myBoard.moveRight();
        }
        
    }
    
    /**
     * Inner class Left action.
     * Moves the piece to the left.
     * 
     * @author Kimberly Stewart
     *
     */
    public class LeftAction extends AbstractAction {

        /**
         * Serial version ID.
         */
        private static final long serialVersionUID = 1L;

        /**
         * The action performed.
         * 
         * @param theEvent the Event
         */
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myBoard.moveLeft();
        }
        
    }
    
    /**
     * Inner class Clockwise rotation action.
     * Rotates the piece clockwise.
     * 
     * @author Kimberly Stewart
     *
     */
    public class RotateClockAction extends AbstractAction {

        /**
         * Serial version ID.
         */
        private static final long serialVersionUID = 1L;

        /**
         * The action performed.
         * 
         * @param theEvent the Event
         */
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myBoard.rotateClockwise();
        }
        
    }
    
    /**
     * Inner class Counter Clockwise action.
     * Rotates the piece counter clockwise.
     * 
     * @author Kimberly Stewart
     *
     */
    public class RotateCounterAction extends AbstractAction {

        /**
         * Serial Version ID.
         */
        private static final long serialVersionUID = 1L;

        /**
         * The action performed.
         * 
         * @param theEvent the Event
         */
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myBoard.rotateCounterclockwise();
        }
        
    }
    
    
    /**
     * Inner class Down action.
     * Moves the piece down & progresses the game by 1 step.
     * 
     * @author Kimberly Stewart
     *
     */
    public class DownAction extends AbstractAction {

        /**
         * Serial version ID.
         */
        private static final long serialVersionUID = 1L;

        /**
         * The action performed.
         * 
         * @param theEvent the Event
         */
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            if (!myBoard.isFull()) {
                myBoard.moveDown();
            }
        }
        
    }
    
    /**
     * Inner class Drop action.
     * Drops the current piece to the bottom.
     * 
     * @author Kimberly Stewart
     *
     */
    public class DropAction extends AbstractAction {

        /**
         * Serial version ID.
         */
        private static final long serialVersionUID = 1L;

        /**
         * The action performed.
         * 
         * @param theEvent the Event
         */
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            if (!myBoard.isFull()) {
                myBoard.drop();
            }
        }
        
    }
    
    /**
     * Inner class Size action.
     * Resizes the board based on a radio value.
     * Size determines the block size of the board.
     * 
     * @author Kimberly Stewart
     *
     */
    public class SizeAction extends AbstractAction {
        /**
         * Serial version ID.
         */
        private static final long serialVersionUID = 1L;
        
        /**
         * Integer representing the tool thickness.
         */
        private final int mySize;
        
        /**
         * Constructs the size action.
         * 
         * @param theSize the size to be set
         */
        public SizeAction(final int theSize) {
            mySize = theSize;
            
        }
        
        /**
         * The action performed.
         * 
         * @param theEvent the Event
         */
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myBoardPanel.setBlockSize(mySize);
            myNextPanel.setBlockSize(mySize);
            myScorePanel.setBlockSize(mySize);
            pack();
            
        }
        
    }
    
    /**
     * Inner class Size action.
     * Resizes the board based on a radio value.
     * Size determines the block size of the board.
     * 
     * @author Kimberly Stewart
     *
     */
    public class KeyCustomAction extends AbstractAction {
        /**
         * Serial version ID.
         */
        private static final long serialVersionUID = 1L;
        
        /**
         * Integer representing the tool thickness.
         */
        private ArrayList<KeyBindings> myKeys;
        
        /**
         * Constructs the size action.
         * 
         * @param theSize the size to be set
         */
        public KeyCustomAction(ArrayList<KeyBindings> theKeys) {
            myKeys = theKeys;
            
        }
        
        /**
         * The action performed.
         * 
         * @param theEvent the Event
         */
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            System.out.println("It works!");
            KeyCustomPanel kc = new KeyCustomPanel(myKeys, mySize);
            String s = kc.start();
            System.out.println(s);
            myBoardPanel.pause();
        }
        
    }
    

}
