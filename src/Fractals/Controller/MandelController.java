/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fractals.Controller;

import Fractals.Maths.Complex;
import Fractals.Model.MandelModel;
import Fractals.View.MandelView;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 *
 * @author Lloyd
 */
public class MandelController{

    private Color[] colorMap;
    private JLabel lblComplex = new JLabel();

    public BufferedImage drawMandel(int width, int height, int xCenter, int yCenter, int iterations, int zoom) {
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
    
    public void setComplex(String text){
        lblComplex.setText(text);
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
    
    public void drawStuff(){
        //Basic Frame stuff
        JFrame frmOuter = new JFrame ("Mandlebrot Set");
        System.out.println("Titled");
        frmOuter.setBounds(100, 100, 800, 600);
        frmOuter.setResizable(false);
        frmOuter.setDefaultCloseOperation(frmOuter.EXIT_ON_CLOSE);
        
        Container pnlMain = frmOuter.getContentPane();
        pnlMain.setBounds(100, 100, frmOuter.getWidth(), frmOuter.getHeight());
        
        
        //Make mandel panel
        JPanel pnlMandel = new MandelView();
        pnlMain.add(pnlMandel,BorderLayout.CENTER);
        pnlMandel.setVisible(true);
        
        
        lblComplex.setText("Click on a point to view the complex number");
        
        //Make complex point bit
        JPanel pnlComplex = new JPanel();
        pnlComplex.setBounds(100, 100, frmOuter.getWidth(), (int) (frmOuter.getHeight() * 0.01));      
        pnlComplex.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnlComplex.add(lblComplex);
        pnlMain.add(pnlComplex, BorderLayout.SOUTH);
        pnlComplex.setVisible(true);
        
        //frmOuter.add(pnlMain, BorderLayout.CENTER);
        //frmOuter.pack();
        frmOuter.setVisible(true);
    }

    public static void main(String[] args) {
        MandelController mandel = new MandelController();
        mandel.drawStuff();
    }

}
