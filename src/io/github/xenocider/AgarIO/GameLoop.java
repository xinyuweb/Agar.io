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
import java.util.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by ict11 on 2015-09-30.
 */
public class GameLoop implements Runnable {

    public static List<PlayerBlobs> playerBlobs = new ArrayList<PlayerBlobs>();
    public static Food[] food = new Food[10];
    boolean running;


    public static void setupGameData() {

        for(int i = 0; i < Reference.playersMax; i++) {
            playerBlobs.add(new PlayerBlobs(i));
            System.out.println(i);
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
        for(int i = 0; i < playerBlobs.size(); i++) {
            for (int f = 0; f < food.length; f++) {
                if (Math.sqrt(Math.pow((playerBlobs.get(i).getLocation()[0] - food[f].getLocation()[0]), 2) + (Math.pow((playerBlobs.get(i).getLocation()[1] - food[f].getLocation()[1]), 2))) < playerBlobs.get(i).getMass()*5) {
                    playerBlobs.get(i).setMass(playerBlobs.get(i).getMass() + food[f].getMass());
                    food[f] = new Food();
                }
                if (Reference.gravityOn) {
                    double G = 6.67408 * Math.pow(10, -11);

                    double gravity = G * playerBlobs.get(i).getMass() / Math.pow(
                            Math.sqrt(Math.pow((playerBlobs.get(i).getLocation()[0] - food[f].getLocation()[0]), 2) + Math.pow((playerBlobs.get(i).getLocation()[1] - food[f].getLocation()[1]), 2)),
                            2);
                    double angle = Math.atan2((playerBlobs.get(i).getLocation()[0] - food[f].getLocation()[0]), (playerBlobs.get(i).getLocation()[1] - food[f].getLocation()[1])) * 180 / Math.PI;
                    //System.out.println("Player blob:" + i + " pulling food:" + f + " at " + angle + " with " + gravity * Reference.gravMultiplier);
                    food[f].velocity.add(angle, gravity * Reference.gravMultiplier);
                    if (gravity*Reference.gravMultiplier > Reference.fricitonLimit) {
                        food[f].friction = false;
                    }
                }
            }
            for (int p = 0; p < playerBlobs.size(); p++) {
                if (i!=p && playerBlobs.get(i).getID() != playerBlobs.get(p).getID()){
                    if (Math.sqrt(Math.pow((playerBlobs.get(i).getLocation()[0] - playerBlobs.get(p).getLocation()[0]), 2) + (Math.pow((playerBlobs.get(i).getLocation()[1]- playerBlobs.get(p).getLocation()[1]), 2))) < playerBlobs.get(i).getMass()*5) {
                        if (playerBlobs.get(i).getMass() > playerBlobs.get(p).getMass() * 1.5) {
                            playerBlobs.get(i).setMass(playerBlobs.get(i).getMass() + playerBlobs.get(p).getMass());
                            playerBlobs.add(new PlayerBlobs(playerBlobs.get(p).getID()));
                            playerBlobs.remove(p);
                        }
                    }
                    if (Reference.gravityOn) {
                        double G = 6.67408 * Math.pow(10, -11);

                        double gravity = G * playerBlobs.get(i).getMass() / Math.pow(
                                Math.sqrt(Math.pow((playerBlobs.get(i).getLocation()[0] - playerBlobs.get(p).getLocation()[0]), 2) + Math.pow((playerBlobs.get(i).getLocation()[1] - playerBlobs.get(p).getLocation()[1]), 2)),
                                2);
                        double angle = Vector.getAngle(playerBlobs.get(i).getLocation()[0], playerBlobs.get(i).getLocation()[1], playerBlobs.get(p).getLocation()[0], playerBlobs.get(p).getLocation()[1]);
                        //System.out.println("Player blob:" + i + " pulling player blob:" + p + " at " + angle + " with " + gravity * Reference.gravMultiplier);
                        playerBlobs.get(p).velocity.add(angle, gravity * Reference.gravMultiplier);
                        if (gravity*Reference.gravMultiplier > Reference.fricitonLimit) {
                            playerBlobs.get(p).friction = false;
                        }
                    }
                }
            }
        }




        //Set blobs locations
        for(int i = 0; i < playerBlobs.size(); i++) {
            //Add friction
            if (playerBlobs.get(i).friction) {
                if (playerBlobs.get(i).velocity.magnitude > Reference.friction) {
                    playerBlobs.get(i).velocity.magnitude = playerBlobs.get(i).velocity.magnitude - Reference.friction;
                } else {
                    playerBlobs.get(i).velocity.magnitude = 0;
                }
            }
            if (playerBlobs.get(i).playerVelocity.magnitude > Reference.maxSpeed) {
                playerBlobs.get(i).playerVelocity.magnitude = Reference.maxSpeed;
            }
            int[] loc = playerBlobs.get(i).getLocation();
            double magX = playerBlobs.get(i).velocity.getMagX() + playerBlobs.get(i).playerVelocity.getMagX();
            double magY = playerBlobs.get(i).velocity.getMagY() + playerBlobs.get(i).playerVelocity.getMagY();
            int[] location = {loc[0] + (int)magX, loc[1] + (int)magY};
            playerBlobs.get(i).setLocation(location);
            //Boundaries
            if (playerBlobs.get(i).getLocation()[0] > Reference.mapSize) {
                playerBlobs.get(i).setLocation(Reference.mapSize, playerBlobs.get(i).getLocation()[1]);
                playerBlobs.get(i).velocity.magnitude = 0;
            }
            if (playerBlobs.get(i).getLocation()[1] > Reference.mapSize) {
                playerBlobs.get(i).setLocation(playerBlobs.get(i).getLocation()[0], Reference.mapSize);
                playerBlobs.get(i).velocity.magnitude = 0;
            }
            if (playerBlobs.get(i).getLocation()[0] < 0) {
                playerBlobs.get(i).setLocation(0, playerBlobs.get(i).getLocation()[1]);
                playerBlobs.get(i).velocity.magnitude = 0;
            }
            if (playerBlobs.get(i).getLocation()[1] < 0) {
                playerBlobs.get(i).setLocation(playerBlobs.get(i).getLocation()[0], 0);
                playerBlobs.get(i).velocity.magnitude = 0;
            }
            playerBlobs.get(i).friction=true;
        }
        for(int f = 0; f < food.length; f++) {
            //Add friction
            if (food[f].friction) {
                if (food[f].velocity.magnitude > Reference.friction) {
                    food[f].velocity.magnitude = food[f].velocity.magnitude - Reference.friction;
                } else {
                    food[f].velocity.magnitude = 0;
                }
            }
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
        for (int i = 0; i < playerBlobs.size(); i++) {
            if (playerBlobs.get(i).getID() != 0) {
                for (int p = 0; p < playerBlobs.size(); p++) {
                    if (playerBlobs.get(i).getMass() > playerBlobs.get(p).getMass() * 1.5) {
                        if (i != p && playerBlobs.get(i).getID() != playerBlobs.get(p).getID()) {
                            double distance = Math.sqrt(Math.pow(playerBlobs.get(i).getLocation()[0] - playerBlobs.get(p).getLocation()[0], 2) + Math.pow(playerBlobs.get(i).getLocation()[1] - playerBlobs.get(p).getLocation()[1], 2));
                            if (distance < closestDistance) {
                                closestDistance = distance;
                                closestPlayer = playerBlobs.get(p);
                            }
                        }

                    }
                }
                if (closestPlayer == null) {
                    for (int f = 0; f < food.length; f++) {
                        double distance = Math.sqrt(Math.pow(playerBlobs.get(i).getLocation()[0] - food[f].getLocation()[0], 2) + Math.pow(playerBlobs.get(i).getLocation()[1] - food[f].getLocation()[1], 2));
                        if (distance < closestDistance) {
                            closestDistance = distance;
                            closestFood = food[f];
                        }
                    }
                    double angle = Vector.getAngle(closestFood.getLocation()[0], closestFood.getLocation()[1], playerBlobs.get(i).getLocation()[0], playerBlobs.get(i).getLocation()[1]);
                    double mag = 0.1;
                    playerBlobs.get(i).playerVelocity.add(angle, mag);
                    playerBlobs.get(i).target = closestFood;
                }
                else {
                    double angle = Vector.getAngle(closestPlayer.getLocation()[0], closestPlayer.getLocation()[1], playerBlobs.get(i).getLocation()[0], playerBlobs.get(i).getLocation()[1]);
                    double mag = 0.1;
                    playerBlobs.get(i).playerVelocity.add(angle, mag);
                    playerBlobs.get(i).target = closestPlayer;
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
<<<<<<< HEAD
            for(int i = 0; i < playerBlobs.size(); i++) {
                if (playerBlobs.get(i).getID() == 0) {
                    playerBlobs.get(i).playerVelocity.add(angle, mag);
                }
                //System.out.println(playerBlobs[0].playerVelocity.magnitude);
            }
=======


            playerBlobs[0].playerVelocity.add(angle,mag);
            //System.out.println(playerBlobs[0].playerVelocity.magnitude);
>>>>>>> origin/master
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

        //SPLIT
        if (KeyListener.split) {
            List<Integer> blobs = new ArrayList<Integer>();
            for(int i = 0; i < playerBlobs.size(); i++) {
                if (playerBlobs.get(i).getID() == 0 && playerBlobs.get(i).getMass() > Reference.splitMin) {
                    blobs.add(i);
                }
            }
            for(int i = 0; i < blobs.size(); i++) {
                playerBlobs.get(blobs.get(i)).setMass(playerBlobs.get(blobs.get(i)).getMass()/2);
                playerBlobs.add(new PlayerBlobs(0, playerBlobs.get(blobs.get(i)).getMass(), playerBlobs.get(blobs.get(i)).getLocation()[0] + 12, playerBlobs.get(blobs.get(i)).getLocation()[1] + 12));
            }
            KeyListener.split = false;
        }



    }
}
