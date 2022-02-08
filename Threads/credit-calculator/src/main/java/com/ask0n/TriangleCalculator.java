package com.ask0n;

public class TriangleCalculator {
    public static final double SCALE = Math.pow(10, 1);

    public double areaWithTwoCathetus(int cat1, int cat2){
        double result = 0.5 * cat1 * cat2;
        return Math.ceil(result * SCALE) / SCALE;
    }

    public double areaWithOneCathetus(int hypo, int cat){
        double result = 0.5 * cat * Math.sqrt(Math.pow(hypo, 2) - Math.pow(cat, 2));
        return Math.ceil(result * SCALE) / SCALE;
    }
}
