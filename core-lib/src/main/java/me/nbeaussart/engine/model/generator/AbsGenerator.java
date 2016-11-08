package me.nbeaussart.engine.model.generator;

import me.nbeaussart.engine.model.Color;
import me.nbeaussart.engine.model.GameGenerator;
import me.nbeaussart.engine.model.generator.wrapper.GameMapWrapper;
import me.nbeaussart.engine.model.interfaces.IColoredSquare;
import me.nbeaussart.engine.model.interfaces.ICoordinateSquare;
import me.nbeaussart.engine.model.interfaces.IGameMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * @author Nicolas Beaussart
 * @since 27/10/16
 */
public abstract class AbsGenerator<T extends ICoordinateSquare> {

    private static final Logger log = LoggerFactory.getLogger(AbsGenerator.class);

    private GameGenerator<T> gameGenerator;
    private GameMapWrapper<T> mapWrapper;
    Random r = new Random(System.currentTimeMillis());

    public AbsGenerator(GameGenerator<T> gameGenerator) {
        this.gameGenerator = checkNotNull(gameGenerator);
    }
    public AbsGenerator() {
    }

    public void setGameGenerator(GameGenerator<T> gameGenerator) {
        this.gameGenerator = checkNotNull(gameGenerator);
    }

    public void generate(){
        checkState(gameGenerator != null, "Game map is null");
        mapWrapper = new GameMapWrapper<T>(gameGenerator.getGameMap());
        log.debug("Starting to generate dungeon");
        long start = System.currentTimeMillis();
        doGenerate();
        long end = System.currentTimeMillis();
        log.debug("Done in {} ms", end-start);
    }

    public abstract void doGenerate();


    public GameGenerator<T> getGameGenerator() {
        return gameGenerator;
    }

    public GameMapWrapper<T> getMapWrapper() {
        return mapWrapper;
    }
}
