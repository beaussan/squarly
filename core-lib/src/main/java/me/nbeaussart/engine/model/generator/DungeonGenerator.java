package me.nbeaussart.engine.model.generator;

import me.nbeaussart.engine.model.Cord;
import me.nbeaussart.engine.model.Direction;
import me.nbeaussart.engine.model.GameGenerator;
import me.nbeaussart.engine.model.interfaces.ICoordinateSquare;
import me.nbeaussart.engine.model.interfaces.IGameMap;
import me.nbeaussart.engine.model.interfaces.IState;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author Nicolas Beaussart
 * @since 31/10/16
 */
public class DungeonGenerator<T extends ICoordinateSquare> extends AbsGenerator<T> {

    private IGameMap<T> gameMap;
    private List<T> mapData;

    public DungeonGenerator(GameGenerator<T> gameGenerator) {
        super(gameGenerator);
    }

    public DungeonGenerator() {
        super();
    }

    @Override
    public void doGenerate() {

    }






}
