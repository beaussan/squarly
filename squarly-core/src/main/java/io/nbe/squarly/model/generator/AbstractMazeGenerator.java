package io.nbe.squarly.model.generator;

import io.nbe.squarly.model.Cord;
import io.nbe.squarly.model.GameGenerator;
import io.nbe.squarly.model.generator.wrapper.GameMapWrapper;
import io.nbe.squarly.model.generator.wrapper.SquareWrapper;
import io.nbe.squarly.model.interfaces.ICoordinateSquare;
import io.nbe.squarly.model.interfaces.IState;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A abtract maze generator
 * @param <T> the type of tiles
 * @author Nicolas Beaussart
 */
public abstract class AbstractMazeGenerator<T extends ICoordinateSquare> extends AbsGenerator<T> {

    /**
     * Create a maze generator with a game generator passez in parameter
     * @param gameGenerator the gameGenerator
     */
    public AbstractMazeGenerator(GameGenerator<T> gameGenerator) {
        super(gameGenerator);
    }

    /**
     * Create a maze generator
     */
    public AbstractMazeGenerator() {
    }

    @Override
    public void doGenerate() {

        GameMapWrapper<T> mapWrapper = getMapWrapper();

        List<SquareWrapper<T>> wallList = new ArrayList<>();


        Optional<SquareWrapper<T>> fromCords = mapWrapper.getFromCords(Cord.get(1, 1));

        if (fromCords.isPresent()) {
            fromCords.get().setState(IState.ROOM);
            wallList.addAll(fromCords.get().getNeighs(IState.WALL));

        } else {
            throw new IllegalStateException("The map is too small");
        }

        while (!wallList.isEmpty()){
            SquareWrapper<T> rmd = wallList.remove(getR().nextInt(wallList.size()));
            handleSquare(wallList, rmd);
        }
    }

    protected abstract void handleSquare(List<SquareWrapper<T>> wallList, SquareWrapper<T> rmd);


}
