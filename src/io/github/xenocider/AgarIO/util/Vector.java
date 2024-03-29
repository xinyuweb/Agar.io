package io.github.xenocider.AgarIO.util;

import io.github.xenocider.AgarIO.GameLoop;

import java.util.List;

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

    public void add(double d, double m) {
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
        //System.out.println(dir + ", " + mag);
        direction = dir;
        magnitude = mag;
    }

    public double getMagX() {
        return Math.sin(direction*Math.PI/180)*magnitude;
    }

    public double getMagY() {
        return Math.cos(direction * Math.PI / 180)*magnitude;
    }

    public static double getAngle(double x1, double y1, double x2, double y2) {
        return Math.atan2((x1 - x2),(y1 - y2)) * 180 / Math.PI;
    }

    public double getMag() {
        return magnitude;
    }

    public static double getMagX(double d, double m) {
        return Math.sin(d*Math.PI/180)*m;
    }
    public static double getMagY(double d, double m) {
        return Math.cos(d * Math.PI / 180)*m;
    }

}
