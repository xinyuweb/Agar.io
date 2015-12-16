package io.github.xenocider.AgarIO;

import io.github.xenocider.AgarIO.SexyStuff.IdiotBox;
import io.github.xenocider.AgarIO.SexyStuff.TestOnlyGraphics;
import io.github.xenocider.AgarIO.entity.Food;
import io.github.xenocider.AgarIO.entity.PlayerBlobs;
import io.github.xenocider.AgarIO.listener.KeyListener;
import io.github.xenocider.AgarIO.references.Reference;
import io.github.xenocider.AgarIO.util.Vector;

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

    public static List<Integer> scoreboard = new ArrayList<Integer>();

    public static List<ArrayList<Vector>> velocities = new ArrayList<ArrayList<Vector>>();

    public static List<Integer> idRelations = new ArrayList<Integer>();


    public static void setupGameData() {

        System.out.println("            Creating Players & Initializing Scoreboard...");
        for(int i = 0; i < Reference.playersMax; i++) {
            playerBlobs.add(new PlayerBlobs(i));
            scoreboard.add(0);
        }
        System.out.println("            Creation & Initialization Complete!");
        System.out.println("            Creating Food...");
        for(int i = 0; i < Reference.foodMin; i++) {
            food.add(new Food());
        }
        System.out.println("            Creation Complete!");
        playerBlobs.get(0).ai = false;
    }

    public static void startLoop() {
        System.out.println("        Creating And Starting Game Loop...");
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        ScheduledFuture gameLoop = executor.scheduleWithFixedDelay(new GameLoop(), 0, 100, TimeUnit.MILLISECONDS);
        System.out.println("        Game Loop Successfully Created And Started!");


    }

    @Override
    public void run() {
        if (Reference.playGame) {

            if (Reference.servermode) {

            }


            if (food.size() < Reference.foodMin) {
                System.out.println("Adding Food");
                food.add(new Food());
            }
            if (food.size() > Reference.foodMax) {
                System.out.println("Removing Food");
                food.remove(0);
            }
            //System.out.println("loop de loop");

            //Adjust player velocity
            if (Reference.gameloopDebug) {System.out.println("Adjusting Player's Velocity...");}
            adjustPlayerVelocity();
            if (Reference.gameloopDebug) {System.out.println("Adjusted Player's Velocity Successfully!");}

            if (Reference.AIOn) {
                if (Reference.gameloopDebug) {System.out.println("Running AI...");}
                runAI();
                if (Reference.gameloopDebug) {System.out.println("AI Running Completed!");}
            }
            if (Reference.gameloopDebug) {System.out.println("Adjusting Gravity...");}
            //setting gravity effects
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

                                    playerBlobs.get(p).velocity.add(angle, gravity * Reference.gravMultiplier * 10);

                                    if (gravity * Reference.gravMultiplier * 10> Reference.fricitonLimit) {
                                        playerBlobs.get(p).friction = false;
                                        //System.out.println(i + " on " + p + " "+ gravity*Reference.gravMultiplier);

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
            if (Reference.gameloopDebug) {System.out.println("Adjusting Gravity Completed!");}

            if (Reference.gameloopDebug) {System.out.println("Setting Blobs Locations...");}
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
                //Mass slowing you down
                if (playerBlobs.get(i).playerVelocity.magnitude > 30 * Math.pow(playerBlobs.get(i).getMass() + 2, -1)+1) {
                    playerBlobs.get(i).playerVelocity.magnitude = 30 * Math.pow(playerBlobs.get(i).getMass() + 2, -1)+1;
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
            if (Reference.gameloopDebug) {System.out.println("Blobs Locations Set!");}
            if (Reference.gameloopDebug) {System.out.println("Setting Food Locations...");}
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
            if (Reference.gameloopDebug) {System.out.println("Food Locations Set");}

            if (Reference.gameloopDebug) {System.out.println("Checking To Eat Food & Players...");}
            for (int i = 0; i < playerBlobs.size(); i++) {
                for (int f = 0; f < food.size(); f++) {
                    if (Math.sqrt(Math.pow((playerBlobs.get(i).getLocation()[0] - food.get(f).getLocation()[0]), 2) + (Math.pow((playerBlobs.get(i).getLocation()[1] - food.get(f).getLocation()[1]), 2))) < playerBlobs.get(i).getMass() * Reference.zoom) {
                        if (playerBlobs.get(i).getMass() > food.get(f).getMass()) {
                            playerBlobs.get(i).setMass(playerBlobs.get(i).getMass() + food.get(f).getMass());
                            food.remove(f);
                        }
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
                                        PlayerBlobs player = new PlayerBlobs(playerBlobs.get(p).getID());
                                        playerBlobs.add(player);
                                        player.ai = playerBlobs.get(p).ai;
                                        scoreboard.set(playerBlobs.get(p).getID(),0);
                                    }
                                    playerBlobs.remove(p);
                                }
                            }
                        }
                    }
                }

            }
            if (Reference.gameloopDebug) {System.out.println("Check Completed!");}
            if (Reference.gameloopDebug) {System.out.println("Checking For Critical Mass...");}
            for (int id = 0; id < Reference.playersMax; id ++) {
                double totalM = 0;
                int children = 0;
                PlayerBlobs player = null;
                for (int i = 0; i < playerBlobs.size(); i++) {
                    if (playerBlobs.get(i).getID() == id) {
                        totalM = totalM + playerBlobs.get(i).getMass();
                        children = children + 1;
                        player = playerBlobs.get(i);
                    }
                    if (children == 1 && totalM > Reference.critMass) {
                            player.explode();
                            scoreboard.set(id,scoreboard.get(id)+1);
                        break;
                    }
                }
            }
            if (Reference.gameloopDebug) {System.out.println("Checking Completed!");}


            //Debug.debug(playerBlobs.get(0));

            //System.out.println(playerBlobs[0].getLocation()[0] + ", " + playerBlobs[0].getLocation()[1]);
            //Run graphics
            //Gooey.paint(IdiotBox.frame.getGraphics());
            if (Reference.gameloopDebug) {System.out.println("Drawing...");}
            TestOnlyGraphics.paint(IdiotBox.frame.getGraphics());
            if (Reference.gameloopDebug) {System.out.println("Drawing Completed!");}
        }
    }

    private void runAI() {
        if (Reference.aiDebug) {System.out.println("Setting Up Variables...");}
        double closestPreyDis = Reference.preyDis;
        double closestEnemyDis = Reference.enemyDis;
        PlayerBlobs closestPlayer = null;
        PlayerBlobs closestEnemy = null;
        Food closestFood = null;
        if (Reference.aiDebug) {System.out.println("Variables Setup!");}
        if (Reference.aiDebug) {System.out.println("Running AI Loops...");}
        for (int i = 0; i < playerBlobs.size(); i++) {
            closestPreyDis = Reference.preyDis;
            closestEnemyDis = Reference.enemyDis;
            closestPlayer = null;
            closestEnemy = null;
            closestFood = null;
            if (playerBlobs.get(i).ai = true) {
                if (Reference.aiDebug) {System.out.println("Finding Closest Eatable Player & Closest Enemy Player...");}
                for (int p = 0; p < playerBlobs.size(); p++) {
                        if (i != p && playerBlobs.get(i).getID() != playerBlobs.get(p).getID()) {
                            double distance = Math.sqrt(Math.pow(playerBlobs.get(i).getLocation()[0] - playerBlobs.get(p).getLocation()[0], 2) + Math.pow(playerBlobs.get(i).getLocation()[1] - playerBlobs.get(p).getLocation()[1], 2));
                            distance = distance - playerBlobs.get(i).getMass()*Reference.zoom - playerBlobs.get(p).getMass()*Reference.zoom;
                            if (distance < closestPreyDis && playerBlobs.get(i).getMass() > playerBlobs.get(p).getMass() * 1.5) {
                                closestPreyDis = distance;
                                closestPlayer = playerBlobs.get(p);
                            }
                            if (distance < closestEnemyDis && playerBlobs.get(i).getMass() *1.5 < playerBlobs.get(p).getMass()) {
                                closestEnemyDis = distance;
                                closestEnemy = playerBlobs.get(p);
                            }
                        }
                }
                if (Reference.aiDebug) {System.out.println("Found Closest Players!");}
                if (Reference.aiDebug) {
                    System.out.println("Closest Players To Player " + i + ") Eatable: " + closestPlayer + " " + closestPreyDis + " away   Enemy: " + closestEnemy + " " + closestEnemyDis + " away");
                }
                if (closestPlayer == null && closestEnemy == null) {
                    if (Reference.aiDebug) {
                         System.out.println("Player " + i + " AI On Food");
                    }
                    for (int f = 0; f < food.size(); f++) {
                        double distance = Math.sqrt(Math.pow(playerBlobs.get(i).getLocation()[0] - food.get(f).getLocation()[0], 2) + Math.pow(playerBlobs.get(i).getLocation()[1] - food.get(f).getLocation()[1], 2));
                        if (distance < closestPreyDis) {
                            closestPreyDis = distance;
                            closestFood = food.get(f);
                        }
                    }
                    if (closestFood != null) {
                        double angle = Vector.getAngle(closestFood.getLocation()[0], closestFood.getLocation()[1], playerBlobs.get(i).getLocation()[0], playerBlobs.get(i).getLocation()[1]);
                        double mag = Reference.enemyMag;
                        playerBlobs.get(i).playerVelocity.add(angle, mag);
                        playerBlobs.get(i).target = closestFood;
                    }
                }
                else if (closestEnemy != null){
                    if (closestPlayer == null || closestEnemyDis < closestPreyDis) {
                        if (Reference.aiDebug) {
                            System.out.println("Player " + i + " AI On Fear from Player " + closestEnemy.getID());
                        }
                        double angle = Vector.getAngle(closestEnemy.getLocation()[0], closestEnemy.getLocation()[1], playerBlobs.get(i).getLocation()[0], playerBlobs.get(i).getLocation()[1]);
                        double mag = Reference.enemyMag;
                        playerBlobs.get(i).playerVelocity.add(angle - 180, mag);
                    }
                }
                else if (closestPlayer != null){
                    if (Reference.aiDebug) {
                        System.out.println("Player " + i + " AI On Kill to Player " + closestPlayer.getID());
                    }
                    double angle = Vector.getAngle(closestPlayer.getLocation()[0], closestPlayer.getLocation()[1], playerBlobs.get(i).getLocation()[0], playerBlobs.get(i).getLocation()[1]);
                    double mag = Reference.enemyMag;
                    playerBlobs.get(i).playerVelocity.add(angle, mag);
                    playerBlobs.get(i).target = closestPlayer;
                }
            }
        }
        if (Reference.aiDebug) {System.out.println("AI Loops Ran Successfully!");}
    }

    private void adjustPlayerVelocity() {
        try {
            double xOffset = 0;
            double yOffset = 0;
            double count = 0;
            double xTotal = 0;
            double yTotal = 0;
            for (int i = 0; i < GameLoop.playerBlobs.size(); i++) {
                if(GameLoop.playerBlobs.get(i).getID()==0) {
                    xTotal = xTotal + GameLoop.playerBlobs.get(i).getLocation()[0];
                    yTotal = yTotal + GameLoop.playerBlobs.get(i).getLocation()[1];
                    count = count + 1;
                }
            }
            xOffset = xTotal/count-IdiotBox.frame.getWidth() / 2;
            yOffset = yTotal/count-IdiotBox.frame.getHeight() / 2;

            double mouseX = /*MouseInfo.getPointerInfo().getLocation().getX() - */IdiotBox.frame.getMousePosition().getX();
            double mouseY = /*MouseInfo.getPointerInfo().getLocation().getY() - */IdiotBox.frame.getMousePosition().getY();
            double centerX = IdiotBox.frame.getWidth() / 2;
            double centerY = IdiotBox.frame.getHeight() / 2;
            //double angle = Vector.getAngle(mouseX, mouseY, centerX, centerY);
            //double mag = Math.sqrt(Math.pow(mouseX - centerX, 2) + Math.pow(mouseY - centerY, 2));

            for(int i = 0; i < playerBlobs.size(); i++) {

                if (playerBlobs.get(i).getID() == 0) {
                    double angle = Vector.getAngle(mouseX,mouseY,playerBlobs.get(i).getLocation()[0]-xOffset,playerBlobs.get(i).getLocation()[1]-yOffset);
                    double mag = Math.sqrt(Math.pow(mouseX - playerBlobs.get(i).getLocation()[0]-xOffset, 2) + Math.pow(mouseY - playerBlobs.get(i).getLocation()[1]-yOffset, 2));
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
            System.out.println("Try to split");
            List<Integer> blobs = new ArrayList<Integer>();
            double totalM = 0;
            for(int i = 0; i < playerBlobs.size(); i++) {
                if (playerBlobs.get(i).getID() == 0) {
                    if (playerBlobs.get(i).getMass() >= Reference.splitMin) {
                        blobs.add(i);
                    }
                    totalM = totalM + playerBlobs.get(i).getMass();
                }
            }
            if (totalM < Reference.critMass) {
                for (int i = 0; i < blobs.size(); i++) {
                    PlayerBlobs p1 = playerBlobs.get(blobs.get(i));
                    p1.setMass(p1.getMass() / 2);
                    Vector v1 = new Vector(p1.playerVelocity.direction, p1.getMass() * Reference.zoom);
                    Vector v2 = new Vector(p1.playerVelocity.direction, p1.getMass() * Reference.zoom);
                    System.out.println(v2.getMagX() + ", " + v2.getMagY());
                    GameLoop.playerBlobs.add(new PlayerBlobs(0, p1.getMass(), p1.getLocation()[0] + v2.getMagX(), p1.getLocation()[1] + v2.getMagY(), p1.getTwin(), false));
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
            else {
                System.out.println("Can't split, critical mass hit");
            }
            KeyListener.split = false;
        }



    }

    public static void clientJoined(int id) {
        int ids = 0;
        for (int i = 0; i < playerBlobs.size(); i++) {
        if (playerBlobs.get(i).getID() > ids) {
            ids = playerBlobs.get(i).getID();
        }
        }
        idRelations.set(id,ids+1);
    }

    public static void clientLeft(int id) {
        for (int i = 0; i < playerBlobs.size(); i++) {
            if (playerBlobs.get(i).getID() == idRelations.get(id)) {
                playerBlobs.remove(i);
            }
        }
        idRelations.remove(id);
    }
}
