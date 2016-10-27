package me.nbeaussart.impl.gui.gamegui;

import me.nbeaussart.engine.model.interfaces.IGameMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    private List<GameSquare> mapData = new ArrayList<>();
    private List<Consumer<Optional<GameSquare>>> listUpdateHandlers = new ArrayList<>();

    public GameMap(int sizeX, int sizeY, int height, int width) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.height = height;
        this.width = width;
    }

    @Override
    public List<GameSquare> getMapData() {
        return mapData;
    }

    @Override
    public void setMapData(List<GameSquare> data) {
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
