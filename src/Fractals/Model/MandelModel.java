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

    private int width, height, maxIterations, noIterations;
    private MandelController mandelController;
    private BufferedImage image;

    public int calculateMandelPoint(Complex point, int iter) {
        Complex zNumber = point;
        while (iter > 0 && zNumber.modulusSquared() < 4) {
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

    public BufferedImage drawFractal(int width, int height, int maxIterations, MandelController mandelController) throws InterruptedException {
        this.width = width;
        this.height = height;
        this.maxIterations = maxIterations;
        this.mandelController = mandelController;
        mandelController.setImageDrawn(false);
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        noIterations = Runtime.getRuntime().availableProcessors() * 2;        
        
        Thread[] threads = new Thread[noIterations];

        for (int i = 0; i <noIterations; i++) {
            threads[i] = new Thread(new MThread(i));
            threads[i].start();
        }
        for (int i = 0; i <noIterations; i++) {
            threads[i].join();
        }
        mandelController.setImageDrawn(true);
        return image;
    }

    class MThread implements Runnable {

        private int i;

        public MThread(int i) {
            this.i = i;
        }

        //Method uses the thread number to draw the mandelbrot in columns
        public void run() {
            MandelModel newMandel = new MandelModel();
            for (int x = i; x < width; x += noIterations) {
                for (int y = 0; y < height; y++) {
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
                    image.setRGB(x, y, rgb);
                }
            }
        }
    }
}
