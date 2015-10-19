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
    private int[] location = {50,50};
    private int id = -1;
    private int mass = 1;

    public Entity() {
    }

    public File getSkin(){
        return skin;
    }

    public int[] getLocation(){
        return location;
    }
    public int getMass() {return mass;}

    public void setSkin(File s) {
        skin = s;
    }

    public void setLocation(int[] l) {
        location = l;
    }
    public void setLocation(int x, int y) {
        int[] loc = {x,y};
        location = loc;
    }
    public void setMass(int i) {
        mass = i;
    }

}
