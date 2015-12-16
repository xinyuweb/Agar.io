package io.github.xenocider.AgarIO.listener;

import io.github.xenocider.AgarIO.GameLoop;
import io.github.xenocider.AgarIO.SexyStuff.IdiotBox;
import io.github.xenocider.AgarIO.util.Debug;

import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

public class MouseListener implements MouseInputListener {


    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {

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
        xOffset = xTotal/count- IdiotBox.frame.getWidth() / 2;
        yOffset = yTotal/count-IdiotBox.frame.getHeight() / 2;

        for (int i = 0; i < GameLoop.playerBlobs.size(); i++) {
            if (50 > Math.abs(GameLoop.playerBlobs.get(i).getLocation()[0]-xOffset - e.getX()) && 50 > Math.abs(GameLoop.playerBlobs.get(i).getLocation()[1]-yOffset - e.getY())) {
                Debug.debug(GameLoop.playerBlobs.get(i));
                //System.out.println(e.getX() + " " + GameLoop.playerBlobs[i].getLocation()[0]);
            }
        }
        for (int i = 0; i < GameLoop.food.size(); i++) {
            if (50 > Math.abs(GameLoop.food.get(i).getLocation()[0]-xOffset - e.getX()) && 50 > Math.abs(GameLoop.food.get(i).getLocation()[1]-yOffset - e.getY())) {
                Debug.debug(GameLoop.food.get(i));
            }
        }



    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button is pressed on a component and then
     * dragged.  <code>MOUSE_DRAGGED</code> events will continue to be
     * delivered to the component where the drag originated until the
     * mouse button is released (regardless of whether the mouse position
     * is within the bounds of the component).
     * <p>
     * Due to platform-dependent Drag&amp;Drop implementations,
     * <code>MOUSE_DRAGGED</code> events may not be delivered during a native
     * Drag&amp;Drop operation.
     *
     * @param e
     */
    @Override
    public void mouseDragged(MouseEvent e) {

    }

    /**
     * Invoked when the mouse cursor has been moved onto a component
     * but no buttons have been pushed.
     *
     * @param e
     */
    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
