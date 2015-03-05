/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fractals.View;

import Fractals.Maths.Complex;
import Fractals.Controller.MandelController;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Lloyd
 */
public class MandelView extends AbstractViewFrame{
    MandelController control = new MandelController();
    private BufferedImage I;
    
    public MandelView(){
        super("Mandelbrot Set");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = control.drawMandel(0, 0, getWidth(), getHeight(), 50, 150);
    }
    
    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }
    
    public static void main (String [] args){
        new MandelView().setVisible(true);
    }
}
