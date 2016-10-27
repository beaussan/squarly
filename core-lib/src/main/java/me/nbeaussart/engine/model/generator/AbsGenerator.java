package me.nbeaussart.engine.model.generator;

import me.nbeaussart.engine.model.Color;
import me.nbeaussart.engine.model.GameGenerator;
import me.nbeaussart.engine.model.interfaces.IColoredSquare;
import me.nbeaussart.engine.model.interfaces.ICoordinateSquare;
import me.nbeaussart.engine.model.interfaces.IGameMap;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Nicolas Beaussart
 * @since 27/10/16
 */
public abstract class AbsGenerator<T extends ICoordinateSquare> {


    private GameGenerator<T> gameGenerator;

    public AbsGenerator(GameGenerator<T> gameGenerator) {
        this.gameGenerator = checkNotNull(gameGenerator);
    }


    public abstract void generate();


    public GameGenerator<T> getGameGenerator() {
        return gameGenerator;
    }

}
