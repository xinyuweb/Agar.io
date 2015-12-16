package io.github.xenocider.AgarIO.SexyStuff;

import io.github.xenocider.AgarIO.GameLoop;
import io.github.xenocider.AgarIO.references.Reference;

import java.awt.*;

/**
 * Created by ict11 on 2015-10-07.
 */
public class TestOnlyGraphics {

    public static void paint(Graphics g){

        g.clearRect(0, 0, 10000, 10000);

        double xOffset = 0;
        double yOffset = 0;
        double count = 0;
        double xTotal = 0;
        double yTotal = 0;
        for (int i = 0; i < GameLoop.playerBlobs.size(); i++) {
            if(GameLoop.playerBlobs.get(i).getID()==0) {
                xTotal = xTotal + GameLoop.playerBlobs.get(i).getLocation()[0];
                yTotal = yTotal + GameLoop.playerBlobs.get(i).getLocation()[1];
                count = count + 1;
            }
        }
        xOffset = xTotal/count-IdiotBox.frame.getWidth() / 2;
        yOffset = yTotal/count-IdiotBox.frame.getHeight() / 2;
        //System.out.println(xOffset + " " + yOffset);
            //g.fillRect(100,100,100,100);

        //g.setColor(Color.red);

        for (int i = 0; i < GameLoop.playerBlobs.size(); i++) {
            g.setColor(Color.BLUE);
            if(GameLoop.playerBlobs.get(i).getID()==0) {
                g.setColor(Color.RED);
            }
            g.fillOval((int) (GameLoop.playerBlobs.get(i).getLocation()[0]-GameLoop.playerBlobs.get(i).getMass()*Reference.zoom-xOffset),(int) (GameLoop.playerBlobs.get(i).getLocation()[1]-GameLoop.playerBlobs.get(i).getMass()*Reference.zoom-yOffset),(int) (GameLoop.playerBlobs.get(i).getMass()*10),(int) (GameLoop.playerBlobs.get(i).getMass()*10));
            g.setColor(Color.GREEN);
            g.fillRect((int)(GameLoop.playerBlobs.get(i).getLocation()[0]-xOffset),(int)(GameLoop.playerBlobs.get(i).getLocation()[1]-yOffset),1,1);
            //System.out.println("drewcircle");
        }
        for (int i = 0; i < GameLoop.food.size(); i++) {
            g.setColor(Color.BLACK);
            g.fillOval((int) (GameLoop.food.get(i).getLocation()[0] - GameLoop.food.get(i).getMass() * Reference.zoom-xOffset), (int) (GameLoop.food.get(i).getLocation()[1] - GameLoop.food.get(i).getMass() * Reference.zoom-yOffset), (int) (GameLoop.food.get(i).getMass() * 10), (int) (GameLoop.food.get(i).getMass() * 10));
            //System.out.println("drewcircle");
        }
        g.setColor(Color.WHITE);
        g.fillRect(IdiotBox.WIDTH - 200, 0, 200, GameLoop.scoreboard.size()*20+70);
        g.setColor(Color.BLACK);
        g.drawString("SCOREBOARD", IdiotBox.WIDTH - 180, 40);
        for (int i = 0; i < GameLoop.scoreboard.size(); i++) {
            g.drawString("Player " + i +": " + GameLoop.scoreboard.get(i), IdiotBox.WIDTH-180, i*20+60);
        }

    }
}
