package io.github.xenocider.AgarIO;

import io.github.xenocider.AgarIO.entity.PlayerBlobs;
import io.github.xenocider.AgarIO.listener.KeyListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by ict11 on 2015-09-30.
 */
public class GameLoop implements Runnable {

    PlayerBlobs[] playerBlobs;

    public static void setupGameData() {

    }

    public static void startLoop() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        ScheduledFuture gameLoop = executor.scheduleWithFixedDelay(new GameLoop(), 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void run() {

        System.out.println("loop de loop");

        //Adjust player velocity
        if (KeyListener.forward) {

        }


    }
}
