package io.github.xenocider.AgarIO;

<<<<<<< Updated upstream
import io.github.xenocider.AgarIO.SexyStuff.Display;
import java.awt.Graphics.*;
=======
import io.github.xenocider.AgarIO.entity.Food;
import io.github.xenocider.AgarIO.entity.PlayerBlobs;
>>>>>>> Stashed changes

public class AgarIO {

    public static void main(String[] args) {

        startGame();
    }

    private static void startGame() {

<<<<<<< Updated upstream
        Display.createDisplay();
=======
        System.out.println(new PlayerBlobs().getSkin());
        System.out.println(new Food().getSkin());


>>>>>>> Stashed changes
    }
}
