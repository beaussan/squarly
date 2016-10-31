package me.nbeaussart.engine.model;

import me.nbeaussart.engine.model.generator.AbsGenerator;
import me.nbeaussart.engine.model.generator.MazeGenerator;
import me.nbeaussart.engine.model.generator.MazeGeneratorClean;
import me.nbeaussart.engine.model.interfaces.IColoredSquare;
import me.nbeaussart.engine.model.interfaces.ICoordinateSquare;
import me.nbeaussart.engine.model.interfaces.IGameMap;
import me.nbeaussart.engine.model.interfaces.IState;
import me.nbeaussart.engine.view.GameScreen;
import me.nbeaussart.engine.view.MapPrinter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * @author Nicolas Beaussart
 * @since 27/10/16
 */
public class GameGenerator<T extends ICoordinateSquare> {

    private final static Color COLOR_WALL=new Color(0,0,0);
    private final static Color COLOR_ROOM=new Color(255,255,255);
    private final static Color COLOR_DOOR=new Color(255,0,0);
    private final static long PAUSE_DURATION = 5;

    private final IGameMap<T> gameMap;
    private final boolean isShowing;
    private AbsGenerator<T> usedGenerator;
    private List<Consumer<List<T>>> postProssesors = new ArrayList<>();

    public GameGenerator(IGameMap<T> gameMap) {
        this.gameMap = checkNotNull(gameMap, "GameMap should not be null");
        isShowing = false;
        checkIntegrityMap();
        setupMap();
    }
    public GameGenerator(IGameMap<T> gameMap, boolean debugPrinting) {
        this.gameMap = checkNotNull(gameMap, "GameMap should not be null");
        isShowing = debugPrinting;
        checkIntegrityMap();
        setupMap();
        if (isShowing){
            setupShowing();
        }
    }

    public GameGenerator<T> addPostProsessor(Consumer<List<T>> constor){
        postProssesors.add(constor);
        return this;
    }

    public GameGenerator<T> useGenerator(AbsGenerator<T> generator){
        generator.setGameGenerator(this);
        usedGenerator = generator;
        return this;
    }

    public GameGenerator<T> useMazeGenerator(){
        usedGenerator = new MazeGenerator<T>(this);
        return this;
    }

    public GameGenerator<T> useMazeGeneratorClean(){
        usedGenerator = new MazeGeneratorClean<T>(this);
        return this;
    }

    public IGameMap<T> generate(){
        usedGenerator.generate();
        postProssesors.forEach(tConsumer -> tConsumer.accept(gameMap.getMapData()));
        return gameMap;
    }

    private void setupShowing(){
        GameMapWrapper gm = new GameMapWrapper();
        gm.setMapData(
                gameMap.getMapData().stream().map( t -> new ColorWrapper(t, gm)).collect(Collectors.toList())
        );
        MapPrinter<ColorWrapper> map = new MapPrinter<>(gm);
        GameScreen.createGameScreen("GENERATOR", map);
    }



    private void checkIntegrityMap(){
        gameMap.removeOutOfBounds();
        gameMap.removeDuplicate();
        checkState(gameMap.sizeX() > 0, "SizeX should be over 0");
        checkState(gameMap.sizeY() > 0, "SizeY should be over 0");
        checkState(checkNotNull(gameMap.getMapData(), "Map data should not be null").size() ==
                gameMap.sizeX()*gameMap.sizeY(), "The map is not the correct lenght, expected %s but got %s tiles",
                gameMap.sizeX()*gameMap.sizeY(), gameMap.getMapData().size());
    }


    private void setupMap(){
        gameMap.getMapData().forEach(t -> t.setState(IState.WALL));
    }

    public IGameMap<T> getGameMap() {
        return gameMap;
    }

    public boolean isShowing() {
        return isShowing;
    }

    private class GameMapWrapper implements IGameMap<ColorWrapper>{
        public List<ColorWrapper> wrapper = new ArrayList<>();
        private  List<Consumer<Optional<ColorWrapper>>> data = new ArrayList<>();
        @Override
        public List<ColorWrapper> getMapData() {
            return wrapper;
        }

        @Override
        public void setMapData(List<ColorWrapper> data) {
            this.wrapper = data;
        }

        @Override
        public List<Consumer<Optional<ColorWrapper>>> getUpdatesHandlers() {
            return data;
        }

        @Override
        public int sizeX() {
            return gameMap.sizeX();
        }

        @Override
        public int sizeY() {
            return gameMap.sizeY();
        }

        @Override
        public int getHeightPixel() {
            return gameMap.getHeightPixel();
        }

        @Override
        public int getWidthPixel() {
            return gameMap.getWidthPixel();
        }
    }

    private class ColorWrapper implements IColoredSquare, ICoordinateSquare {
        private T t;
        private GameMapWrapper gmw;
        ColorWrapper(T t, GameMapWrapper gmw){
            this.t = t;
            this.gmw = gmw;
            gameMap.addUpdatesHandlers(t1 -> {
                t1.ifPresent(t2 -> {
                    if (t2.getCord().equals(getCord())){
                        setUpdated();
                        try {
                            System.out.println("Sleeping");
                            Thread.sleep(PAUSE_DURATION);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                setUpdated();

            });
        }
        @Override
        public Color getColor() {
            switch (t.getState()) {
                case DOOR:
                    return COLOR_DOOR;
                case ROOM:
                    return COLOR_ROOM;
                case WALL:
                    return COLOR_WALL;
            }
            return null;
        }

        @Override
        public Cord getCord() {
            return t.getCord();
        }

        @Override
        public IGameMap<? extends ICoordinateSquare> getGameMap() {
            return t.getGameMap();
        }

        @Override
        public void setUpdated() {
            gmw.setChanged(this);
        }

        @Override
        public IState getState() {
            return t.getState();
        }

        @Override
        public void setState(IState state) {

        }
    }
}