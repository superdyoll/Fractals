/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fractals.Controller;

import Fractals.Maths.Complex;
import Fractals.Model.MandelModel;
import java.awt.image.BufferedImage;

/**
 *
 * @author Lloyd
 */
public class MandelController {

    public BufferedImage drawMandel(int width, int height, int iterations, int zoom) {
        MandelModel newMandel = new MandelModel();
        BufferedImage graph = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Complex point = new Complex((x-width)/zoom, (y-height)/zoom);
                int iter = newMandel.calculateMandelPoint(point, iterations);
                graph.setRGB(x, y, iter | (iter << 8));
            }
        }
        return graph;
    }

}
