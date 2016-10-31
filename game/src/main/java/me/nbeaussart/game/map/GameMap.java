package me.nbeaussart.game.map;

import me.nbeaussart.engine.model.interfaces.IGameMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Created by beaussan on 31/10/16.
 */
public class GameMap implements IGameMap<GameSquare>{

    private final int sizeX;
    private final int sizeY;
    private List<GameSquare> list = new ArrayList<>();
    private List<Consumer<Optional<GameSquare>>> machin = new ArrayList<>();

    public GameMap(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public List<GameSquare> getMapData() {
        return list;
    }

    public void setMapData(List<GameSquare> data) {
        list = data;
    }

    public List<Consumer<Optional<GameSquare>>> getUpdatesHandlers() {
        return machin;
    }

    public int sizeX() {
        return sizeX;
    }

    public int sizeY() {
        return sizeY;
    }
}
