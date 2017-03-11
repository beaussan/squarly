package io.nbe.squarly.model.interfaces;

import io.nbe.squarly.model.Cord;

import java.util.*;
import java.util.function.Consumer;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Interface which represent the game map
 *
 * @param <T> the type of cells in the dungeon
 * @author Nicolas Beaussart
 * @since 12/10/16
 */
public interface IGameMap<T extends ICoordinateSquare> {

    /**
     * @return should return a map with coordinates as key for data <T> from the map data
     */
    Map<Cord, T> getMapData();

    /**
     * Define the map data of the game map
     * @param data the map which contains all the data for each coordinates
     */
    void setMapData(Map<Cord, T> data);

    /**
     * Get T data from the coordinate
     * @param cord the coordinate to get
     * @return an optional value containing the data T
     */
    default Optional<T> getFromCords(Cord cord) {
        return Optional.ofNullable(getMapData().get(cord));
    }

    /**
     * Add data T to the game map
     * @param t the data to add containing also the coordinate
     */
    default void add(T t){
        checkNotNull(getMapData(), "Map data is null")
        .put(checkNotNull(t, "Object to add is null").getCord(), t);
    }

    /**
     * Remove every data from the game map
     */
    default void removeOutOfBounds() {
        if (getMapData() != null){
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

    /**
     * @return the size X of the game map
     */
    int sizeX();

    /**
     * @return the size Y of the game map
     */
    int sizeY();

    /**
     * Default method to get the height of the pixel
     * @return the height of the pixel, by default 10
     */
    default int getHeightPixel() {
        return 10;
    }

    /**
     * Default method to get the width of the pixel
     * @return the width of th epixel, by default 10
     */
    default int getWidthPixel() {
        return 10;
    }
}
