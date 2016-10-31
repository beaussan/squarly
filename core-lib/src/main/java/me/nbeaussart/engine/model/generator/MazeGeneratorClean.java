package me.nbeaussart.engine.model.generator;

import me.nbeaussart.engine.model.Cord;
import me.nbeaussart.engine.model.Direction;
import me.nbeaussart.engine.model.GameGenerator;
import me.nbeaussart.engine.model.interfaces.ICoordinateSquare;
import me.nbeaussart.engine.model.interfaces.IGameMap;
import me.nbeaussart.engine.model.interfaces.IState;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Nicolas Beaussart
 * @since 27/10/16
 */
public class MazeGeneratorClean<T extends ICoordinateSquare> extends AbsGenerator<T> {

    private final IGameMap<T> gameMap;
    private final List<T> mapData;

    public MazeGeneratorClean(GameGenerator<T> gameGenerator) {
        super(gameGenerator);
        gameMap = getGameGenerator().getGameMap();
        mapData = gameMap.getMapData();
    }

    @Override
    public void doGenerate() {

        List<T> wallList = new ArrayList<T>();
        Random r = new Random();

        Optional<T> fromCords = gameMap.getFromCords(new Cord(1, 1));
        if (fromCords.isPresent()) {
            fromCords.get().setState(IState.ROOM);
            wallList.addAll(getWallsAround(fromCords.get()));
        } else {
            throw new IllegalStateException("The map is too small");
        }

        while (!wallList.isEmpty()){
            T rmd = wallList.remove(r.nextInt(wallList.size()));

            List<T> around = getAround(rmd);

            if (getRoomAround(rmd, around).size() == 1 &&
                    getWallsAroundDiagonal(rmd, around).size() >= 6){
                rmd.setState(IState.ROOM);
                wallList.addAll(getWallsAround(rmd, around));
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

    private List<T> getWallsAround(T t, List<T> arround){
        return arround.parallelStream().filter(t1 -> t1.getState() == IState.WALL).collect(Collectors.toList());
    }


    private List<T> getWallsAround(T t){
        return getWallsAround(t, getAround(t));
    }


    private List<T> getWallsAroundDiagonal(T t, List<T> arround){
        List<T> aroundDiagonal = getAroundDiagonal(t);
        aroundDiagonal.addAll(arround);
        return aroundDiagonal.parallelStream().filter(t1 -> t1.getState() == IState.WALL).collect(Collectors.toList());
    }

    private List<T> getRoomAround(T t, List<T> arround){
        return arround.parallelStream().filter(t1 -> t1.getState() == IState.ROOM).collect(Collectors.toList());
    }

    private List<T> getAround(T t){
        return Arrays.stream(Direction.values())
                .map(direction -> gameMap.getFromCords(t.getCord().add(direction.getCords())))
                .filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
    }


    private List<T> getAroundDiagonal(T t){
        List<Cord> cordList =new ArrayList<>();
        cordList.add(Direction.UP.getCords().add(Direction.RIGHT.getCords()));
        cordList.add(Direction.UP.getCords().add(Direction.LEFT.getCords()));
        cordList.add(Direction.DOWN.getCords().add(Direction.RIGHT.getCords()));
        cordList.add(Direction.DOWN.getCords().add(Direction.LEFT.getCords()));
        return cordList.parallelStream().map(direction -> gameMap.getFromCords(t.getCord().add(direction)))
                .filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
    }


}
