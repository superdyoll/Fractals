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
import javax.swing.JFrame;

/**
 *
 * @author Lloyd
 */
public class MandelView extends JFrame{
    MandelController control = new MandelController();
    private BufferedImage I;
    
    public MandelView(){
        super("Mandelbrot Set");
        System.out.println("Titled");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        System.out.println("Basic Jframe things done");
        I = control.drawMandel(getWidth(), getHeight(), 570, 100);
        System.out.println("Image created");
    }
    
    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
        System.out.println("Drawn");
    }
    
}
