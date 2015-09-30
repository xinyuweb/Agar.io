package io.github.xenocider.AgarIO.SexyStuff;

import javax.swing.*;

public class IdiotBox {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;

    public static void main(String args[]){

        createDisplay();

    }

    public static void createDisplay(){

        JFrame frame = new JFrame("Agar.io");

        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

    }
}
