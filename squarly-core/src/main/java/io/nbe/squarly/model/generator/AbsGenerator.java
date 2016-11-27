package io.nbe.squarly.model.generator;

import io.nbe.squarly.model.GameGenerator;
import io.nbe.squarly.model.generator.wrapper.GameMapWrapper;
import io.nbe.squarly.model.interfaces.ICoordinateSquare;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A generator for making a dungeon
 * @param <T> the squares inside  the dungeon
 * @author Nicolas Beaussart
 */
public abstract class AbsGenerator<T extends ICoordinateSquare> {

    private static final Logger log = LoggerFactory.getLogger(AbsGenerator.class);

    private GameGenerator<T> gameGenerator;
    private GameMapWrapper<T> mapWrapper;
    private Random r = new Random();

    /**
     * creating a generator
     * @param gameGenerator the gamegenerator that this generator is using
     */
    public AbsGenerator(GameGenerator<T> gameGenerator) {
        this.gameGenerator = checkNotNull(gameGenerator);
    }

    /**
     * Creatinng a generator
     */
    public AbsGenerator() {

    }

    public void setGameGenerator(GameGenerator<T> gameGenerator) {
        this.gameGenerator = checkNotNull(gameGenerator);
    }

    /**
     * generate the dungeon based of this generator
     */
    public void generate(){
        mapWrapper = new GameMapWrapper<>(checkNotNull(gameGenerator, "Game map is null").getGameMap());
        log.debug("Starting to generate dungeon");
        long start = System.currentTimeMillis();
        doGenerate();
        long end = System.currentTimeMillis();
        log.debug("Done in {} ms", end-start);
    }

    protected abstract void doGenerate();


    public GameGenerator<T> getGameGenerator() {
        return gameGenerator;
    }

    public GameMapWrapper<T> getMapWrapper() {
        return mapWrapper;
    }

    public Random getR() {
        return r;
    }

    public void setR(Random r) {
        this.r = r;
    }
}
