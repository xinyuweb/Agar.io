package io.github.xenocider.AgarIO;

import io.github.xenocider.AgarIO.SexyStuff.Gooey;
import io.github.xenocider.AgarIO.SexyStuff.IdiotBox;
import io.github.xenocider.AgarIO.entity.PlayerBlobs;
import io.github.xenocider.AgarIO.listener.KeyListener;

import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by ict11 on 2015-09-30.
 */
public class GameLoop implements Runnable {

    public static PlayerBlobs[] playerBlobs = new PlayerBlobs[5];

    public static void setupGameData() {

        for(int i = 0; i < playerBlobs.length; i++) {
            playerBlobs[i] = new PlayerBlobs();
        }

    }

    public static void startLoop() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        ScheduledFuture gameLoop = executor.scheduleWithFixedDelay(new GameLoop(), 0, 10, TimeUnit.MILLISECONDS);
    }

    @Override
    public void run() {

        //System.out.println("loop de loop");

        Gooey.paint(IdiotBox.frame.getGraphics());

        //Adjust player velocity
        if (KeyListener.forward) {
            playerBlobs[0].setVelocity(playerBlobs[0].getVelocity().add(180,0.01));
        }
        if (KeyListener.back) {
            playerBlobs[0].setVelocity(playerBlobs[0].getVelocity().add(0,0.01));
        }
        if (KeyListener.left) {
            playerBlobs[0].setVelocity(playerBlobs[0].getVelocity().add(270,0.01));
        }
        if (KeyListener.right) {
            playerBlobs[0].setVelocity(playerBlobs[0].getVelocity().add(90,0.01));
        }

        //Set player location
        int[] loc = playerBlobs[0].getLocation();
        double magX = playerBlobs[0].getVelocity().getMagX();
        double magY = playerBlobs[0].getVelocity().getMagY();
        int[] location = {loc[0] + (int)magX, loc[1] + (int)magY};
        playerBlobs[0].setLocation(location);
        //System.out.println(location[0] + ", " + location[1]);


    }
}
