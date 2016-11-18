package io.nbe.impl.gui.gamegui.textbased;

import io.nbe.squarly.model.Cord;
import io.nbe.squarly.model.interfaces.IGameMap;

import java.util.*;
import java.util.function.Consumer;

/**
 * @author Nicolas Beaussart
 * @since 01/11/16
 */
public class GameMap implements IGameMap<GameSquareText> {
    Map<Cord, GameSquareText> map = new HashMap<>();
    List<Consumer<Optional<GameSquareText>>> data = new ArrayList<>();

    final int sizex;
    final int sizey;

    public GameMap(int sizex, int sizey) {
        this.sizex = sizex;
        this.sizey = sizey;
    }

    @Override
    public Map<Cord, GameSquareText> getMapData() {
        return map;
    }

    @Override
    public void setMapData(Map<Cord, GameSquareText> data) {
        this.map = data;
    }

    @Override
    public List<Consumer<Optional<GameSquareText>>> getUpdatesHandlers() {
        return data;
    }

    @Override
    public int sizeX() {
        return sizex;
    }

    @Override
    public int sizeY() {
        return sizey;
    }
}
