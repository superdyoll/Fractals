/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fractals.View;

import Fractals.Maths.Complex;
import javax.swing.JButton;

/**
 *
 * @author Lloyd
 */
public class FavButton extends JButton{
    private Complex fixed;
    private int zoom;
    
    FavButton(String text, Complex fixed, int zoom){
        super (text);
        this.fixed = fixed;
        this.zoom = zoom;
    }

    /**
     * @return the fixed
     */
    public Complex getFixed() {
        return fixed;
    }

    /**
     * @param fixed the fixed to set
     */
    public void setFixed(Complex fixed) {
        this.fixed = fixed;
    }

    /**
     * @return the zoom
     */
    public int getZoom() {
        return zoom;
    }

    /**
     * @param zoom the zoom to set
     */
    public void setZoom(int zoom) {
        this.zoom = zoom;
    }
    
    
}
