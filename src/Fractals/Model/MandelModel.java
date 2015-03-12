/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fractals.Model;

import Fractals.Controller.MandelController;
import Fractals.Maths.Complex;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Lloyd
 */
public class MandelModel {

    public int calculateMandelPoint(Complex point, int iter) {
        Complex zNumber = point;
        while (iter > 0 && zNumber.modulusSquared()< 4) {
            //System.out.println(zNumber);
            zNumber = zNumber.square();
            zNumber = zNumber.add(point);
            iter--;
        }
        return iter;
    }

    public int calculateJuliaPoint(Complex point, Complex fixed, int iter) {
        Complex zNumber = point.add(fixed);
        while (iter > 0 && zNumber.modulusSquared() < 4) {
            zNumber = zNumber.square();
            zNumber = zNumber.add(fixed);
            iter--;
        }
        return iter;
    }

    public BufferedImage drawFractal(int width, int height, int maxIterations, MandelController mandelController) {
        mandelController.setImageDrawn(false);
        MandelModel newMandel = new MandelModel();
        BufferedImage graph = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Complex point = new Complex((float) (x - mandelController.getXCenter()) / mandelController.getZoom(), (float) (y - mandelController.getYCenter()) / mandelController.getZoom());
                //Work out the iterations taken diverge
                int iter;
                if (mandelController.isJuliaSet()) {
                    iter = newMandel.calculateJuliaPoint(point, mandelController.getFixed(), maxIterations);
                } else {
                    iter = newMandel.calculateMandelPoint(point, maxIterations);
                }
                Color c = mandelController.getColour(maxIterations, iter);
                int rgb = c.getRGB();
                graph.setRGB(x, y, rgb);
            }
        }
        mandelController.setImageDrawn(true);
        return graph;
    }

}
