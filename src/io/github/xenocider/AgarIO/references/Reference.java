package io.github.xenocider.AgarIO.references;

import java.awt.event.KeyEvent;

/**
 * Created by ict11 on 2015-09-28.
 */
public class Reference {

    public static boolean playGame = true;

    //Keybindings
    public static final int forward = KeyEvent.VK_W;
    public static final int back = KeyEvent.VK_S;
    public static final int left = KeyEvent.VK_A;
    public static final int right = KeyEvent.VK_D;
    public static final int split = KeyEvent.VK_SPACE;
    public static final int pause = KeyEvent.VK_P;

    //Map Size
    public static final int mapSize = 1000;

    public static final int zoom = 5;

    public static final int playersMax = 3;
    public static final int splitMin = 4;
    public static final int rejoinTime = 100;
    public static final double critMass = 60;

    //Test Variables
    public static final boolean gravityOn = true;
    public static final boolean AIOn = true;
    public static final boolean selfGravity = false;

    //Gravitational Multiplier
    public static final double gravMultiplier = 1000000000000d;
    public static final double selfGravMultiplier = 0.01d;

    //Movement Variables
    public static final double mouseMultiplier = 0.05;
    public static final double mouseMax = 2;
    public static final double maxSpeed = 2;
    public static final double rejoinSpeed = 1;
    public static final double splitSpeed = 5;
    public static final double friction = 0.2;
    public static final double fricitonLimit = 0.01;
    public static final double massSlownessMultiplier = -2;


}
