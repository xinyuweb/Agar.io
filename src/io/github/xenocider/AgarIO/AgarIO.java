package io.github.xenocider.AgarIO;

import io.github.xenocider.AgarIO.SexyStuff.IdiotBox;
import io.github.xenocider.AgarIO.entity.Food;
import io.github.xenocider.AgarIO.entity.PlayerBlobs;
import io.github.xenocider.AgarIO.listener.KeyListener;
import io.github.xenocider.AgarIO.listener.MouseListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class AgarIO {

    public static void main(String[] args) {

        System.out.println("Initializing Game...");
        System.out.println("    Setting Up Game..");
        setupGame();
        System.out.println("    Setting Up Complete!");
        System.out.println("    Starting Game...");
        startGame();
        System.out.println("    Starting Game Successful!");
        System.out.println("Initialization Complete!");

    }

    private static void setupGame() {

        System.out.println("        Creating Display...");
        IdiotBox.createDisplay();
        IdiotBox.frame.setFocusable(true);
        System.out.println("        Display Created!");
        System.out.println("        Adding Key Listener...");
        IdiotBox.frame.addKeyListener(new KeyListener());
        System.out.println("        Key Listener Added!");
        System.out.println("        Adding Mouse Listener...");
        IdiotBox.frame.addMouseListener(new MouseListener());
        System.out.println("        Mouse Listener Added!");
        System.out.println("        Setting Up Game Data...");
        GameLoop.setupGameData();
        System.out.println("        Game Data Setup!");

    }

    public static void startGame() {
        GameLoop.startLoop();
    }
}
