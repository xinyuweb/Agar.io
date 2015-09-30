package io.github.xenocider.AgarIO;

import io.github.xenocider.AgarIO.SexyStuff.Display;
import io.github.xenocider.AgarIO.entity.Food;
import io.github.xenocider.AgarIO.entity.PlayerBlobs;
import io.github.xenocider.AgarIO.listener.KeyListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class AgarIO {

    public static void main(String[] args) {

        setupGame();
        startGame();
    }

    private static void setupGame() {

        Display.createDisplay();
        Display.frame.addKeyListener(new KeyListener());
        Display.frame.setFocusable(true);

        GameLoop.setupGameData();

    }

    public static void startGame() {
        GameLoop.startLoop();
    }
}
