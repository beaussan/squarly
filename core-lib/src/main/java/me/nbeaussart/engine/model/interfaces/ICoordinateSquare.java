package me.nbeaussart.engine.model.interfaces;

import me.nbeaussart.engine.model.Cord;
import me.nbeaussart.engine.model.Direction;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Nicolas Beaussart
 * @since 25/10/16
 */
public interface ICoordinateSquare {


    default List<? extends ICoordinateSquare> getNeighbors(){
        return Arrays.stream(Direction.values())
                .map(direction ->  getGameMap().getFromCords(getCord().add(direction)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
    
    Cord getCord();

    IGameMap<? extends ICoordinateSquare> getGameMap();

    void setUpdated();

    IState getState();
    
    void setState(IState state);
}
