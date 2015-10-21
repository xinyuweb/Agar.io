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

        for (int i = 0; i < GameLoop.playerBlobs.size(); i++) {
            if(GameLoop.playerBlobs.get(i).getID()==0) {
                g.setColor(Color.RED);
            }
            g.fillOval(GameLoop.playerBlobs.get(i).getLocation()[0]-GameLoop.playerBlobs.get(i).getMass()*5,GameLoop.playerBlobs.get(i).getLocation()[1]-GameLoop.playerBlobs.get(i).getMass()*5,GameLoop.playerBlobs.get(i).getMass()*10,GameLoop.playerBlobs.get(i).getMass()*10);
            g.setColor(Color.GREEN);
            g.fillRect(GameLoop.playerBlobs.get(i).getLocation()[0],GameLoop.playerBlobs.get(i).getLocation()[1],1,1);
            g.setColor(Color.BLUE);
            //System.out.println("drewcircle");
        }
        g.setColor(Color.BLACK);
        for (int i = 0; i < GameLoop.food.length; i++) {
            g.fillOval(GameLoop.food[i].getLocation()[0]-GameLoop.food[i].getMass()*5,GameLoop.food[i].getLocation()[1]-GameLoop.food[i].getMass()*5,GameLoop.food[i].getMass()*10,GameLoop.food[i].getMass()*10);
            //System.out.println("drewcircle");
        }

    }
}
