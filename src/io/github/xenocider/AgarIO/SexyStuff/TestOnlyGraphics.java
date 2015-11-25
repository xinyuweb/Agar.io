package io.github.xenocider.AgarIO.SexyStuff;

import io.github.xenocider.AgarIO.GameLoop;
import io.github.xenocider.AgarIO.references.Reference;

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
            g.setColor(Color.BLUE);
            if(GameLoop.playerBlobs.get(i).getID()==0) {
                g.setColor(Color.RED);
            }
            g.fillOval((int) (GameLoop.playerBlobs.get(i).getLocation()[0]-GameLoop.playerBlobs.get(i).getMass()*Reference.zoom),(int) (GameLoop.playerBlobs.get(i).getLocation()[1]-GameLoop.playerBlobs.get(i).getMass()*Reference.zoom),(int) (GameLoop.playerBlobs.get(i).getMass()*10),(int) (GameLoop.playerBlobs.get(i).getMass()*10));
            g.setColor(Color.GREEN);
            g.fillRect((int)GameLoop.playerBlobs.get(i).getLocation()[0],(int)GameLoop.playerBlobs.get(i).getLocation()[1],1,1);
            //System.out.println("drewcircle");
        }
        for (int i = 0; i < GameLoop.food.size(); i++) {
            g.setColor(Color.BLACK);
            g.fillOval((int) (GameLoop.food.get(i).getLocation()[0]-GameLoop.food.get(i).getMass()*Reference.zoom),(int) (GameLoop.food.get(i).getLocation()[1]-GameLoop.food.get(i).getMass()*Reference.zoom),(int) (GameLoop.food.get(i).getMass()*10),(int) (GameLoop.food.get(i).getMass()*10));
            //System.out.println("drewcircle");
        }

    }
}
