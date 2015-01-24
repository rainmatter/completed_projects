/*
 * TCSS 305 - Winter 2014
 * Assignment 5 - PowerPaint
 * 
 * Drawing Panel
 */

package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import tools.PencilTool;
import tools.PowerTool;

/**
 * Digital Paint program.
 * <p)
 * Drawing panel stores and draws the current drawing.
 * <p>
 * Contains inner classes for mouse listeners.
 * 
 * @author Kimberly Stewart
 * @version 5 March 2014
 */
public class DrawingPanel extends JPanel {
    /**
     * Pencil tool.
     */
    private static final String PENCIL = "Pencil";
    
    /**
     * Grid dimensions.
     */
    private static final int GRID_DIM = 10;
    
    /**
     * Width of the panel.
     */
    private static final int WIDTH = 500;
    
    /**
     * Height of the panel.
     */
    private static final int HEIGHT = 300;
    
    /**
     * Serial version ID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * List containing the current drawing.
     */
    private final List<PowerShape> myDrawing;
    
    /**
     * List containing the redo shapes.
     */
    private final List<PowerShape> myRedoShapes;
    
    private int myRedoTracker;
    
    /**
     * Current color in use.
     */
    private Color myCurrentColor;
    
    /**
     * Current tool thickness in use.
     */
    private int myCurrentThickness;
    
    /**
     * Current Tool in use.
     */
    private String myActiveTool;
    
    /**
     * Current Tool in use.
     */
    private PowerTool myCurrentTool;
    
    /**
     * Mouse action listeners.
     */
    private final MouseAction myMouseAction;
    
    /**
     * For when the mouse is being dragged.
     */
    private Boolean myDragStatus;
    
    /**
     * Drag shape.
     */
    private PowerShape myTempShape;
    
    /**
     * 2D Graphics object.
     */
    private Graphics2D myGraphics2D;
    
    /**
     * Whether the grid should be drawn.
     */
    private boolean myGridStatus;
    
    /**
     * Constructs a new Drawing panel.
     * 
     * Initializes default tool, color, and thickness.
     */
    public DrawingPanel() {
        myDrawing = new ArrayList<>();
        myRedoShapes = new ArrayList<>();
        myCurrentColor = Color.BLACK;
        myCurrentThickness = 1;
        myActiveTool = PENCIL;
        myCurrentTool = null;
        myMouseAction = new MouseAction();
        addMouseListener(myMouseAction);
        addMouseMotionListener(myMouseAction);
        myRedoTracker = -1;
        myDragStatus = false;
    }
    
    /**
     * Sets the size of the drawing panel.
     * 
     * @return the dimension
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }
    
    
    /**
     * Paint component where drawing is drawn.
     * 
     * @param theGraphics the graphics
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        myGraphics2D = (Graphics2D) theGraphics;
        myGraphics2D.setColor(Color.WHITE);
        myGraphics2D.fillRect(0, 0, this.getWidth(), this.getHeight());
        if (myGridStatus) {
            drawGrid();
        }
        myGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                                      RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i = 0; i < myDrawing.size(); i++) {
            myGraphics2D.setColor(myDrawing.get(i).getColor());
            myGraphics2D.setStroke(new BasicStroke(myDrawing.get(i).getThickness()));
            myGraphics2D.draw(myDrawing.get(i).getShape());
        }
        if (myDragStatus) {
            myGraphics2D.setColor(myTempShape.getColor());
            myGraphics2D.setStroke(new BasicStroke(myTempShape.getThickness()));
            myGraphics2D.draw(myTempShape.getShape());
        }
    }
    
    /**
     * Draws the Grid.
     */
    private void drawGrid() {
        myGraphics2D.setColor(Color.BLACK);
        for (int i = 0; i < this.getWidth() / (GRID_DIM - 1); i++) {
            myGraphics2D.draw(new Line2D.Double(i * GRID_DIM, 
                                                0, i * GRID_DIM, this.getHeight()));
        }
        for (int i = 0; i < this.getHeight() / (GRID_DIM - 1); i++) {
            myGraphics2D.draw(new Line2D.Double(0, i * GRID_DIM, 
                                                this.getWidth(), i * GRID_DIM));
        }
    }
    
    /**
     * Sets the current tool.
     * 
     * @param theActiveTool the tool
     */
    public void setActiveTool(final String theActiveTool) {
        myActiveTool = theActiveTool;
    }
    
    /**
     * Sets the current tool.
     * 
     * @param theTool the tool
     */
    public void setCurrentTool(final PowerTool theTool) {
        myCurrentTool = theTool;
    }
    
    /**
     * Sets the current color.
     * 
     * @param theColor the color
     */
    public void setColor(final Color theColor) {
        myCurrentColor = theColor;
    }
    
    /**
     * Sets the current thickness.
     * 
     * @param theThickness the thickness
     */
    public void setThickness(final int theThickness) {
        myCurrentThickness = theThickness;
    }
    
    /**
     * Gets the current color.
     * 
     * @return color
     */
    public Color getCurrentColor() {
        return myCurrentColor;
    }
    
    /**
     * Get current tool.
     * 
     * @return the tool.
     */
    public String getCurrentTool() {
        return myActiveTool;
    }
    
    public Boolean isPanelEmpty() {
        return myDrawing.isEmpty();
    }
    
    public int getRedoSize() {
        return myRedoShapes.size();
    }
    
    
    public int getRedoTracker() {
        return myRedoTracker;
    }
    
    /**
     * Sets the grid status.
     * 
     * @param theGridStatus the grid status.
     */
    public void gridToggle(final Boolean theGridStatus) {
        myGridStatus = theGridStatus;
        repaint();
    }
    
    /**
     * Clears the drawing panel.
     */
    public void clear() {
        myDrawing.removeAll(myDrawing);
        myRedoShapes.removeAll(myRedoShapes);
        myRedoTracker = -1;
        repaint();
    }
    
    public void undo() {
        myRedoShapes.add(myDrawing.get(myDrawing.size() - 1));
        if (myRedoShapes.size() > 100) {
            myRedoShapes.remove(0);
        }
        myDrawing.remove(myDrawing.size() - 1);
        myRedoTracker++;
        repaint();
    }
    
    public void redo() {
        myRedoTracker--;
        myDrawing.add(myRedoShapes.get(myRedoTracker));
        repaint();
    }
    
    
    
    
    /**
     * Inner class mouse listener.
     * 
     * @author Kimberly Stewart
     *
     */
    public class MouseAction extends MouseAdapter {
        /**
         * Starting X point.
         */
        private int myStartX;
        
        /**
         * Starting Y point.
         */
        private int myStartY;
        
        /**
         * End X point.
         */
        private int myEndX;
        
        /**
         * End Y point.
         */
        private int myEndY;

        /**
         * Mouse pressed action.
         * Stores starting x, y point.
         * 
         * @param theEvent the event
         */
        @Override
        public void mousePressed(final MouseEvent theEvent) {
            myStartX = theEvent.getX();
            myStartY = theEvent.getY();
            if (myActiveTool.equalsIgnoreCase(PENCIL)) {
                myCurrentTool = new PencilTool();
            }
        }
        
        /**
         * Mouse dragged action.
         * Either drags the pencil or displays temporary shape.
         * 
         * @param theEvent the event
         */
        @Override
        public void mouseDragged(final MouseEvent theEvent) {
            myDragStatus = true;
            myEndX = theEvent.getX();
            myEndY = theEvent.getY();
            if (myActiveTool.equalsIgnoreCase(PENCIL)) {
                myCurrentTool.createShape(myStartX, myStartY, 
                                          myEndX, myEndY);
                myStartX = theEvent.getX();
                myStartY = theEvent.getY();
            } else {
                myCurrentTool.createShape(myStartX, myStartY, 
                                              myEndX, myEndY);
            }
            myTempShape = new PowerShape(myCurrentTool.getShape(), myCurrentColor, myCurrentThickness);
            repaint();
        }
        
        /**
         * Mouse released action.
         * Stores the end X, Y point.
         * Draws the new shape.
         * 
         * @param theEvent the event
         */
        @Override
        public void mouseReleased(final MouseEvent theEvent) {
            myDragStatus = false;
            myEndX = theEvent.getX();
            myEndY = theEvent.getY();   
            myCurrentTool.createShape(myStartX, myStartY, 
                                              myEndX, myEndY);
            myDrawing.add(new PowerShape(myCurrentTool.getShape(), myCurrentColor, myCurrentThickness));
            if (myRedoTracker != -1) {
                myRedoShapes.removeAll(myRedoShapes);
            }
            myRedoTracker = 0;
            repaint();
        }
        
    }
    
    
    
}
