package io.nbe.impl.gui.gamegui.fovtest;

import io.nbe.squarly.model.Cord;
import io.nbe.squarly.model.interfaces.IGameMap;

import java.util.*;
import java.util.function.Consumer;

/**
 * @author Nicolas Beaussart
 * @since 13/11/16
 */
public class GameMap implements IGameMap<GameSquare> {

    Map<Cord, GameSquare> data = new HashMap<>();
    List<Consumer<Optional<GameSquare>>> updateHandlers = new ArrayList<>();
    final int sizeX;
    final int sizeY;
    final private int height;
    final private int width;

    public GameMap(int sizeX, int sizeY, int height, int width) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                add(new GameSquare(Cord.get(x,y), this));
            }
        }
        this.height = height;
        this.width = width;
    }


    @Override
    public int getHeightPixel() {
        return height;
    }

    @Override
    public int getWidthPixel() {
        return width;
    }

    @Override
    public Map<Cord, GameSquare> getMapData() {
        return data;
    }

    @Override
    public void setMapData(Map<Cord, GameSquare> data) {
        this.data = data;
    }

    @Override
    public List<Consumer<Optional<GameSquare>>> getUpdatesHandlers() {
        return updateHandlers;
    }

    @Override
    public int sizeX() {
        return sizeX;
    }

    @Override
    public int sizeY() {
        return sizeY;
    }
}
