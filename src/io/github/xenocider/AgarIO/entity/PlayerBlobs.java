package io.github.xenocider.AgarIO.entity;

import io.github.xenocider.AgarIO.GameLoop;
import io.github.xenocider.AgarIO.references.Reference;
import io.github.xenocider.AgarIO.util.Vector;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

/**
 * Created by ict11 on 2015-09-23.
 */
public class PlayerBlobs extends Entity{

    public Vector playerVelocity = new Vector(0,0);
    private int id = -1;
    public Entity target = null;
    public int rejoinTime = -1;
    private List<PlayerBlobs> twin = new ArrayList<PlayerBlobs>();

    public PlayerBlobs(int id) {
        setSkin(new File("player"));
        System.out.println("Reg Blob created");
        setLocation((int) (Math.random() * Reference.mapSize), (int) (Math.random() * Reference.mapSize));
        setMass(2);
        setID(id);
    }
    public PlayerBlobs(int id, double mass,double x, double y, List<PlayerBlobs> t) {
        System.out.println("Split created");
        setSkin(new File("player"));
        setLocation(x, y);
        setMass(mass);
        setID(id);
        //setTwin(t);
        try {
        for (int i = 0; i < t.size(); i++) {
                addTwin(t.get(i));
            }
        }
        catch (Exception e) {
        }
    }

    public void restartRejoin() {
        //System.out.println("Restarted Rejoin Timer");
        rejoinTime = Reference.rejoinTime;
    }

    public int getID() {
        return id;
    }
    public void setID(int i) {
        id = i;
    }

    public void rejoin() {

        System.out.println("Combining " + this.toString() + " and " + twin.get(twin.size()-1).toString());
        this.setMass(this.getMass() + twin.get(twin.size() - 1).getMass());

        System.out.println("# of twins = " + twin.size());
        GameLoop.playerBlobs.remove(twin.get(twin.size() - 1));
        twin.remove(twin.size() - 1);
        System.out.println("Removed a twin from " + this.toString());
        if (twin.size() > 0) {
            restartRejoin();
        }
        else {
            rejoinTime = -1;
            target = null;
        }
    }

    public void rejoin2() {
        double angle = Vector.getAngle(twin.get(twin.size() - 1).getLocation()[0], twin.get(twin.size() - 1).getLocation()[1], this.getLocation()[0], this.getLocation()[1]);
        double mag = Reference.rejoinSpeed;
        playerVelocity.add(angle, mag);
        target = twin.get(twin.size() - 1);
        if (GameLoop.playerBlobs.contains(twin.get(twin.size() - 1))) {
            if (Math.sqrt(Math.pow((this.getLocation()[0] - twin.get(twin.size() - 1).getLocation()[0]), 2) + (Math.pow((this.getLocation()[1] - twin.get(twin.size() - 1).getLocation()[1]), 2))) < this.getMass() * 5) {
                for (int i = 0; i < GameLoop.playerBlobs.size(); i++) {
                    if (GameLoop.playerBlobs.get(i) == this) {
                        this.rejoin();
                        break;
                    }
                    if (GameLoop.playerBlobs.get(i) == target) {
                        twin.get(twin.size() - 1).rejoin();
                        break;
                    }
                }
            }
        }
        else {
            twin.remove(twin.size()-1);
        }
    }

    public List<PlayerBlobs> getTwin() {
        return twin;
    }

    public void addTwin(PlayerBlobs p) {
        System.out.println("adding to twin list");
        twin.add(p);
        System.out.println(twin.toString());
    }

    public void setTwin(List<PlayerBlobs> t) {
        twin = t;
    }

    public void explode() {
        double excessMass = this.getMass() -2;
        this.setMass(2);
        for (int i = 0; i <= excessMass/5; i++) {
            Food f = new Food(2, this.getLocation()[0], this.getLocation()[1]);
            GameLoop.food.add(f);
            f.velocity = new Vector(Math.random()*360, 0);
            //TODO Food doesn't shoot out
        }
    }

}
