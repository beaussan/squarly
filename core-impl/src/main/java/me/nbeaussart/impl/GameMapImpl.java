package me.nbeaussart.impl;

import me.nbeaussart.engine.model.GameMap;
import me.nbeaussart.engine.model.GameSquare;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nicolas Beaussart
 * @since 20/10/16
 */
public class GameMapImpl implements GameMap {

    private List<GameSquare> mapData = new ArrayList<>();
    private final int sizeX;
    private final int sizeY;
    private final int height;
    private final int width;

    public GameMapImpl(int sizeX, int sizeY, int height, int width){
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
    public int sizeX() {
        return sizeX;
    }

    @Override
    public int sizeY() {
        return sizeY;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }


    public void deleteDuplicate(){
        mapData = mapData.stream().distinct().collect(Collectors.toList());
    }

}
