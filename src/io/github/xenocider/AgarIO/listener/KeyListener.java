package io.github.xenocider.AgarIO.listener;

import io.github.xenocider.AgarIO.references.Reference;

import java.awt.event.KeyEvent;

/**
 * Created by ict11 on 2015-09-28.
 */
public class KeyListener implements java.awt.event.KeyListener {

    public static boolean forward = false;
    public static boolean back = false;
    public static boolean left = false;
    public static boolean right = false;

    public static boolean split = false;


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == Reference.forward) {
            forward = true;
        }
        if(e.getKeyCode() == Reference.back) {
            back = true;
        }
        if(e.getKeyCode() == Reference.left) {
            left = true;
        }
        if(e.getKeyCode() == Reference.right) {
            right = true;
        }
        if(e.getKeyCode() == Reference.split) {
            split = true;
        }
        if(e.getKeyCode() == Reference.pause) {
            if (Reference.playGame) {
                Reference.playGame = false;
            }
            else {
                Reference.playGame = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == Reference.forward) {
            forward = false;
        }
        if(e.getKeyCode() == Reference.back) {
            back = false;
        }
        if(e.getKeyCode() == Reference.left) {
            left = false;
        }
        if(e.getKeyCode() == Reference.right) {
            right = false;
        }
        if(e.getKeyCode() == Reference.split) {
            split = false;
        }
    }
}
