package me.nbeaussart.engine.view;

import me.nbeaussart.engine.model.interfaces.ICoordinateSquare;

import java.awt.event.MouseEvent;

/**
 * @author Nicolas Beaussart
 * @since 21/10/16
 */
public interface GameSquareClicked<T extends ICoordinateSquare> {

    default void mouseClicked(T square, MouseEvent e) {
    }

    default void mousePressed(T square, MouseEvent e) {
    }

    default void mouseReleased(T square, MouseEvent e) {
    }

    default void mouseEntered(T square, MouseEvent e) {
    }

    default void mouseExited(T square, MouseEvent e) {
    }

    default void mouseDragged(T square, MouseEvent e) {
    }

    default void mouseMoved(T square, MouseEvent e) {
    }
}
