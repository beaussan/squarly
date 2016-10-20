package me.nbeaussart.engine.model;

/**
 * @author Nicolas Beaussart
 * @since 12/10/16
 */
public interface GameSquare {

    Coord getCoords();
    Color getColor();

    GameMap getGameMap();
}
