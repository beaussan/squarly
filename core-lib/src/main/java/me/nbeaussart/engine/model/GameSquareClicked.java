package me.nbeaussart.engine.model;

import java.awt.event.MouseEvent;

/**
 * @author Nicolas Beaussart
 * @since 21/10/16
 */
public interface GameSquareClicked {

    default void  mouseClicked(GameSquare square, MouseEvent e) {}

    default void mousePressed(GameSquare square, MouseEvent e) {}

    default void mouseReleased(GameSquare square, MouseEvent e) {}

    default void mouseEntered(GameSquare square, MouseEvent e) {}

    default void mouseExited(GameSquare square, MouseEvent e) {}

    default void mouseDragged(GameSquare square, MouseEvent e) {}

    default void mouseMoved(GameSquare square, MouseEvent e) {}
}
