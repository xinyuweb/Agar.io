package io.github.xenocider.AgarIO.entity;

import io.github.xenocider.AgarIO.util.Vector;

import java.awt.*;
import java.io.File;

/**
 * Created by ict11 on 2015-09-28.
 */
public class Entity {

    private File skin = new File("dud");
    public Vector velocity = new Vector(0,0);
    private double[] location = {50,50};
    private int id = -1;
    private double mass = 1;
    public boolean friction = true;

    public Entity() {
    }

    public File getSkin(){
        return skin;
    }

    public double[] getLocation(){
        return location;
    }
    public double getMass() {return mass;}

    public void setSkin(File s) {
        skin = s;
    }

    public void setLocation(double[] l) {
        location = l;
    }
    public void setLocation(double x, double y) {
        double[] loc = {x,y};
        location = loc;
    }
    public void setMass(double i) {
        mass = i;
    }

    public Vector getVelocity() {return velocity;}

}
