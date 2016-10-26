package me.nbeaussart.engine.model.interfaces;

import me.nbeaussart.engine.model.Coord;

/**
 * @author Nicolas Beaussart
 * @since 25/10/16
 */
public interface ICoordinateSquare {
    Coord getCoord();

    IGameMap<? extends ICoordinateSquare> getGameMap();
}
