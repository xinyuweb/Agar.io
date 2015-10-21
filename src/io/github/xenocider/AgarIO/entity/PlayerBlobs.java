package io.github.xenocider.AgarIO.entity;

import io.github.xenocider.AgarIO.references.Reference;
import io.github.xenocider.AgarIO.util.Vector;

import java.io.File;

/**
 * Created by ict11 on 2015-09-23.
 */
public class PlayerBlobs extends Entity{

    public Vector playerVelocity = new Vector(0,0);
    private int id = 0;
    public Entity target = null;

    public PlayerBlobs(int id) {
        setSkin(new File("player"));
        System.out.println("Reg Blob created");
        setLocation((int) (Math.random() * Reference.mapSize), (int) (Math.random() * Reference.mapSize));
        setMass(2);
        setID(id);
    }
    public PlayerBlobs(int id, int mass,int x, int y) {
        System.out.println("Split created");
        setSkin(new File("player"));
        setLocation(x,y);
        setMass(mass);
        setID(id);
    }

    public int getID() {
        return id;
    }
    public void setID(int i) {
        id = i;
    }

}
