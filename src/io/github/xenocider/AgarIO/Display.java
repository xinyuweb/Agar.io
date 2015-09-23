package io.github.xenocider.AgarIO;

import javax.swing.*;
import java.awt.*;

public class Display{

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 10000;

    public static void main(String args[]){

        createDisplay();

    }

    private static void createDisplay(){

        JFrame frame = new JFrame("Agar.io");
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);


    }
}