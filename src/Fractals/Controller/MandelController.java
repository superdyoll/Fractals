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

    public BufferedImage drawMandel(int minX, int minY, int maxX, int maxY, int iterations, int zoom) {
        MandelModel newMandel = new MandelModel();
        BufferedImage graph = new BufferedImage(maxX, maxY, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                Complex point = new Complex((x-maxX)/zoom, (y-maxY)/zoom);
                int iter = newMandel.calculateMandelPoint(point, iterations);
                graph.setRGB(x, y, iter | (iter << 8));
            }
        }
        return graph;
    }

}
