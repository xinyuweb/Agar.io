package io.github.xenocider.AgarIO.SexyStuff;

import io.github.xenocider.AgarIO.GameLoop;

import java.awt.*;

/**
 * Created by ict11 on 2015-10-07.
 */
public class TestOnlyGraphics {

    public static void paint(Graphics g){

        g.clearRect(0,0,10000,10000);

        //g.fillRect(100,100,100,100);

        //g.setColor(Color.red);

        for (int i = 0; i < GameLoop.playerBlobs.length; i++) {
            g.fillOval(GameLoop.playerBlobs[i].getLocation()[0],GameLoop.playerBlobs[i].getLocation()[1],50,50);
            //System.out.println("drewcircle");
        }
        for (int i = 0; i < GameLoop.food.length; i++) {
            g.fillOval(GameLoop.food[i].getLocation()[0],GameLoop.food[i].getLocation()[1],25,25);
            //System.out.println("drewcircle");
        }

    }
}
