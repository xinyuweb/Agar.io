package io.github.xenocider.AgarIO;

import io.github.xenocider.AgarIO.SexyStuff.Display;
import io.github.xenocider.AgarIO.entity.Food;
import io.github.xenocider.AgarIO.entity.PlayerBlobs;

public class AgarIO {

    public static void main(String[] args) {

        startGame();
    }

    private static void startGame() {

        Display.createDisplay();
        System.out.println(new PlayerBlobs().getSkin());
        System.out.println(new Food().getSkin());


    }
}
