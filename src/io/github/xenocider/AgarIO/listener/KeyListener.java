package io.github.xenocider.AgarIO.listener;

import io.github.xenocider.AgarIO.references.Reference;

import java.awt.event.KeyEvent;

/**
 * Created by ict11 on 2015-09-28.
 */
public class KeyListener implements java.awt.event.KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyCode() == Reference.forward)
            System.out.println("forward");
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
