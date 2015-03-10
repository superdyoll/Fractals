/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fractals.View;

import Fractals.Controller.MandelController;
import Fractals.Maths.Complex;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Lloyd
 */
public class MandelView{

    private final JLabel lblComplex = new JLabel();
    private MandelController pnlJulia;

    
    public void setComplex(Complex point){
        lblComplex.setText(point.toString());
        pnlJulia.setFixed(point);
        pnlJulia.repaint();
    }

    
    public void drawStuff(){
        //Basic Frame stuff
        JFrame frmOuter = new JFrame ("Mandlebrot Set");
        System.out.println("Titled");
        frmOuter.setBounds(100, 100, 800, 600);
        frmOuter.setDefaultCloseOperation(frmOuter.EXIT_ON_CLOSE);
        
        Container pnlMain = frmOuter.getContentPane();
        pnlMain.setBounds(100, 100, frmOuter.getWidth(), frmOuter.getHeight());
        
        
        //Make mandel panel
        JPanel pnlMandel = new MandelController(this);
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
        
        //Make Julia frame
        JFrame frmJulia = new JFrame ("Julia Set");
        System.out.println("Titled");
        frmJulia.setBounds(100, 100, 400, 300);
        
        Container pnlJuliaMain = frmJulia.getContentPane();
        
        //Make mandel panel
        pnlJulia = new MandelController(this, true, 50);
        pnlJuliaMain.add(pnlJulia,BorderLayout.CENTER);
        pnlJulia.setVisible(true);
        
        frmJulia.setVisible(true);
    }

    public static void main(String[] args) {
        MandelView mandel = new MandelView();
        mandel.drawStuff();
    }

}
