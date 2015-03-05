/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fractals.Controller;

import Fractals.Maths.Complex;
import Fractals.Model.MandelModel;
import Fractals.View.MandelView;
import java.awt.image.BufferedImage;

/**
 *
 * @author Lloyd
 */
public class MandelController {

    public BufferedImage drawMandel(int width, int height, int iterations, int zoom) {
        System.out.println("Drawing mandel");
        MandelModel newMandel = new MandelModel();
        BufferedImage graph = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        System.out.println("Image created");
        System.out.println(height + " heigh");
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Complex point = new Complex((x-width)/zoom, (y-height)/zoom);
                if(y > 299){
                    System.out.println(point + " point");
                }
                int iter = newMandel.calculateMandelPoint(point, iterations);
                if(y > 300){
                    System.out.println(iter + " iter");
                }
                graph.setRGB(x, y, iter | (iter << 8));
            }
            System.out.println(y + " loop");
        }
        System.out.println("For loops finished");
        return graph;
    }
    
    public static void main (String [] args){
        new MandelView().setVisible(true);
    }

}
