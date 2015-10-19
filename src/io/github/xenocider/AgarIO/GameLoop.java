package io.github.xenocider.AgarIO;

import io.github.xenocider.AgarIO.SexyStuff.IdiotBox;
import io.github.xenocider.AgarIO.SexyStuff.TestOnlyGraphics;
import io.github.xenocider.AgarIO.entity.Food;
import io.github.xenocider.AgarIO.entity.PlayerBlobs;
import io.github.xenocider.AgarIO.listener.KeyListener;
import io.github.xenocider.AgarIO.references.Reference;
import io.github.xenocider.AgarIO.util.Debug;
import io.github.xenocider.AgarIO.util.Vector;

import javax.swing.*;
import java.awt.*;
import java.sql.Ref;
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
            playerBlobs[i].setID(i);
            playerBlobs[i].setMass(2);
        }
        for(int i = 0; i < food.length; i++) {
            food[i] = new Food();
        }

    }

    public static void startLoop() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        ScheduledFuture gameLoop = executor.scheduleWithFixedDelay(new GameLoop(), 0, 100, TimeUnit.MILLISECONDS);


    }

    @Override
    public void run() {

        //System.out.println("loop de loop");

        //Adjust player velocity
        adjustPlayerVelocity();

        //TODO AI moving
        if (Reference.AIOn) {
            runAI();
        }

        //Eating food/players & setting gravity effects
        for(int i = 0; i < playerBlobs.length; i++) {
            for (int f = 0; f < food.length; f++) {
                if (Math.abs(playerBlobs[i].getLocation()[0] - food[f].getLocation()[0]) < 50 && Math.abs(playerBlobs[i].getLocation()[1] - food[f].getLocation()[1]) < 50) {
                    playerBlobs[i].setMass(playerBlobs[i].getMass() + food[f].getMass());
                    food[f] = new Food();
                }
                if (Reference.gravityOn) {
                    double G = 6.67408 * Math.pow(10, -11);

                    double gravity = G * playerBlobs[i].getMass() / Math.pow(
                            Math.sqrt(Math.pow((playerBlobs[i].getLocation()[0] - food[f].getLocation()[0]), 2) + Math.pow((playerBlobs[i].getLocation()[1] - food[f].getLocation()[1]), 2)),
                            2);
                    double angle = Math.atan2((playerBlobs[i].getLocation()[0] - food[f].getLocation()[0]), (playerBlobs[i].getLocation()[1] - food[f].getLocation()[1])) * 180 / Math.PI;
                    //System.out.println("Player blob:" + i + " pulling food:" + f + " at " + angle + " with " + gravity * Reference.gravMultiplier);
                    food[f].velocity.add(angle, gravity * Reference.gravMultiplier);
                }
            }
            for (int p = 0; p < playerBlobs.length; p++) {
                if (i!=p && playerBlobs[i].getID() != playerBlobs[p].getID()){
                    if (Math.abs(playerBlobs[i].getLocation()[0] - playerBlobs[p].getLocation()[0]) < 50 && Math.abs(playerBlobs[i].getLocation()[1] - playerBlobs[p].getLocation()[1]) < 50) {
                        if (playerBlobs[i].getMass() > playerBlobs[p].getMass() * 1.5) {
                            playerBlobs[i].setMass(playerBlobs[i].getMass() + playerBlobs[p].getMass());
                            playerBlobs[p] = new PlayerBlobs();
                        }
                    }
                    if (Reference.gravityOn) {
                        double G = 6.67408 * Math.pow(10, -11);

                        double gravity = G * playerBlobs[i].getMass() / Math.pow(
                                Math.sqrt(Math.pow((playerBlobs[i].getLocation()[0] - playerBlobs[p].getLocation()[0]), 2) + Math.pow((playerBlobs[i].getLocation()[1] - playerBlobs[p].getLocation()[1]), 2)),
                                2);
                        double angle = Vector.getAngle(playerBlobs[i].getLocation()[0], playerBlobs[i].getLocation()[1], playerBlobs[p].getLocation()[0], playerBlobs[p].getLocation()[1]);
                        //System.out.println("Player blob:" + i + " pulling player blob:" + p + " at " + angle + " with " + gravity * Reference.gravMultiplier);
                        playerBlobs[p].velocity.add(angle, gravity * Reference.gravMultiplier);
                    }
                }
            }
        }


        //Set blobs locations
        for(int i = 0; i < playerBlobs.length; i++) {
            if (playerBlobs[i].playerVelocity.magnitude > Reference.maxSpeed) {
                playerBlobs[i].playerVelocity.magnitude = Reference.maxSpeed;
            }
            int[] loc = playerBlobs[i].getLocation();
            double magX = playerBlobs[i].velocity.getMagX() + playerBlobs[i].playerVelocity.getMagX();
            double magY = playerBlobs[i].velocity.getMagY() + playerBlobs[i].playerVelocity.getMagY();
            int[] location = {loc[0] + (int)magX, loc[1] + (int)magY};
            playerBlobs[i].setLocation(location);
        }
        for(int f = 0; f < food.length; f++) {
            int[] loc = food[f].getLocation();
            double magX = food[f].velocity.getMagX();
            double magY = food[f].velocity.getMagY();
            int[] location = {loc[0] + (int)magX, loc[1] + (int)magY};
            food[f].setLocation(location);
        }
        //System.out.println(playerBlobs[0].getLocation()[0] + ", " + playerBlobs[0].getLocation()[1]);

        //Run graphics
        //Gooey.paint(IdiotBox.frame.getGraphics());
        TestOnlyGraphics.paint(IdiotBox.frame.getGraphics());
    }

    private void runAI() {
        double closestDistance = 1000000;
        PlayerBlobs closestPlayer = null;
        Food closestFood = null;
        for (int i = 0; i < playerBlobs.length; i++) {
            if (playerBlobs[i].getID() != 0) {
                for (int p = 0; p < playerBlobs.length; p++) {
                    if (playerBlobs[i].getMass() > playerBlobs[p].getMass() * 1.5) {
                        if (i != p && playerBlobs[i].getID() != playerBlobs[p].getID()) {
                            double distance = Math.sqrt(Math.pow(playerBlobs[i].getLocation()[0] - playerBlobs[p].getLocation()[0], 2) + Math.pow(playerBlobs[i].getLocation()[1] - playerBlobs[p].getLocation()[1], 2));
                            if (distance < closestDistance) {
                                closestDistance = distance;
                                closestPlayer = playerBlobs[p];
                            }
                        }

                    }
                }
                if (closestPlayer == null) {
                    for (int f = 0; f < food.length; f++) {
                        double distance = Math.sqrt(Math.pow(playerBlobs[i].getLocation()[0] - food[f].getLocation()[0], 2) + Math.pow(playerBlobs[i].getLocation()[1] - food[f].getLocation()[1], 2));
                        if (distance < closestDistance) {
                            closestDistance = distance;
                            closestFood = food[f];
                        }
                    }
                    double angle = Vector.getAngle(closestFood.getLocation()[0], closestFood.getLocation()[1], playerBlobs[i].getLocation()[0], playerBlobs[i].getLocation()[1]);
                    double mag = 0.1;
                    playerBlobs[i].playerVelocity.add(angle, mag);
                }
                else {
                    double angle = Vector.getAngle(closestPlayer.getLocation()[0], closestPlayer.getLocation()[1], playerBlobs[i].getLocation()[0], playerBlobs[i].getLocation()[1]);
                    double mag = 0.1;
                    playerBlobs[i].playerVelocity.add(angle, mag);
                }
            }
        }
    }

    private void adjustPlayerVelocity() {
        try {
            double mouseX = /*MouseInfo.getPointerInfo().getLocation().getX() - */IdiotBox.frame.getMousePosition().getX();
            double mouseY = /*MouseInfo.getPointerInfo().getLocation().getY() - */IdiotBox.frame.getMousePosition().getY();
            double centerX = IdiotBox.frame.getWidth() / 2;
            double centerY = IdiotBox.frame.getHeight() / 2;
            double angle = Vector.getAngle(mouseX, mouseY, centerX, centerY);
            double mag = Math.sqrt(Math.pow(mouseX - centerX, 2) + Math.pow(mouseY - centerY, 2));
            mag = mag * Reference.mouseMultiplier;

            if (mag > Reference.mouseMax) {
                mag = Reference.mouseMax;
            }

            playerBlobs[0].playerVelocity.add(angle,mag);
            //System.out.println(playerBlobs[0].playerVelocity.magnitude);
        }
        catch (Exception e) {
            //e.printStackTrace();
        }

/*
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
        */
    }
}
