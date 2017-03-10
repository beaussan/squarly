package io.nbe.squarly.model.interfaces;

import io.nbe.squarly.model.Cord;
import io.nbe.squarly.model.Direction;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Interface which represent the coordinate for square
 *
 * @author Nicolas Beaussart
 * @since 25/10/16
 */
public interface ICoordinateSquare {

    /**
     * Create the list of the neighbors of the coordinate to return it
     * @return the list of the neighbors
     */
    default List<? extends ICoordinateSquare> getNeighbors(){
        return Arrays.stream(Direction.values())
                .map(direction ->  getGameMap().getFromCords(getCord().add(direction)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    /**
     * @return true if the current state is a wall, false else
     */
    default boolean isOpaque() { return getState() == IState.WALL; }

    /**
     * Method which return the coordinate of the square
     * @return the coordinate of the square
     */
    Cord getCord();

    /**
     * @return the reference of the game map.
     */
    IGameMap<? extends ICoordinateSquare> getGameMap();

    /**
     * Set that the coordinate square has been updated
     */
    void setUpdated();

    /**
     * @return the state of the current coordinate square
     */
    IState getState();

    /**
     * Define the state of the current coordinate square
     * @param state the state to define
     */
    void setState(IState state);
}
