package io.github.xenocider.AgarIO.entity;

import io.github.xenocider.AgarIO.util.Vector;

import java.io.File;

/**
 * Created by ict11 on 2015-09-28.
 */
public class Entity {

    private File skin = new File("dud");
    private Vector velocity = new Vector(0,0);
    private int[] location = {0,0};
    private int id = -1;

    public Entity(int i) {
        id = i;
    }

    public File getSkin(){
        return skin;
    }
    public Vector getVelocity(){
        return velocity;
    }
    public int[] getLocation(){
        return location;
    }

    protected void setSkin(File s) {
        skin = s;
    }
    protected void setVelocity(Vector v) {
        velocity = v;
    }
    protected void setLocation(int[] l) {
        location = l;
    }

}
