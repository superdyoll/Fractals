/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fractals.Maths;

/**
 *
 * @author Lloyd
 */
public class Complex {
    
    private double real;
    private double imaginary;
    
    public Complex (double real, double imaginary){
        this.real = real;
        this.imaginary = imaginary;
    }
    
    public Complex square(){
        double realPart = (getReal()*getReal()) - (getImaginary()*getImaginary());
        double imaginaryPart = 2 * getReal() * getImaginary();
        return new Complex (realPart, imaginaryPart);
    }
    
    public double modulusSquared (){
        return (getReal()*getReal()) + (getImaginary() * getImaginary());
    }
    
    public Complex add(Complex d){
        double realPart = real + d.getReal();
        double imaginaryPart = imaginary + d.getImaginary();
        return new Complex (realPart, imaginaryPart);
    }

    /**
     * @return the real
     */
    public double getReal() {
        return real;
    }

    /**
     * @param real the real to set
     */
    public void setReal(double real) {
        this.real = real;
    }

    /**
     * @return the imaginary
     */
    public double getImaginary() {
        return imaginary;
    }

    /**
     * @param imaginary the imaginary to set
     */
    public void setImaginary(double imaginary) {
        this.imaginary = imaginary;
    }
}