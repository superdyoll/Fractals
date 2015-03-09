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
                Complex point = new Complex((x-(width/2))/zoom, (y-(height/2))/zoom);
                int iter = newMandel.calculateMandelPoint(point, iterations);
                graph.setRGB(x, y, iter | (iter << 8));
            }
        }
        /*for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                zx = zy = 0;
                cX = (x - 400) / ZOOM;
                cY = (y - 300) / ZOOM;
                int iter = MAX_ITER;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iter--;
                }
                graph.setRGB(x, y, iter | (iter << 8));
            }
        }*/
        System.out.println("For loops finished");
        return graph;
    }
    
    public static void main (String [] args){
        new MandelView().setVisible(true);
    }

}
