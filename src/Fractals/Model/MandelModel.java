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
public class MandelModel{
    
    public int calculateMandelPoint (Complex point, int iterations){
        Complex zNumber = point;
        while(iterations > 0 || zNumber.modulus() < 2){
            zNumber = zNumber.square();
            zNumber = zNumber.add(point);
            iterations--;
        }
        return iterations;
    }
    
    public int calculateJuliaPoint (Complex point, Complex fixed, int iterations){
        Complex zNumber = point.add(fixed);
        while (iterations > 0 || zNumber.modulus() < 2){
            zNumber = zNumber.square();
            zNumber = zNumber.add(fixed);
            iterations--;
        }
        return iterations;
    }
    
    
}
