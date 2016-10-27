package me.nbeaussart.engine.model.generator;

import me.nbeaussart.engine.model.Cord;
import me.nbeaussart.engine.model.Direction;
import me.nbeaussart.engine.model.GameGenerator;
import me.nbeaussart.engine.model.interfaces.ICoordinateSquare;
import me.nbeaussart.engine.model.interfaces.IGameMap;
import me.nbeaussart.engine.model.interfaces.IState;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Nicolas Beaussart
 * @since 27/10/16
 */
public class MazeGenerator<T extends ICoordinateSquare> extends AbsGenerator<T> {

    private final IGameMap<T> gameMap;
    private final List<T> mapData;

    public MazeGenerator(GameGenerator<T> gameGenerator) {
        super(gameGenerator);
        gameMap = getGameGenerator().getGameMap();
        mapData = gameMap.getMapData();
    }

    @Override
    public void generate() {

        List<T> wallList = new ArrayList<T>();
        Random r = new Random();

        Optional<T> fromCords = gameMap.getFromCords(new Cord(1, 1));
        if (fromCords.isPresent()) {
            fromCords.get().setState(IState.ROOM);
            wallList.addAll(getWallsArround(fromCords.get()));
        } else {
            throw new IllegalStateException("The map is too small");
        }

        while (!wallList.isEmpty()){
            T rmd = wallList.remove(r.nextInt(wallList.size()));
            if (getArround(rmd).size() != 4){
                continue;
            }
            if (getRoomArround(rmd).size() == 1){
                rmd.setState(IState.ROOM);
                wallList.addAll(getWallsArround(rmd));
            }
        }



    }

    /*
    Start with a grid full of walls.
    Pick a cell, mark it as part of the maze. Add the walls of the cell to the wall list.
    While there are walls in the list:

        Pick a random wall from the list. If only one of the two cells that the wall divides is a room, then:
            Make the wall a passage and mark the unvisited cell as part of the maze.
            Add the neighboring walls of the cell to the wall list.
        Remove the wall from the list.

     */

    private List<T> getWallsArround(T t){
        return getArround(t).stream().filter(t1 -> t1.getState() == IState.WALL).collect(Collectors.toList());
    }
    private List<T> getRoomArround(T t){
        return getArround(t).stream().filter(t1 -> t1.getState() == IState.ROOM).collect(Collectors.toList());
    }

    private List<T> getArround(T t){
        return Arrays.stream(Direction.values()).map(direction -> gameMap.getFromCords(t.getCord().add(direction.getCords())))
                .filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
    }


}
