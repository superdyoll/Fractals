/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fractals.Model;

import Fractals.Maths.Complex;

/**
 *
 * @author Lloyd
 */
public class MandelModel {

    private double zx, zy, cX, cY, tmp;
    
    public int calculateMandelPoint(Complex point, int iter) {
        /*Complex zNumber = point;
         while(iter > 0 && zNumber.modulus() < 2){
         zNumber = zNumber.square();
         zNumber = zNumber.add(point);
         iterations--;
         }*/
        zx = zy = 0;
        cX = point.getReal();
        cY = point.getImaginary();
        while (zx * zx + zy * zy < 4 && iter > 0) {
            tmp = zx * zx - zy * zy + cX;
            zy = 2.0 * zx * zy + cY;
            zx = tmp;
            iter--;
        }
        return iter;
    }

    public int calculateJuliaPoint(Complex point, Complex fixed, int iterations) {
        Complex zNumber = point.add(fixed);
        while (iterations > 0 || zNumber.modulus() < 2) {
            zNumber = zNumber.square();
            zNumber = zNumber.add(fixed);
            iterations--;
        }
        return iterations;
    }

}
