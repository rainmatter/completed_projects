/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tools;

import java.awt.Shape;

/**
 *
 * @author Muna
 */
public abstract class PowerTool {
    
    public abstract void createShape(final int theStartX, final int theStartY, final int theEndX, 
                      final int theEndY);
    
    public Shape getShape() {
        return null;
    }
}

