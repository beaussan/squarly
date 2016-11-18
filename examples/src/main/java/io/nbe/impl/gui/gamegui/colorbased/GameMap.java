package io.nbe.impl.gui.gamegui.colorbased;

import io.nbe.squarly.model.Cord;
import io.nbe.squarly.model.interfaces.IGameMap;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * @author Nicolas Beaussart
 * @since 20/10/16
 */
public class GameMap implements IGameMap<GameSquare> {

    private final int sizeX;
    private final int sizeY;
    private final int height;
    private final int width;
    private Map<Cord, GameSquare> mapData = new ConcurrentHashMap<>();
    private List<Consumer<Optional<GameSquare>>> listUpdateHandlers = new ArrayList<>();

    public GameMap(int sizeX, int sizeY, int height, int width) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.height = height;
        this.width = width;
    }

    @Override
    public Map<Cord, GameSquare> getMapData() {
        return mapData;
    }


    @Override
    public void setMapData(Map<Cord, GameSquare> data) {
        this.mapData = data;
    }

    @Override
    public List<Consumer<Optional<GameSquare>>> getUpdatesHandlers() {
        return listUpdateHandlers;
    }

    @Override
    public int sizeX() {
        return sizeX;
    }

    @Override
    public int sizeY() {
        return sizeY;
    }

    @Override
    public int getHeightPixel() {
        return height;
    }

    @Override
    public int getWidthPixel() {
        return width;
    }


}
