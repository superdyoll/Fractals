/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fractals.View;

import Fractals.Maths.Complex;
import Fractals.Controller.MandelController;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Lloyd
 */
public class MandelView extends JPanel implements MouseListener{
    MandelController control = new MandelController();
    private BufferedImage I;
    
    public MandelView(){
        setBounds(100, 100, 800, 600);
    }
    
    public void drawImageUp(){
        I = control.drawMandel(getWidth(), getHeight(), getWidth()/2, getHeight()/2, 570 , 150);
    }
    
    @Override
    public void paint(Graphics g) {
        drawImageUp();
        g.drawImage(I, 0, 0, this);
        System.out.println("Drawn");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Complex point = new Complex(e.getX(), e.getY());
        System.out.println("Pont Clicked" + point);
        control.setComplex(point.toString());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
