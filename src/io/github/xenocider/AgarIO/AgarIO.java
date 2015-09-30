package io.github.xenocider.AgarIO;

import io.github.xenocider.AgarIO.SexyStuff.IdiotBox;
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

        IdiotBox.createDisplay();
        System.out.println(new PlayerBlobs().getSkin());
        System.out.println(new Food().getSkin());
        IdiotBox.frame.addKeyListener(new KeyListener());
        IdiotBox.frame.setFocusable(true);

        GameLoop.setupGameData();

    }

    public static void startGame() {
        GameLoop.startLoop();
    }
}
