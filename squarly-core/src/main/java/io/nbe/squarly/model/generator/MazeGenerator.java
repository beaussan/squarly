package io.nbe.squarly.model.generator;

import io.nbe.squarly.model.GameGenerator;
import io.nbe.squarly.model.generator.wrapper.SquareWrapper;
import io.nbe.squarly.model.interfaces.ICoordinateSquare;
import io.nbe.squarly.model.interfaces.IState;

import java.util.List;

/**
 * create a maze generator
 * @param <T> the square type
 * @author Nicolas Beaussart
 */
public class MazeGenerator<T extends ICoordinateSquare> extends AbstractMazeGenerator<T> {
    /**
     * Creating a maze generator
     * @param gameGenerator the gameGenerator wrapper
     */
    public MazeGenerator(GameGenerator<T> gameGenerator) {
        super(gameGenerator);
    }

    /**
     * Creating a maze generator
     */
    public MazeGenerator() {
        super();
    }

    @Override
    protected void handleSquare(List<SquareWrapper<T>> wallList, SquareWrapper<T> rmd) {
        if (rmd.getNeighs().size() != 4){
            return;
        }
        if (rmd.getNeighs(IState.ROOM).size() == 1){
            rmd.setState(IState.ROOM);
            wallList.addAll(rmd.getNeighs(IState.WALL));
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
