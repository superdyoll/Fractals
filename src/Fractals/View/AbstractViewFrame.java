/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fractals.View;

import java.beans.PropertyChangeEvent;
import javax.swing.JFrame;

/**
 *
 * @author Lloyd
 */
public abstract class AbstractViewFrame extends JFrame{
    
    public AbstractViewFrame(){
        super();
    }
    
    public AbstractViewFrame(String text){
        super(text);
    }

    public void modelPropertyChange(PropertyChangeEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}