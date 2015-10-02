package io.github.xenocider.AgarIO.util;

/**
 * Created by ict11 on 2015-09-28.
 */
public class Vector {

    public double direction;
    public double magnitude;

    public Vector(double d, double m) {
        direction = d;
        magnitude = m;
    }

    public Vector add(double d, double m) {
        double magX = Math.sin(direction)*magnitude;
        double magY = Math.cos(direction)*magnitude;
        double mx = Math.sin(d)*m;
        double my = Math.cos(d)*m;
        magX = magX + mx;
        magY = magY + my;
        double mag = Math.sqrt(Math.pow(magX, 2) + Math.pow(magY, 2));
        double dir = Math.atan(magY/magX);
        return new Vector(dir, mag);
    }

    public double getMagX() {
        return Math.sin(direction)*magnitude;
    }

    public double getMagY() {
        return Math.cos(direction)*magnitude;
    }
}
