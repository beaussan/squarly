package me.nbeaussart.engine.model.interfaces;

import com.google.common.base.Preconditions;
import me.nbeaussart.engine.model.Cord;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author Nicolas Beaussart
 * @since 12/10/16
 */
public interface IGameMap<T extends ICoordinateSquare> {

    List<T> getMapData();
    void setMapData(List<T> data);

    default Optional<T> getFromCords(Cord cord) {
        return getMapData().stream().filter(t -> t.getCord().equals(cord)).findFirst();
    }

    default void removeOutOfBounds() {
        getMapData().removeIf(square -> square.getCord().getX() < 0 ||
                square.getCord().getY() < 0 ||
                sizeX() <= square.getCord().getX() ||
                sizeY() <= square.getCord().getY());
    }

    List<Consumer<Optional<T>>> getUpdatesHandlers();

    default void addUpdatesHandlers(Consumer<Optional<T>> obj) {
        if (!getUpdatesHandlers().contains(Preconditions.checkNotNull(obj))) {
            getUpdatesHandlers().add(obj);
        }
    }


    default void removeDuplicate(){
        setMapData(getMapData().stream().distinct().collect(Collectors.toList()));
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
