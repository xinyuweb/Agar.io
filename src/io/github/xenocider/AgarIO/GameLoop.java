package io.github.xenocider.AgarIO;

import io.github.xenocider.AgarIO.SexyStuff.IdiotBox;
import io.github.xenocider.AgarIO.SexyStuff.TestOnlyGraphics;
import io.github.xenocider.AgarIO.entity.Food;
import io.github.xenocider.AgarIO.entity.PlayerBlobs;
import io.github.xenocider.AgarIO.listener.KeyListener;
import io.github.xenocider.AgarIO.references.Reference;

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
    boolean running;


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


        for(int i = 0; i < playerBlobs.length; i++) {
            //Eating food/players
            for (int f = 0; f < food.length; f++) {
                if (Math.abs(playerBlobs[i].getLocation()[0] - food[f].getLocation()[0]) < 50 && Math.abs(playerBlobs[i].getLocation()[1] - food[f].getLocation()[1]) < 50) {
                    playerBlobs[i].setMass(playerBlobs[i].getMass() + food[f].getMass());
                    food[f] = new Food();
                }
                double G = 6.67408 * Math.pow(10, -11);

                double gravity = G * playerBlobs[i].getMass() / Math.pow(
                        Math.sqrt(Math.pow((playerBlobs[i].getLocation()[0] - food[f].getLocation()[0]), 2) + Math.pow((playerBlobs[i].getLocation()[1] - food[f].getLocation()[1]), 2)),
                        2);
                double angle = Math.atan2((playerBlobs[i].getLocation()[0] - food[f].getLocation()[0]), (playerBlobs[i].getLocation()[1] - food[f].getLocation()[1])) * 180 / Math.PI;
                System.out.println("Player blob:" + i + " pulling food:" + f + " at " + angle + " with " + gravity * Reference.gravMultiplier);
                food[f].setVelocity(food[f].getVelocity().add(angle, gravity * Reference.gravMultiplier));
            }
            for (int p = 0; p < playerBlobs.length; p++) {
                if (i!=p){
                    if (Math.abs(playerBlobs[i].getLocation()[0] - playerBlobs[p].getLocation()[0]) < 50 && Math.abs(playerBlobs[i].getLocation()[1] - playerBlobs[p].getLocation()[1]) < 50) {
                        if (playerBlobs[i].getMass() > playerBlobs[p].getMass() * 1.5) {
                            playerBlobs[i].setMass(playerBlobs[i].getMass() + playerBlobs[p].getMass());
                            playerBlobs[p] = new PlayerBlobs();
                        }
                    }
                    //TODO gravity
                    double G = 6.67408 * Math.pow(10, -11);

                    double gravity = G * playerBlobs[i].getMass() / Math.pow(
                            Math.sqrt(Math.pow((playerBlobs[i].getLocation()[0] - playerBlobs[p].getLocation()[0]), 2) + Math.pow((playerBlobs[i].getLocation()[1] - playerBlobs[p].getLocation()[1]), 2)),
                            2);
                    double angle = Math.atan2((playerBlobs[i].getLocation()[0] - playerBlobs[p].getLocation()[0]), (playerBlobs[i].getLocation()[1] - playerBlobs[p].getLocation()[1])) * 180 / Math.PI;
                    System.out.println("Player blob:" + i + " pulling player blob:" + p + " at " + angle + " with " + gravity * Reference.gravMultiplier);
                    playerBlobs[p].setVelocity(playerBlobs[p].getVelocity().add(angle, gravity * Reference.gravMultiplier));
                }
            }
        }


        //Set blobs locations
        for(int i = 0; i < playerBlobs.length; i++) {
            int[] loc = playerBlobs[i].getLocation();
            double magX = playerBlobs[i].getVelocity().getMagX();
            double magY = playerBlobs[i].getVelocity().getMagY();
            int[] location = {loc[0] + (int)magX, loc[1] + (int)magY};
            playerBlobs[i].setLocation(location);
        }
        for(int f = 0; f < food.length; f++) {
            int[] loc = food[f].getLocation();
            double magX = food[f].getVelocity().getMagX();
            double magY = food[f].getVelocity().getMagY();
            int[] location = {loc[0] + (int)magX, loc[1] + (int)magY};
            food[f].setLocation(location);
        }

        //Run graphics
        //Gooey.paint(IdiotBox.frame.getGraphics());
        TestOnlyGraphics.paint(IdiotBox.frame.getGraphics());

    }
}
