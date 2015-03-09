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
        Complex zNumber = point;
        while (iter > 0 && zNumber.modulusSquared()< 4) {
            //System.out.println(zNumber);
            zNumber = zNumber.square();
            zNumber = zNumber.add(point);
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
