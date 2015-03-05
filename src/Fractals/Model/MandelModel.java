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
public class MandelModel extends AbstractModel{
    
    public int calculateMandelPoint (Complex point, int iterations){
        Complex zNumber = point;
        int x = 0;
        while (x <= iterations | zNumber.modulus() < 2){
            zNumber = zNumber.square();
            zNumber = zNumber.add(point);
            x++;
        }
        return x;
    }
    
    public int calculateJuliaPoint (Complex point, Complex fixed, int iterations){
        Complex zNumber = point.add(fixed);
        int x = 0;
        while (x <= iterations | zNumber.modulus() < 2){
            zNumber = zNumber.square();
            zNumber = zNumber.add(fixed);
            x++;
        }
        return x;
    }
    
    
}
