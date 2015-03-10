/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fractals.View;

import Fractals.Maths.Complex;
import Fractals.Controller.MandelController;
import Fractals.Model.MandelModel;
import java.awt.Color;
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
    private MandelController control;
    private BufferedImage I;
    private int currentZoom, currentXCenter, currentYCenter;
    
    public MandelView(MandelController controller){
        setBounds(100, 100, 800, 600);
        addMouseListener(this);
        control = controller;
    }
    
    public BufferedImage drawMandel(int width, int height, int xCenter, int yCenter, int iterations, int zoom) {
        currentZoom = zoom;
        currentXCenter = xCenter;
        currentYCenter = yCenter;
        System.out.println("Drawing mandel");
        System.out.println("width: " + width + "height: " + height);
        MandelModel newMandel = new MandelModel();
        BufferedImage graph = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        System.out.println("Image created");
        System.out.println(height + " heigh");
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Complex point = new Complex(((float) (x - xCenter) / zoom), ((float) (y - yCenter) / zoom));
                int iter = newMandel.calculateMandelPoint(point, iterations);
                Color c = getColour(iterations, iter);
                int rgb = c.getRGB();
                graph.setRGB(x, y, rgb);
            }
        }
        System.out.println("For loops finished");
        return graph;
    }
    
    public Color getColour(int maxIterations, int iter) {
        if (iter < maxIterations && iter > 0) {
            int i = iter % 16;
            Color c;
            switch (i) {
                case 0:
                    c = new Color(66, 30, 15);
                    break;
                case 1:
                    c = new Color(25, 7, 26);
                    break;
                case 2:
                    c = new Color(9, 1, 47);
                    break;
                case 3:
                    c = new Color(4, 4, 73);
                    break;
                case 4:
                    c = new Color(0, 7, 100);
                    break;
                case 5:
                    c = new Color(12, 44, 138);
                    break;
                case 6:
                    c = new Color(24, 82, 177);
                    break;
                case 7:
                    c = new Color(57, 125, 209);
                    break;
                case 8:
                    c = new Color(134, 181, 229);
                    break;
                case 9:
                    c = new Color(211, 236, 248);
                    break;
                case 10:
                    c = new Color(241, 233, 191);
                    break;
                case 11:
                    c = new Color(248, 201, 95);
                    break;
                case 12:
                    c = new Color(255, 170, 0);
                    break;
                case 13:
                    c = new Color(204, 128, 0);
                    break;
                case 14:
                    c = new Color(153, 87, 0);
                    break;
                case 15:
                    c = new Color(106, 52, 3);
                    break;
                default:
                    c = new Color(255,255,255);
                    break;
            }
            return c;
        } else if (iter == maxIterations){
            return new Color (255,255,255);
        }else{
            return new Color (0,0,0);
        }
    }
    
    @Override
    public void paint(Graphics g) {
        I = drawMandel(getWidth(), getHeight(), getWidth()/2, getHeight()/2, 570 , 150);
        g.drawImage(I, 0, 0, this);
        System.out.println("Drawn");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Complex point = new Complex(((float) (e.getX() - currentXCenter) / currentZoom), ((float) (e.getY() - currentYCenter) / currentZoom));
        System.out.println("Pont Clicked" + point);
        control.setComplex(point.toString());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
