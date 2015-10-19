package io.github.xenocider.AgarIO.SexyStuff;

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics2D.*;
import java.awt.geom.Arc2D;

public class IdiotBox {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;

    public static JFrame frame;

    public static int targetFPS = 60;
    public static double FPS;
    public static double frames;
    public static double seconds = System.nanoTime();


    public static void main(String args[]){

        createDisplay();
        updateDisplay();

    }

    public static void createDisplay(){

        frame = new JFrame("Agar.io");

        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

    }


    public static void updateDisplay(){

        while (true){

            FPS = FPS / seconds;
            System.out.println(FPS);
        }

    }
}
