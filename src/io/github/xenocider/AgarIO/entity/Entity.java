package io.github.xenocider.AgarIO.entity;

import java.io.File;
import java.net.URL;
import java.util.Vector;

/**
 * Created by ict11 on 2015-09-23.
 */
public class Entity {

    protected File skin;
    protected Vector velocity;
    protected int[] location = {0,0};

    public Entity() {
        skin = new File("dud");
        velocity = new Vector();
    }

    public File getSkin() {
        return skin;
    }

    public int[] getLocation() {
        return location;
    }

    public Vector getVelocity() {
        return velocity;
    }

}
