package io.github.xenocider.AgarIO.entity;

import io.github.xenocider.AgarIO.util.Vector;

import java.io.File;

/**
 * Created by ict11 on 2015-09-23.
 */
public class PlayerBlobs extends Entity{

    public Vector playerVelocity = new Vector(0,0);

    public PlayerBlobs() {
        setSkin(new File("player"));
        setLocation((int) (Math.random() * 1000), (int) (Math.random() * 1000));
    }


}
