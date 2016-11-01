package me.nbeaussart.engine.model.interfaces;

import me.nbeaussart.engine.model.Cord;

/**
 * @author Nicolas Beaussart
 * @since 01/11/16
 */
public interface IEntityCoordinate<T extends ICoordinateSquare> {

    Cord getCord();
     boolean canPassOn(T gameSquare);
    int getCost(T gameSquare);
    <E extends IGameMap<T>> T getMap();

}
