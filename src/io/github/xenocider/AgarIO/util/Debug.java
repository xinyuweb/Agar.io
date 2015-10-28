package io.github.xenocider.AgarIO.util;

import io.github.xenocider.AgarIO.entity.Entity;
import io.github.xenocider.AgarIO.entity.PlayerBlobs;

public class Debug {

    public static void debug(Entity e) {
        System.out.println("-- Debug Info On " + e.toString() + " --");
        try {
            PlayerBlobs p = (PlayerBlobs) e;
            System.out.println("ID = " + p.getID());
        }
        catch (Exception ex){

        }
        System.out.println("Location = " + e.getLocation()[0] + ", " + e.getLocation()[1]);
        System.out.println("Mass = " + e.getMass());
        System.out.println("Skin = " + e.getSkin());
        System.out.println("Velocity = " + e.velocity.magnitude + " at " + e.velocity.direction + "*");
        try {
            PlayerBlobs p = (PlayerBlobs) e;
            System.out.println("PVelocity = " + p.playerVelocity.magnitude + " at " + p.playerVelocity.direction + "*");
            System.out.println("Target = " + p.target.toString() + " at " + p.target.getLocation()[0] + ", " + p.target.getLocation()[1]);
        }
        catch (Exception ex) {

        }
        System.out.println("Friction on = " + e.friction);
    }

}
