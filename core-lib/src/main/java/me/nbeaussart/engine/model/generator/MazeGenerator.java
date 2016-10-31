package me.nbeaussart.engine.model.generator;

import me.nbeaussart.engine.model.Cord;
import me.nbeaussart.engine.model.Direction;
import me.nbeaussart.engine.model.GameGenerator;
import me.nbeaussart.engine.model.generator.wrapper.GameMapWrapper;
import me.nbeaussart.engine.model.generator.wrapper.SquareWrapper;
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
    public void doGenerate() {

        GameMapWrapper<T> mapWrapper = getMapWrapper();

        List<SquareWrapper<T>> wallList = new ArrayList<>();
        Random r = new Random();

        Optional<SquareWrapper<T>> fromCords = mapWrapper.getFromCords(new Cord(1, 1));

        if (fromCords.isPresent()) {
            fromCords.get().setState(IState.ROOM);
            wallList.addAll(fromCords.get().getNeighs(IState.WALL));

        } else {
            throw new IllegalStateException("The map is too small");
        }

        while (!wallList.isEmpty()){
            SquareWrapper<T> rmd = wallList.remove(r.nextInt(wallList.size()));
            if (rmd.getNeighs().size() != 4){
                continue;
            }
            if (rmd.getNeighs(IState.ROOM).size() == 1){
                rmd.setState(IState.ROOM);
                wallList.addAll(rmd.getNeighs(IState.WALL));
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


}
