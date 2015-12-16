package io.github.xenocider.AgarIO.server;

import io.github.xenocider.AgarIO.entity.Food;
import io.github.xenocider.AgarIO.entity.PlayerBlobs;
import io.github.xenocider.AgarIO.util.Vector;

import java.io.*;
import java.util.ArrayList;

/*
 * This class defines the different type of messages that will be exchanged between the
 * Clients and the Server. 
 * When talking from a Java Client to a Java Server a lot easier to pass Java objects, no 
 * need to count bytes or to wait for a line feed at the end of the frame
 */
public class ChatMessage implements Serializable {

    protected static final long serialVersionUID = 1112122200L;

    // The different types of message sent by the Client
    // WHOISIN to receive the list of the users connected
    // MESSAGE an ordinary message
    // LOGOUT to disconnect from the Server
    //static final int WHOISIN = 0, MESSAGE = 1, LOGOUT = 2;
    //private int type;
    private String message;

    // constructor
    ChatMessage(int type, String message) {
        this.type = type;
        this.message = message;
    }

    // getters
    //int getType() {
        //return type;
    //}
    String getMessage() {
        return message;
    }


    //MY CODE

    private int type;
    static final int JOIN = 0, LEAVE = 1, SERVERDATA = 2, CLIENTDATA = 3;

    //Server
    private ArrayList<PlayerBlobs> players;
    private ArrayList<Food> food;
    private ArrayList<Integer> scoreboard;

    //Client
    private boolean split;
    private ArrayList<Vector> velocity;

    int getType() {
        return type;
    }

    ArrayList<PlayerBlobs> getPlayers() {
        return players;
    }
    ArrayList<Food> getFood() {
        return food;
    }
    ArrayList<Integer> getScoreboard() {
        return scoreboard;
    }

    boolean isSplit() {
        return split;
    }
    ArrayList<Vector> getVelocity() {
        return velocity;
    }
}
