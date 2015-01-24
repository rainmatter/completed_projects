/*
 * TCSS 305 - Winter 2014
 * Assignment 5 - PowerPaint
 * 
 * PowerPaintFrame
 */

package gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import model.ColorIcon;
import model.DrawingPanel;
import tools.EllipseTool;
import tools.LineTool;
import tools.PencilTool;
import tools.RectangleTool;

/**
 * Digital Paint program.
 * <p)
 * Frame class generates the frame for the panels and tools.
 * <p>
 * Contains inner classes for tool actions.
 * 
 * @author Kimberly Stewart
 * @version 5 March 2014
 */
public class PowerPaintFrame extends JFrame {
    /**
     * Color of a selected tool button.
     */
    private static final Color SELECTED_COLOR = new Color(180, 180, 210);
    
    /**
     * Color pop up icon path.
     */
    private static final String COLOR_ICON_PATH = "/icons/color.png";
    
    /**
     * Name of the color buttons and menu items.
     */
    private static final String COLOR_LABEL = "Color...";
    
    /**
     * Name of the pencil buttons and menu items.
     */
    private static final String PENCIL_LABEL = "Pencil";
    
    /**
     * Name of the Line buttons and menu items.
     */
    private static final String LINE_LABEL = "Line";
    
    /**
     * Name of the Rectangle buttons and menu items.
     */
    private static final String RECT_LABEL = "Rectangle";
    
    /**
     * Name of the Ellipse buttons and menu items.
     */
    private static final String ELLIPSE_LABEL = "Ellipse";
    
    /**
     * Serial version ID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * The main panel of the frame.
     */
    private final JPanel myMainPanel;
    
    /**
     * The drawing panel where the drawing will be.
     */
    private final DrawingPanel myDrawingPanel;
    
    /**
     * The frame tool bar with buttons.
     */
    private final JToolBar myToolBar;
    
    /**
     * A list containing the tool bar buttons.
     */
    private List<JButton> myToolButtons;
    
    /**
     * A list containing the menu radio tool buttons.
     */
    private List<JRadioButtonMenuItem> myToolRadios;
    
    /**
     * Color menu item.
     */
    private JMenuItem myColorItem;
    
    /**
     * Color tool bar button.
     */
    private JButton myColorButton;
    
    /**
     * The undo menu item.
     */
    private JMenuItem myUndoItem;
    
    /**
     * The Redo menu item.
     */
    private JMenuItem myRedoItem;
    
    /**
     * Mouse action listeners.
     */
    private final MouseAction myMouseAction;
    
   /**
     * Constructs frame.
     * Initializes the panels and tool bar.
     * Sets layout & creates tool bar buttons.
     */
    public PowerPaintFrame() {
        myMainPanel = new JPanel();
        myMainPanel.setLayout(new BorderLayout());
        myDrawingPanel = new DrawingPanel();
        myDrawingPanel.setCurrentTool(new PencilTool());
        myMainPanel.add(myDrawingPanel, BorderLayout.CENTER);
        myToolBar = new JToolBar();
        createToolBarButtons();
        myMainPanel.add(myToolBar, BorderLayout.SOUTH);
        add(myMainPanel);
        myMouseAction = new MouseAction();
        myDrawingPanel.addMouseListener(myMouseAction);
        pack();
    }
    
    /**
     * Frame start method.
     * Controls settings for frame.
     * Creates and adds the menu bar.
     */
    public void start() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //Create new PowerPaint frame.
                final PowerPaintFrame frame = new PowerPaintFrame();
                frame.setTitle("TCSS 305 PowerPaint");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                final Image img = new ImageIcon(getClass().
                                          getResource("/icons/pencil.gif")).getImage();
                frame.setIconImage(img);
                //Set Location as center of screen.
                final Toolkit kit = Toolkit.getDefaultToolkit();
                final Dimension screenSize = kit.getScreenSize();
                final int screenWidth = (int) screenSize.getWidth();
                final int screenHeight = (int) screenSize.getHeight();
                frame.setLocation(screenWidth / 2 - 250, screenHeight / 2 - 150);
                frame.setJMenuBar(frame.createMenuBar());
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
        menuBar.add(addFileMenu());
        menuBar.add(addEditMenu());
        menuBar.add(addOptionsMenu());
        menuBar.add(addToolsMenu());
        menuBar.add(addHelpMenu());
        return menuBar;
    }
    
    /**
     * Creates the file section of the menu bar.
     * 
     * @return menu of file
     */
    private JMenu addFileMenu() {
        final JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');
        //Add clear menu item
        final JMenuItem clearItem = new JMenuItem("Clear");
        clearItem.setMnemonic('C');
        fileMenu.add(clearItem);
        final PowerPaintFrame.ClearAction clear = new PowerPaintFrame.ClearAction();
        clearItem.addActionListener(clear);
        fileMenu.addSeparator();
        //Add exit menu item
        final JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setMnemonic('x');
        fileMenu.add(exitItem);
        exitItem.addActionListener(new PowerPaintFrame.ExitAction());
        return fileMenu;
    }
    
    /**
     * Creates the Edit section of the menu bar.
     * 
     * @return menu of edit
     */
    private JMenu addEditMenu() {
        final JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic('d');
        myUndoItem = new JMenuItem("Undo");
        myUndoItem.setMnemonic('U');
        myUndoItem.setAccelerator(KeyStroke.getKeyStroke("control Z"));
        editMenu.add(myUndoItem);
        final UndoAction undo = new UndoAction();
        myUndoItem.addActionListener(undo);
        myUndoItem.setEnabled(false);
        myRedoItem = new JMenuItem("Redo");
        myRedoItem.setMnemonic('R');
        editMenu.add(myRedoItem);
        myRedoItem.setAccelerator(KeyStroke.getKeyStroke("control Y"));
        myRedoItem.addActionListener(new RedoAction());
        myRedoItem.setEnabled(false);
        return editMenu;
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
        final JCheckBoxMenuItem gridCheckItem = new JCheckBoxMenuItem("Grid");
        gridCheckItem.setMnemonic('G');
        optionsMenu.add(gridCheckItem);
        final GridAction grid = new GridAction(false);
        gridCheckItem.addActionListener(grid);
        //Thickness submenu
        final JMenu thicknessMenu = new JMenu("Thickness");
        thicknessMenu.setMnemonic('T');
        optionsMenu.add(thicknessMenu);
        //Thickness radios
        final ButtonGroup thicknessRadios = new ButtonGroup();
        final JRadioButtonMenuItem thick1 = new JRadioButtonMenuItem("1");
        final JRadioButtonMenuItem thick2 = new JRadioButtonMenuItem("2");
        final JRadioButtonMenuItem thick4 = new JRadioButtonMenuItem("4");
        thick1.setMnemonic('1');
        thick2.setMnemonic('2');
        thick4.setMnemonic('4');
        thicknessRadios.add(thick1);
        thicknessRadios.add(thick2);
        thicknessRadios.add(thick4);
        thicknessMenu.add(thick1);
        thicknessMenu.add(thick2);
        thicknessMenu.add(thick4);
        final ThicknessAction one = new ThicknessAction(1);
        thick1.addActionListener(one);
        thick1.setSelected(true);
        final ThicknessAction two = new ThicknessAction(2);
        thick2.addActionListener(two);
        final ThicknessAction four = new ThicknessAction(4);
        thick4.addActionListener(four);
        return optionsMenu;
    }
    
    /**
     * Creates the tools section of the menu.
     * 
     * @return menu for tools
     */
    private JMenu addToolsMenu() {
        final JMenu toolsMenu = new JMenu("Tools");
        toolsMenu.setMnemonic('T');
        myColorItem = new JMenuItem(COLOR_LABEL);
        myColorItem.setMnemonic('C');
        final ColorIcon ico = new ColorIcon(myDrawingPanel.getCurrentColor());
        myColorItem.setIcon(new ImageIcon(ico.getIcon()));
        final Icon img = new ImageIcon(getClass().getResource(COLOR_ICON_PATH));
        final ColorAction colorAction = new ColorAction(COLOR_LABEL, img);
        myColorItem.addActionListener(colorAction);
        toolsMenu.add(myColorItem);
        toolsMenu.addSeparator();
        
        final ButtonGroup toolsRadios = new ButtonGroup();
        final JRadioButtonMenuItem pencil = new JRadioButtonMenuItem(PENCIL_LABEL);
        pencil.setMnemonic('P');
        final JRadioButtonMenuItem line = new JRadioButtonMenuItem(LINE_LABEL);
        line.setMnemonic('L');
        final JRadioButtonMenuItem rectangle = new JRadioButtonMenuItem(RECT_LABEL);
        rectangle.setMnemonic('R');
        final JRadioButtonMenuItem ellipse = new JRadioButtonMenuItem(ELLIPSE_LABEL);
        ellipse.setMnemonic('E');
        
        final PencilAction pencilAction = new PencilAction(PENCIL_LABEL);
        pencil.addActionListener(pencilAction);
        final LineAction lineAction = new LineAction(LINE_LABEL);
        line.addActionListener(lineAction);
        final RectangleAction rectangleAction = new RectangleAction(RECT_LABEL);
        rectangle.addActionListener(rectangleAction);
        final RectangleAction ellipseAction = new RectangleAction(ELLIPSE_LABEL);
        ellipse.addActionListener(ellipseAction);
        
        pencil.setSelected(true);
        myToolRadios = new ArrayList<>();
        myToolRadios.add(pencil);
        myToolRadios.add(line);
        myToolRadios.add(rectangle);
        myToolRadios.add(ellipse);
        for (int i = 0; i < myToolRadios.size(); i++) {
            toolsRadios.add(myToolRadios.get(i));
            toolsMenu.add(myToolRadios.get(i));
        }
        setButtonAsSelected();
        return toolsMenu;
    }
    
    
    /**
     * Creates help section of the menu.
     * 
     * @return menu for help
     */
    private JMenu addHelpMenu() {
        final JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('H');
        final JMenuItem aboutItem = new JMenuItem("About...");
        aboutItem.setMnemonic('A');
        helpMenu.add(aboutItem);
        final AboutAction about = new AboutAction();
        aboutItem.addActionListener(about);
        return helpMenu;
    }
    
    
    /**
     * Creates the tool bar.
     * Adds buttons for color, pencil, line, rectangle, and ellipse.
     */
    private void createToolBarButtons() {
        //Add color button
        myColorButton = new JButton(COLOR_LABEL);
        final ColorIcon ico = new ColorIcon(myDrawingPanel.getCurrentColor());
        myColorButton.setIcon(new ImageIcon(ico.getIcon()));
        final ColorAction colorAction = 
                        new ColorAction(COLOR_LABEL, 
                                        new ImageIcon(getClass().
                                                      getResource(COLOR_ICON_PATH)));
        myColorButton.addActionListener(colorAction);
        //Add tool buttons
        final JButton pencilButton = new JButton(PENCIL_LABEL);
        final JButton lineButton = new JButton(LINE_LABEL);
        final JButton rectangleButton = new JButton(RECT_LABEL);
        final JButton ellipseButton = new JButton(ELLIPSE_LABEL);
        myColorButton.setMnemonic('C');
        pencilButton.setMnemonic('P');
        lineButton.setMnemonic('L');
        rectangleButton.setMnemonic('R');
        ellipseButton.setMnemonic('E');
        final PencilAction pencil = new PencilAction(PENCIL_LABEL);
        pencilButton.addActionListener(pencil);
        
        final LineAction line = new LineAction(LINE_LABEL);
        lineButton.addActionListener(line);
        final RectangleAction rectangle = new RectangleAction(RECT_LABEL);
        rectangleButton.addActionListener(rectangle);
        final EllipseAction ellipse = new EllipseAction(ELLIPSE_LABEL);
        ellipseButton.addActionListener(ellipse);
        myToolButtons = new ArrayList<>();
        myToolButtons.add(pencilButton);
        myToolButtons.add(lineButton);
        myToolButtons.add(rectangleButton);
        myToolButtons.add(ellipseButton);
        myToolBar.add(myColorButton);
        myToolBar.addSeparator();
        for (int i = 0; i < myToolButtons.size(); i++) {
            myToolBar.add(myToolButtons.get(i));
        }
    }
    
    /**
     * Changes the state of the buttons when selected or not selected.
     * Affects both menu tools and tool bar buttons.
     */
    private void setButtonAsSelected() {
        for (int i = 0; i < myToolButtons.size(); i++) {
            if (myToolButtons.get(i).getText().equals(myDrawingPanel.getCurrentTool())) {
                myToolButtons.get(i).setSelected(true);
                myToolButtons.get(i).setBackground(SELECTED_COLOR);
                myToolButtons.get(i).setRolloverEnabled(false);
            } else {
                myToolButtons.get(i).setSelected(false);
                myToolButtons.get(i).setBackground(null);
                myToolButtons.get(i).setRolloverEnabled(true);
            }
         
        }

        for (int i = 0; i < myToolRadios.size(); i++) {
            if (myToolRadios.get(i).getText().equals(myDrawingPanel.getCurrentTool())) {
                myToolRadios.get(i).setSelected(true);
            } else {
                myToolRadios.get(i).setSelected(false);
            }
        }
    }
    
    
    
    /**
     * Inner class About window.
     * 
     * Calls a pop up help window.
     * 
     * @author Kimbery Stewart
     *
     */
    public class AboutAction extends AbstractAction {

        /**
         * Serial version ID.
         */
        private static final long serialVersionUID = 1L;

        /**
         * Calls the pop up.
         * 
         * @param theEvent the event.
         */
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            final JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame, "TCSS 305 PowerPaint\nWinter 2014");
            frame.setLocationRelativeTo(myMainPanel);
            
        }
        
    }
    
    /**
     * Inner class Clear
     * 
     * Clears the drawing panel when clear menu item chosen.
     * 
     * @author Kimberly Stewart
     *
     */
    public class ClearAction extends AbstractAction {

        /**
         * Serial version ID.
         */
        private static final long serialVersionUID = 1L;

        /**
         * Clear action calls the clear method of the drawing panel.
         * 
         * @param theEvent the event.
         */
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myDrawingPanel.clear();
            myUndoItem.setEnabled(false);
            myRedoItem.setEnabled(false);
        }
        
    }
    
    /**
     * Inner class Color Action
     * 
     * Calls the color picker.
     * 
     * @author Kimberly Stewart
     *
     */
    public class ColorAction extends AbstractAction {
        
        /**
         * Serial version ID.
         */
        private static final long serialVersionUID = 1L;

        /**
         * Constructs a new color action.
         * Adds color icon to pop up window.
         * 
         * @param theText Name of the tool
         * @param theIcon Icon
         */
        public ColorAction(final String theText, final Icon theIcon) {
            super(theText, theIcon);
        }

        /**
         * Calls color chooser.
         * 
         * @param theEvent the event.
         */
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            final Color newColor = JColorChooser.
                            showDialog(myMainPanel, "Select a color", Color.BLACK);
            myDrawingPanel.setColor(newColor);
            final ColorIcon ico = new ColorIcon(newColor);
            
            myColorItem.setIcon(new ImageIcon(ico.getIcon()));
            myToolBar.repaint();
        }
        
    }
    
    
    /**
     * Inner class Exit Action
     * 
     * Terminates the program.
     * 
     * @author Kimberly Stewart
     *
     */
    public class ExitAction extends AbstractAction {

        /**
         * Serial version ID.
         */
        private static final long serialVersionUID = 1L;

        /**
         * Calls system exit.
         * 
         * @param theEvent the event.
         */
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            System.exit(0);
        }
        
    }
    
    /**
     * Inner class Grid action.
     * 
     * Turns the grid on or off when menu item checked.
     * 
     * @author Kimberly Stewart
     *
     */
    public class GridAction extends AbstractAction {
        /**
         * Serial version ID.
         */
        private static final long serialVersionUID = 1L;
        
        /**
         * Grid on/off.
         */
        private boolean myGridStatus;
        
        /**
         * Constructs a grid action.
         * Default is off.
         * 
         * @param theGridStatus grid status on or off
         */
        public GridAction(final Boolean theGridStatus) {
            myGridStatus = theGridStatus;
            
        }
        
        /**
         * Toggles the status of the gris and repaints.
         * 
         * @param theEvent the event
         */
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myGridStatus = !myGridStatus;
            myDrawingPanel.gridToggle(myGridStatus);
        }
        
    }
    
    
    /**
     * Inner class Thickness action.
     * 
     * Sets the tool thickness when radio number is selected.
     * 
     * @author Kimberly Stewart
     *
     */
    public class ThicknessAction extends AbstractAction {
        /**
         * Serial version ID.
         */
        private static final long serialVersionUID = 1L;
        
        /**
         * Integer representing the tool thickness.
         */
        private final int myThickness;
        
        /**
         * Constructs the thickness tool.
         * 
         * @param theThickness the thickness
         */
        public ThicknessAction(final int theThickness) {
            myThickness = theThickness;
            
        }
        
        /**
         * Constructs a new thickness action.
         * 
         * @param theEvent the event
         */
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myDrawingPanel.setThickness(myThickness);
        }
        
    }
    
    /**
     * Inner class Undo Action
     * 
     * Undo the last shape created.
     * 
     * @author Kimberly Stewart
     *
     */
    public class UndoAction extends AbstractAction {

        /**
         * Serial version ID.
         */
        private static final long serialVersionUID = 1L;

        /**
         * Undo the last shape.
         * 
         * @param theEvent the event.
         */
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myDrawingPanel.undo();
            if (!myDrawingPanel.isPanelEmpty()) {
                myUndoItem.setEnabled(true);
            } else {
                myUndoItem.setEnabled(false);
            }
            myRedoItem.setEnabled(true);
        }
        
    }
    
    /**
     * Inner class Redo Action.
     * 
     * Redraws the last shape undone.
     * 
     * @author Kimberly Stewart
     *
     */
    public class RedoAction extends AbstractAction {

        /**
         * Serial version ID.
         */
        private static final long serialVersionUID = 1L;

        /**
         * Calls redraws an undone shape.
         * 
         * @param theEvent the event.
         */
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myDrawingPanel.redo();
            if (!myDrawingPanel.isPanelEmpty()) {
                myUndoItem.setEnabled(true);
            } else {
                myUndoItem.setEnabled(false);
            }
            if(myDrawingPanel.getRedoTracker() == 0) {
                myRedoItem.setEnabled(false);
            }
        }
        
    }
    
    
    /**
     * Inner class Line action
     * 
     * Turns the selected tool on in the drawing panel.
     * 
     * @author Kimberly Stewart
     *
     */
    public class LineAction extends AbstractAction {
        /**
         * Serial version ID.
         */
        private static final long serialVersionUID = 1L;
        
        /**
         * String representing the current tool.
         */
        private final String myTool;
        private final LineTool myLineTool;
        
        /**
         * Constructs a new tool action.
         * 
         * @param theTool current tool
         */
        public LineAction(final String theTool) {
            myTool = theTool;
            myLineTool = new LineTool();
            
        }
        
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myDrawingPanel.setActiveTool(myTool);
            myDrawingPanel.setCurrentTool(myLineTool);
            
            setButtonAsSelected();
        }
        
    }
    
    /**
     * Inner class Rectangle action
     * 
     * Turns the selected tool on in the drawing panel.
     * 
     * @author Kimberly Stewart
     *
     */
    public class RectangleAction extends AbstractAction {
        /**
         * Serial version ID.
         */
        private static final long serialVersionUID = 1L;
        
        /**
         * String representing the current tool.
         */
        private final String myTool;
        private final RectangleTool myRectangleTool;
        
        /**
         * Constructs a new tool action.
         * 
         * @param theTool current tool
         */
        public RectangleAction(final String theTool) {
            myTool = theTool;
            myRectangleTool = new RectangleTool();
            
        }
        
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myDrawingPanel.setActiveTool(myTool);
            myDrawingPanel.setCurrentTool(myRectangleTool);
            
            setButtonAsSelected();
        }
        
    }
    
    
    /**
     * Inner class Ellipse action
     * 
     * Turns the selected tool on in the drawing panel.
     * 
     * @author Kimberly Stewart
     *
     */
    public class EllipseAction extends AbstractAction {
        /**
         * Serial version ID.
         */
        private static final long serialVersionUID = 1L;
        
        /**
         * String representing the current tool.
         */
        private final String myTool;
        private final EllipseTool myEllipseTool;
        
        /**
         * Constructs a new tool action.
         * 
         * @param theTool current tool
         */
        public EllipseAction(final String theTool) {
            myTool = theTool;
            myEllipseTool = new EllipseTool();
            
        }
        
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myDrawingPanel.setActiveTool(myTool);
            myDrawingPanel.setCurrentTool(myEllipseTool);
            
            setButtonAsSelected();
        }
        
    }
    
    /**
     * Inner class Pencil action
     * 
     * Turns the selected tool on in the drawing panel.
     * 
     * @author Kimberly Stewart
     *
     */
    public class PencilAction extends AbstractAction {
        /**
         * Serial version ID.
         */
        private static final long serialVersionUID = 1L;
        
        /**
         * String representing the current tool.
         */
        private final String myTool;
        private final PencilTool myPencilTool;
        
        /**
         * Constructs a new tool action.
         * 
         * @param theTool current tool
         */
        public PencilAction(final String theTool) {
            myTool = theTool;
            myPencilTool = new PencilTool();
            
        }
        
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myDrawingPanel.setActiveTool(myTool);
            myDrawingPanel.setCurrentTool(myPencilTool);
            
            setButtonAsSelected();
        }
        
    }
    
    
    /**
     * Inner class mouse listener.
     * 
     * @author Kimberly Stewart
     *
     */
    public class MouseAction extends MouseAdapter {


        
        /**
         * Mouse dragged action.
         * Stores the end X, Y point.
         * Draws the new shape.
         * 
         * @param theEvent the event
         */
        @Override
        public void mouseReleased(final MouseEvent theEvent) {
            if (!myDrawingPanel.isPanelEmpty()) {
                myUndoItem.setEnabled(true);
            } else {
                myUndoItem.setEnabled(false);
            }
            myRedoItem.setEnabled(false);
        }
        
    }
    
    
}
