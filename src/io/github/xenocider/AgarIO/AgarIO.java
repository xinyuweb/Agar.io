package io.github.xenocider.AgarIO;

import io.github.xenocider.AgarIO.SexyStuff.Display;
import io.github.xenocider.AgarIO.entity.Food;
import io.github.xenocider.AgarIO.entity.PlayerBlobs;
import io.github.xenocider.AgarIO.listener.KeyListener;

public class AgarIO {

    public static void main(String[] args) {

        startGame();
    }

    private static void startGame() {

        Display.createDisplay();
        Display.frame.addKeyListener(new KeyListener());
        System.out.println(new PlayerBlobs().getSkin());
        System.out.println(new Food().getSkin());


    }
}
