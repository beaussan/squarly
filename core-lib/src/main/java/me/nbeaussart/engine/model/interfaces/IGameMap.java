package me.nbeaussart.engine.model.interfaces;

import me.nbeaussart.engine.model.Coord;

import java.util.List;
import java.util.Optional;

/**
 * @author Nicolas Beaussart
 * @since 12/10/16
 */
public interface IGameMap<T extends ICoordinateSquare> {

    List<T> getMapData();

    default Optional<T> getFromCoords(Coord coord) {
        return getMapData().stream().filter(t -> t.getCoord().equals(coord)).findFirst();
    }

    default void removeOutOfBounds() {
        getMapData().removeIf(square -> square.getCoord().getX() < 0 ||
                square.getCoord().getY() < 0 ||
                sizeX() <= square.getCoord().getX() ||
                sizeY() <= square.getCoord().getY());
    }


    int sizeX();

    int sizeY();

    default int getHeightPixel() {
        return 10;
    }

    default int getWidthPixel() {
        return 10;
    }
}
