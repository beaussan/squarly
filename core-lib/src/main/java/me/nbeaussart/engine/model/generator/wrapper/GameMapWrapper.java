package me.nbeaussart.engine.model.generator.wrapper;

import me.nbeaussart.engine.model.Cord;
import me.nbeaussart.engine.model.Direction;
import me.nbeaussart.engine.model.interfaces.ICoordinateSquare;
import me.nbeaussart.engine.model.interfaces.IGameMap;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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

        gameMap.getMapData().forEach(t -> new SquareWrapper<>(t, this));

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
    public List<SquareWrapper<T>> getMapData() {
        return new ArrayList<>(dataMapped.values());
    }

    @Override
    public void setMapData(List<SquareWrapper<T>> data) {
        dataMapped = data.stream().collect(Collectors.toMap(SquareWrapper::getCord, item -> item));
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