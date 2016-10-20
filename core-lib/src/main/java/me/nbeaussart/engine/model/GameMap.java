package me.nbeaussart.engine.model;

import java.util.List;

/**
 * @author Nicolas Beaussart
 * @since 12/10/16
 */
public interface GameMap {

    List<GameSquare> getMapData();

    int sizeX();
    int siseY();

    default int getHeight() {
        return 10;
    }
    default int getWidth() {
        return 10;
    }
}
