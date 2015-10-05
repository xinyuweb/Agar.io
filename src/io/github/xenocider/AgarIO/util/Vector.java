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
        double magX = Math.sin(direction * Math.PI / 180)*magnitude;
        double magY = Math.cos(direction * Math.PI / 180)*magnitude;
        //System.out.println(d + " & " + m);
        double mx = Math.sin(d*Math.PI/180)*m;
        double my = Math.cos(d * Math.PI / 180)*m;
        //System.out.println(Math.cos(d * Math.PI / 180));
        //System.out.println(mx + ", " + my);
        magX = magX + mx;
        magY = magY + my;
        double mag = Math.sqrt(Math.pow(magX, 2) + Math.pow(magY, 2));
        double dir = Math.atan2(magX,magY)*180/Math.PI;
        System.out.println(dir + ", " + mag);
        return new Vector(dir, mag);
    }

    public double getMagX() {
        return Math.sin(direction*Math.PI/180)*magnitude;
    }

    public double getMagY() {
        return Math.cos(direction*Math.PI/180)*magnitude;
    }
}
