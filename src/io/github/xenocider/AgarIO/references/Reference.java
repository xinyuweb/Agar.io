package io.github.xenocider.AgarIO.references;

import java.awt.event.KeyEvent;

/**
 * Created by ict11 on 2015-09-28.
 */
public class Reference {

    //Keybindings
    public static final int forward = KeyEvent.VK_W;
    public static final int back = KeyEvent.VK_S;
    public static final int left = KeyEvent.VK_A;
    public static final int right = KeyEvent.VK_D;
    public static final int split = KeyEvent.VK_SPACE;

    //Map Size
    public static final int mapSize = 1000;

    public static final int playersMax = 2;

    public static final int splitMin = 4;

    //Test Variables
    public static final boolean gravityOn = true;
    public static final boolean AIOn = true;

    //Gravitational Multiplier
    public static final double gravMultiplier = 1000000000000d;

    //Movement Variables
    public static final double mouseMultiplier = 0.01;
    public static final double mouseMax = 0.1;
    public static final double maxSpeed = 3;
    public static final double friction = 0.2;
    public static final double fricitonLimit = 0.001;

}
