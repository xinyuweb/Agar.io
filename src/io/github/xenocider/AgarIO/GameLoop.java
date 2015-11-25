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
import java.util.concurrent.*;

/**
 * Created by ict11 on 2015-09-30.
 */
public class GameLoop implements Runnable {

    public static List<PlayerBlobs> playerBlobs = new ArrayList<PlayerBlobs>();
    public static List<Food> food = new ArrayList<Food>();
    boolean running;


    public static void setupGameData() {

        for(int i = 0; i < Reference.playersMax; i++) {
            playerBlobs.add(new PlayerBlobs(i));
        }
        for(int i = 0; i < 10; i++) {
            food.add(new Food());
        }
        playerBlobs.get(0).setMass(55);

    }

    public static void startLoop() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        ScheduledFuture gameLoop = executor.scheduleWithFixedDelay(new GameLoop(), 0, 100, TimeUnit.MILLISECONDS);


    }

    @Override
    public void run() {
        if (Reference.playGame) {


            //System.out.println("loop de loop");

            //Adjust player velocity
            adjustPlayerVelocity();

            //TODO AI moving
            if (Reference.AIOn) {
                runAI();
            }

            //Eating food/players & setting gravity effects
            for (int i = 0; i < playerBlobs.size(); i++) {
                for (int f = 0; f < food.size(); f++) {
                    if (Reference.gravityOn) {
                        double G = 6.67408 * Math.pow(10, -11);

                        double gravity = G * playerBlobs.get(i).getMass() / Math.pow(
                                Math.sqrt(Math.pow((playerBlobs.get(i).getLocation()[0] - food.get(f).getLocation()[0]), 2) + Math.pow((playerBlobs.get(i).getLocation()[1] - food.get(f).getLocation()[1]), 2)),
                                2);
                        double angle = Math.atan2((playerBlobs.get(i).getLocation()[0] - food.get(f).getLocation()[0]), (playerBlobs.get(i).getLocation()[1] - food.get(f).getLocation()[1])) * 180 / Math.PI;
                        //System.out.println("Player blob:" + i + " pulling food:" + f + " at " + angle + " with " + gravity * Reference.gravMultiplier);
                        food.get(f).velocity.add(angle, gravity * Reference.gravMultiplier);
                        if (gravity * Reference.gravMultiplier > Reference.fricitonLimit) {
                            food.get(f).friction = false;
                        }
                    }
                }
                for (int p = 0; p < playerBlobs.size(); p++) {
                    if (i != p) {
                        if (playerBlobs.get(i).getID() != playerBlobs.get(p).getID()) {
                            if (Reference.gravityOn) {
                                double G = 6.67408 * Math.pow(10, -11);

                                try {
                                    double gravity = G * playerBlobs.get(i).getMass() / Math.pow(
                                            Math.sqrt(Math.pow((playerBlobs.get(i).getLocation()[0] - playerBlobs.get(p).getLocation()[0]), 2) + Math.pow((playerBlobs.get(i).getLocation()[1] - playerBlobs.get(p).getLocation()[1]), 2)),
                                            2);


                                    double angle = Vector.getAngle(playerBlobs.get(i).getLocation()[0], playerBlobs.get(i).getLocation()[1], playerBlobs.get(p).getLocation()[0], playerBlobs.get(p).getLocation()[1]);
                                    //System.out.println("Player blob:" + i + " pulling player blob:" + p + " at " + angle + " with " + gravity * Reference.gravMultiplier);

                                    playerBlobs.get(p).velocity.add(angle, gravity * Reference.gravMultiplier);

                                    if (gravity * Reference.gravMultiplier > Reference.fricitonLimit) {
                                        playerBlobs.get(p).friction = false;
                                        //System.out.println("UnFrictioned! = " + p);

                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            if (Reference.gravityOn && Reference.selfGravity) {
                                double G = 6.67408 * Math.pow(10, -11);

                                try {
                                    double gravity = G * playerBlobs.get(i).getMass() / Math.pow(
                                            Math.sqrt(Math.pow((playerBlobs.get(i).getLocation()[0] - playerBlobs.get(p).getLocation()[0]), 2) + Math.pow((playerBlobs.get(i).getLocation()[1] - playerBlobs.get(p).getLocation()[1]), 2)),
                                            2);


                                    double angle = Vector.getAngle(playerBlobs.get(i).getLocation()[0], playerBlobs.get(i).getLocation()[1], playerBlobs.get(p).getLocation()[0], playerBlobs.get(p).getLocation()[1]);
                                    //System.out.println("Player blob:" + i + " pulling player blob:" + p + " at " + angle + " with " + gravity * Reference.gravMultiplier);

                                    gravity = gravity * Reference.gravMultiplier;
                                    if (gravity > 2) {
                                        gravity = 2;
                                    }
                                    System.out.println(gravity);
                                    playerBlobs.get(p).velocity.add(angle, gravity);

                                    if (gravity > Reference.fricitonLimit) {
                                        playerBlobs.get(p).friction = true;
                                        //System.out.println("UnFrictioned! = " + p);

                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }


            //Set blobs locations
            for (int i = 0; i < playerBlobs.size(); i++) {

                if (playerBlobs.get(i).friction) {
                    if (playerBlobs.get(i).velocity.magnitude > Reference.friction) {
                        playerBlobs.get(i).velocity.magnitude = playerBlobs.get(i).velocity.magnitude - Reference.friction;
                    } else {
                        playerBlobs.get(i).velocity.magnitude = 0;
                        //System.out.println("Frictioned! = " + i);
                    }
                } else {
                    playerBlobs.get(i).friction = true;
                }
                if (playerBlobs.get(i).playerVelocity.magnitude > 20 * Math.pow(playerBlobs.get(i).getMass() + 5, -1) + Reference.maxSpeed) {
                    playerBlobs.get(i).playerVelocity.magnitude = 20 * Math.pow(playerBlobs.get(i).getMass() + 5, -1) + Reference.maxSpeed;
                }
                // rejoining stuff
                if (playerBlobs.get(i).rejoinTime > 0 && playerBlobs.get(i).getTwin().size() > 0) {
                    playerBlobs.get(i).rejoinTime = playerBlobs.get(i).rejoinTime - 1;
                } else if (playerBlobs.get(i).rejoinTime == 0 && playerBlobs.get(i).getTwin().size() > 0) {
                    playerBlobs.get(i).rejoin2();
                }
                try {
                    playerBlobs.get(i);
                } catch (Exception e) {
                    System.out.println("Strange Error");
                    break;
                }


                if (playerBlobs.get(i).playerVelocity.magnitude > 20 * Math.pow(playerBlobs.get(i).getMass() + 5, -1) + Reference.maxSpeed) {
                    playerBlobs.get(i).playerVelocity.magnitude = 20 * Math.pow(playerBlobs.get(i).getMass() + 5, -1) + Reference.maxSpeed;
                }
                double[] loc = playerBlobs.get(i).getLocation();
                double magX = playerBlobs.get(i).velocity.getMagX() + playerBlobs.get(i).playerVelocity.getMagX();
                double magY = playerBlobs.get(i).velocity.getMagY() + playerBlobs.get(i).playerVelocity.getMagY();
                double[] location = {loc[0] + magX, loc[1] + magY};
                playerBlobs.get(i).setLocation(location);

                //No touchy touchy self
                for (int p = 0; p < playerBlobs.size(); p++) {
                    if (playerBlobs.get(i).getID() == playerBlobs.get(p).getID() && i != p) {
                        //Vector v1 = new Vector(Vector.getAngle(playerBlobs.get(p).getLocation()[0], playerBlobs.get(p).getLocation()[1], playerBlobs.get(0).getLocation()[0], playerBlobs.get(0).getLocation()[1]), playerBlobs.get(0).getMass() * Reference.zoom);
                        Vector v2 = new Vector(Vector.getAngle(playerBlobs.get(i).getLocation()[0], playerBlobs.get(i).getLocation()[1], playerBlobs.get(p).getLocation()[0], playerBlobs.get(p).getLocation()[1]), playerBlobs.get(p).getMass() * Reference.zoom);
                        Vector v1 = new Vector(Vector.getAngle(playerBlobs.get(p).getLocation()[0], playerBlobs.get(p).getLocation()[1], playerBlobs.get(i).getLocation()[0], playerBlobs.get(i).getLocation()[1]), playerBlobs.get(i).getMass() * Reference.zoom);
                        if (Math.sqrt(Math.pow((playerBlobs.get(i).getLocation()[0] - playerBlobs.get(p).getLocation()[0]), 2) + (Math.pow((playerBlobs.get(i).getLocation()[1] - playerBlobs.get(p).getLocation()[1]), 2))) - playerBlobs.get(p).getMass() * Reference.zoom - playerBlobs.get(i).getMass() * Reference.zoom < 0) {
                            if (playerBlobs.get(i).rejoinTime > 0 && playerBlobs.get(p).rejoinTime > 0) {
                                double a = Vector.getAngle(playerBlobs.get(i).getLocation()[0],playerBlobs.get(i).getLocation()[1],playerBlobs.get(p).getLocation()[0],playerBlobs.get(p).getLocation()[1]);
                                Vector v = new Vector(a,(playerBlobs.get(i).getMass()+playerBlobs.get(p).getMass())/2);
                                playerBlobs.get(i).setLocation(playerBlobs.get(i).getLocation()[0] + v.getMagX()/Reference.zoom, playerBlobs.get(i).getLocation()[1] + v.getMagY()/Reference.zoom);
                                playerBlobs.get(p).setLocation(playerBlobs.get(p).getLocation()[0] - v.getMagX()/Reference.zoom, playerBlobs.get(p).getLocation()[1] - v.getMagY()/Reference.zoom);
                            }
                        }
                    }
                }

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
            }
            for (int f = 0; f < food.size(); f++) {
                //Add friction
                if (food.get(f).friction) {
                    if (food.get(f).velocity.magnitude > Reference.friction) {
                        food.get(f).velocity.magnitude = food.get(f).velocity.magnitude - Reference.friction;
                    } else {
                        food.get(f).velocity.magnitude = 0;
                    }
                }
                double[] loc = food.get(f).getLocation();
                double magX = food.get(f).velocity.getMagX();
                double magY = food.get(f).velocity.getMagY();
                double[] location = {loc[0] + magX, loc[1] + magY};
                food.get(f).setLocation(location);
                //Boundaries
                if (food.get(f).getLocation()[0] > Reference.mapSize) {
                    food.get(f).setLocation(Reference.mapSize, food.get(f).getLocation()[1]);
                    food.get(f).velocity.magnitude = 0;
                }
                if (food.get(f).getLocation()[1] > Reference.mapSize) {
                    food.get(f).setLocation(food.get(f).getLocation()[0], Reference.mapSize);
                    food.get(f).velocity.magnitude = 0;
                }
                if (food.get(f).getLocation()[0] < 0) {
                    food.get(f).setLocation(0, food.get(f).getLocation()[1]);
                    food.get(f).velocity.magnitude = 0;
                }
                if (food.get(f).getLocation()[1] < 0) {
                    food.get(f).setLocation(food.get(f).getLocation()[0], 0);
                    food.get(f).velocity.magnitude = 0;
                }
            }
            double totalM = 0;
            int children = 0;
            PlayerBlobs player = null;
            for (int i = 0; i < playerBlobs.size(); i++) {
                for (int f = 0; f < food.size(); f++) {
                    if (Math.sqrt(Math.pow((playerBlobs.get(i).getLocation()[0] - food.get(f).getLocation()[0]), 2) + (Math.pow((playerBlobs.get(i).getLocation()[1] - food.get(f).getLocation()[1]), 2))) < playerBlobs.get(i).getMass() * Reference.zoom) {
                        playerBlobs.get(i).setMass(playerBlobs.get(i).getMass() + food.get(f).getMass());
                        food.remove(f);
                        food.add(new Food());
                    }
                }
                for (int p = 0; p < playerBlobs.size(); p++) {
                    if (Math.sqrt(Math.pow((playerBlobs.get(i).getLocation()[0] - playerBlobs.get(p).getLocation()[0]), 2) + (Math.pow((playerBlobs.get(i).getLocation()[1] - playerBlobs.get(p).getLocation()[1]), 2))) < playerBlobs.get(i).getMass() * Reference.zoom) {
                        if (i != p) {
                            if (playerBlobs.get(i).getID() != playerBlobs.get(p).getID()) {
                                if (playerBlobs.get(i).getMass() > playerBlobs.get(p).getMass() * 1.5) {
                                    playerBlobs.get(i).setMass(playerBlobs.get(i).getMass() + playerBlobs.get(p).getMass());
                                    int quantity = 0;
                                    for (int q = 0; q < playerBlobs.size(); q++) {
                                        if (playerBlobs.get(q).getID() == playerBlobs.get(p).getID()) {
                                            quantity = quantity + 1;
                                        }
                                    }
                                    if (quantity == 1) {
                                        playerBlobs.add(new PlayerBlobs(playerBlobs.get(p).getID()));
                                    }
                                    playerBlobs.remove(p);
                                }
                            }
                        }
                    }
                }
                if (playerBlobs.get(i).getID() == 0) {
                    totalM = totalM + playerBlobs.get(i).getMass();
                    children = children + 1;
                    player = playerBlobs.get(i);
                }
            }
            if (children == 1 && totalM > Reference.critMass) {
                player.explode();
            }

            //Debug.debug(playerBlobs.get(0));

            //System.out.println(playerBlobs[0].getLocation()[0] + ", " + playerBlobs[0].getLocation()[1]);
            //Run graphics
            //Gooey.paint(IdiotBox.frame.getGraphics());
            TestOnlyGraphics.paint(IdiotBox.frame.getGraphics());
        }
    }

    private void runAI() {
        double closestDistance = 1000000;
        PlayerBlobs closestPlayer = null;
        Food closestFood = null;
        for (int i = 0; i < playerBlobs.size(); i++) {
            if (playerBlobs.get(i).getID() > 0) {
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
                    for (int f = 0; f < food.size(); f++) {
                        double distance = Math.sqrt(Math.pow(playerBlobs.get(i).getLocation()[0] - food.get(f).getLocation()[0], 2) + Math.pow(playerBlobs.get(i).getLocation()[1] - food.get(f).getLocation()[1], 2));
                        if (distance < closestDistance) {
                            closestDistance = distance;
                            closestFood = food.get(f);
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
            //double angle = Vector.getAngle(mouseX, mouseY, centerX, centerY);
            //double mag = Math.sqrt(Math.pow(mouseX - centerX, 2) + Math.pow(mouseY - centerY, 2));

            for(int i = 0; i < playerBlobs.size(); i++) {

                if (playerBlobs.get(i).getID() == 0) {
                    double angle = Vector.getAngle(mouseX,mouseY,playerBlobs.get(i).getLocation()[0],playerBlobs.get(i).getLocation()[1]);
                    double mag = Math.sqrt(Math.pow(mouseX - playerBlobs.get(i).getLocation()[0], 2) + Math.pow(mouseY - playerBlobs.get(i).getLocation()[1], 2));
                    mag = mag * Reference.mouseMultiplier;

                    if (mag > Reference.mouseMax) {
                        mag = Reference.mouseMax;
                    }
                    playerBlobs.get(i).playerVelocity.add(angle, mag);
                }
                //System.out.println(playerBlobs[0].playerVelocity.magnitude);
            }
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
            double totalM = 0;
            for(int i = 0; i < playerBlobs.size(); i++) {
                if (playerBlobs.get(i).getID() == 0 && playerBlobs.get(i).getMass() >= Reference.splitMin) {
                    blobs.add(i);
                }
                totalM = totalM + playerBlobs.get(i).getMass();
            }
            if (totalM < Reference.critMass) {
                for (int i = 0; i < blobs.size(); i++) {
                    PlayerBlobs p1 = playerBlobs.get(blobs.get(i));
                    p1.setMass(p1.getMass() / 2);
                    Vector v1 = new Vector(p1.playerVelocity.direction, p1.getMass() * Reference.zoom);
                    Vector v2 = new Vector(p1.playerVelocity.direction, p1.getMass() * Reference.zoom);
                    System.out.println(v2.getMagX() + ", " + v2.getMagY());
                    GameLoop.playerBlobs.add(new PlayerBlobs(0, p1.getMass(), p1.getLocation()[0] + v2.getMagX(), p1.getLocation()[1] + v2.getMagY(), p1.getTwin()));
                    p1.setLocation(p1.getLocation()[0] - v1.getMagX(), p1.getLocation()[1] - v1.getMagY());
                    PlayerBlobs p2 = playerBlobs.get(playerBlobs.size() - 1);
                    p2.velocity.magnitude = Reference.splitSpeed;
                    p2.velocity.direction = p1.playerVelocity.direction;

                    System.out.println("Splitting " + p1.toString() + " and " + p2.toString());

                    p1.addTwin(p2);
                    System.out.println(p1.getTwin().size());
                    p2.addTwin(p1);
                    System.out.println(p1.getTwin().size());
                }
                if (blobs.size() > 0) {
                    for (int i = 0; i < playerBlobs.size(); i++) {
                        playerBlobs.get(i).restartRejoin();
                    }
                }
            }
            KeyListener.split = false;
        }



    }

}
