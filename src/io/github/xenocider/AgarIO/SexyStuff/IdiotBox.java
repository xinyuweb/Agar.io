package io.github.xenocider.AgarIO.SexyStuff;

import javax.swing.*;
import java.awt.*;

public class IdiotBox {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;

    public static JFrame frame;

    public static void main(String args[]){

        createDisplay();

    }

    public static void createDisplay(){

        frame = new JFrame("Agar.io");

        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        updateDisplay();

    }

    public static void updateDisplay(){

        

    }
}
