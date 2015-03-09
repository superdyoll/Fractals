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

    private JLabel lblComplex = new JLabel();

    
    
    public void setComplex(String text){
        lblComplex.setText(text);
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
        JPanel pnlMandel = new MandelView(this);
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
