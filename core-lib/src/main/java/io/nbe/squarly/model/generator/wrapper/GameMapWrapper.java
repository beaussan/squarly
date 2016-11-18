package io.nbe.engine.model.generator.wrapper;

import io.nbe.engine.model.Cord;
import io.nbe.engine.model.Direction;
import io.nbe.engine.model.interfaces.ICoordinateSquare;
import io.nbe.engine.model.interfaces.IGameMap;

import java.util.*;
import java.util.function.Consumer;

/**
 * @author Nicolas Beaussart
 * @since 31/10/16
 */
public class GameMapWrapper<T extends ICoordinateSquare> implements IGameMap<SquareWrapper<T>> {
    private final IGameMap<T> gameMap;
    private List<Consumer<Optional<SquareWrapper<T>>>> data = new ArrayList<>();
    private Map<Cord, SquareWrapper<T>> dataMapped = new HashMap<>();

    @Override
    public Optional<SquareWrapper<T>> getFromCords(Cord cord) {
        return Optional.ofNullable(dataMapped.get(cord));
    }

    public GameMapWrapper(IGameMap<T> gameMap){
        this.gameMap = gameMap;
        final List<Cord> cordList =new ArrayList<>();
        cordList.add(Direction.UP.add(Direction.RIGHT));
        cordList.add(Direction.UP.add(Direction.LEFT));
        cordList.add(Direction.DOWN.add(Direction.RIGHT));
        cordList.add(Direction.DOWN.add(Direction.LEFT));

        gameMap.getMapData().values().forEach(t -> new SquareWrapper<T>(t, this));

        dataMapped.forEach((cord, squareWrapper) -> {
            for (Direction direction : Direction.values()) {
                Cord c = cord.add(direction);
                if (dataMapped.containsKey(c)){
                    squareWrapper.neighs.put(direction, dataMapped.get(c));
                }
            }
            for (Cord cord1 : cordList) {
                Cord c = cord.add(cord1);
                if (dataMapped.containsKey(c)){
                    squareWrapper.diagonals.add(dataMapped.get(c));
                }
            }
            squareWrapper.diagonals.addAll(squareWrapper.neighs.values());
        });
    }

    public void addSquare(SquareWrapper<T> squareWrapper){
        dataMapped.put(squareWrapper.getCord(), squareWrapper);
    }


    @Override
    public Map<Cord, SquareWrapper<T>> getMapData() {
        return dataMapped;
    }

    @Override
    public void setMapData(Map<Cord, SquareWrapper<T>> data) {

    }



    public Map<Cord, SquareWrapper<T>> getDataMapped() {
        return dataMapped;
    }

    @Override
    public List<Consumer<Optional<SquareWrapper<T>>>> getUpdatesHandlers() {
        return data;
    }

    @Override
    public int sizeX() {
        return gameMap.sizeX();
    }

    @Override
    public int sizeY() {
        return gameMap.sizeY();
    }

    @Override
    public int getHeightPixel() {
        return gameMap.getHeightPixel();
    }

    @Override
    public int getWidthPixel() {
        return gameMap.getWidthPixel();
    }
}