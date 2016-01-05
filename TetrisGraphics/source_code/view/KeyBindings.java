/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author Muna
 */
public class KeyBindings {
    private AbstractAction myAction;
    private String myName;
    private int myKey;
    private String myText;
    
    public KeyBindings (AbstractAction theAction, String theName, int theKey) {
        myAction = theAction;
        myName = theName;
        myKey = theKey;
        myText = KeyEvent.getKeyText(myKey);
    }
    
    public AbstractAction getAction() {
        return myAction;
    }
    
    public String getName() {
        return myName;
    }
    
    public int getKey() {
        return myKey;
    }
    
    public String getText() {
        return myText;
    }
    
    public void setKey(int theKey) {
        myKey = theKey;
        myText = KeyEvent.getKeyText(myKey);
    }
    
}
