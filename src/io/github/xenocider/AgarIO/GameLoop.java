package io.github.xenocider.AgarIO;

import io.github.xenocider.AgarIO.SexyStuff.Gooey;
import io.github.xenocider.AgarIO.SexyStuff.IdiotBox;
import io.github.xenocider.AgarIO.entity.Food;
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
    public static Food[] food = new Food[10];

    public static void setupGameData() {

        for(int i = 0; i < playerBlobs.length; i++) {
            playerBlobs[i] = new PlayerBlobs();
        }
        for(int i = 0; i < food.length; i++) {
            food[i] = new Food();
        }

    }

    public static void startLoop() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        ScheduledFuture gameLoop = executor.scheduleWithFixedDelay(new GameLoop(), 0, 30, TimeUnit.MILLISECONDS);
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

        //TODO AI moving

        //Set blobs locations
        for(int i = 0; i < playerBlobs.length; i++) {
            int[] loc = playerBlobs[i].getLocation();
            double magX = playerBlobs[i].getVelocity().getMagX();
            double magY = playerBlobs[i].getVelocity().getMagY();
            int[] location = {loc[0] + (int)magX, loc[1] + (int)magY};
            playerBlobs[i].setLocation(location);
        }

        //Eating food/players
        for(int i = 0; i < playerBlobs.length; i++) {
            for (int f = 0; f < food.length; f++) {
                if (Math.abs(playerBlobs[i].getLocation()[0] - food[f].getLocation()[0]) < 50 && Math.abs(playerBlobs[i].getLocation()[1] - food[f].getLocation()[1]) < 50) {
                    playerBlobs[i].setMass(playerBlobs[i].getMass() + food[f].getMass());
                    food[f] = new Food();
                }
            }
            for (int p = 0; p < playerBlobs.length; p++) {
                if (Math.abs(playerBlobs[i].getLocation()[0] - playerBlobs[p].getLocation()[0]) < 50 && Math.abs(playerBlobs[i].getLocation()[1] - playerBlobs[p].getLocation()[1]) < 50) {
                    if (playerBlobs[i].getMass() > playerBlobs[p].getMass()*1.5) {
                        playerBlobs[i].setMass(playerBlobs[i].getMass() + playerBlobs[p].getMass());
                        playerBlobs[p] = new PlayerBlobs();
                    }
                }
            }
        }

        //TODO gravity
        


    }
}
