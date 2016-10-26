package me.nbeaussart.engine.model.interfaces;

import me.nbeaussart.engine.model.Cord;

/**
 * @author Nicolas Beaussart
 * @since 25/10/16
 */
public interface ICoordinateSquare {
    Cord getCord();

    IGameMap<? extends ICoordinateSquare> getGameMap();

    void setUpdated();
}
