package io.github.xenocider.AgarIO.entity;

import io.github.xenocider.AgarIO.references.Reference;
import io.github.xenocider.AgarIO.util.Vector;

import java.io.File;

/**
 * Created by ict11 on 2015-09-28.
 */
public class Food extends Entity {

    public Food() {
        setSkin(new File("Food"));
        setLocation((int) (Math.random()* Reference.mapSize), (int) (Math.random()*Reference.mapSize));
    }

    public Food(double mass, double x, double y) {
        setSkin(new File("Food"));
        setLocation(x,y);
        setMass(mass);
    }


}
