package io.github.xenocider.AgarIO.SexyStuff;

import javax.swing.*;
import java.awt.*;

public class Display{

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;

    public static JFrame frame;

    public int FPS;

    public static void main(String args[]){

        update();

    }

    public static void createDisplay(){

        frame = new JFrame("Agar.io");
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setBackground(Color.WHITE);
    }

    public static void update(){

        System.out.println("bugah2");

        Gooey.GUI();

    }
}