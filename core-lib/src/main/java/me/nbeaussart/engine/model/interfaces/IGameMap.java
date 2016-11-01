package me.nbeaussart.engine.model.interfaces;

import com.google.common.base.Preconditions;
import me.nbeaussart.engine.model.Cord;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;
import static me.nbeaussart.engine.util.StreamUtils.distinctByKey;

/**
 * @author Nicolas Beaussart
 * @since 12/10/16
 */
public interface IGameMap<T extends ICoordinateSquare> {

    Map<Cord, T> getMapData();

    void setMapData(Map<Cord, T> data);

    default Optional<T> getFromCords(Cord cord) {
        return Optional.ofNullable(getMapData().get(cord));
    }

    default void add(T t){
        checkNotNull(getMapData(), "Map data is null")
        .put(checkNotNull(t, "Object to add is null").getCord(), t);
    }

    default void removeOutOfBounds() {
        List<Cord> toBeRemoved = new ArrayList<>();
        getMapData().forEach((cord, t) -> {
            if (cord.getX() < 0 ||
                    cord.getY() < 0 ||
                    sizeX() <= cord.getX() ||
                    sizeY() <= cord.getY()){
                toBeRemoved.add(cord);
            }
        });
        toBeRemoved.forEach(cord -> getMapData().remove(cord));
    }

    List<Consumer<Optional<T>>> getUpdatesHandlers();

    default void addUpdatesHandlers(Consumer<Optional<T>> obj) {
        if (!getUpdatesHandlers().contains(checkNotNull(obj))) {
            getUpdatesHandlers().add(obj);
        }
    }

    default void setChanged(T object) {
        getUpdatesHandlers().forEach(tConsumer -> tConsumer.accept(Optional.ofNullable(object)));
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
