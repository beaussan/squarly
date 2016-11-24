package io.nbe.squarly.model;

import io.nbe.squarly.model.generator.AbsGenerator;
import io.nbe.squarly.model.generator.DungeonGenerator;
import io.nbe.squarly.model.generator.MazeGenerator;
import io.nbe.squarly.model.generator.MazeGeneratorClean;
import io.nbe.squarly.model.interfaces.IColoredSquare;
import io.nbe.squarly.model.interfaces.ICoordinateSquare;
import io.nbe.squarly.model.interfaces.IState;
import io.nbe.squarly.view.GameScreen;
import io.nbe.squarly.view.MapPrinter;
import io.nbe.squarly.model.interfaces.IGameMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;


/**
 * A class for generating a dungeon
 * @param <T> the type of cells in the dungeon
 * @author Nicolas Beaussart
 */
public class GameGenerator<T extends ICoordinateSquare> {

    private static final Color COLOR_WALL=new Color(0,0,0);
    private static final Color COLOR_ROOM=new Color(255,255,255);
    private static final Color COLOR_DOOR=new Color(255,0,0);
    private static final Color COLOR_DEFAULT = new Color(140, 140, 140);
    private static final long PAUSE_DURATION = 5;
    private static final Logger log = LoggerFactory.getLogger(GameGenerator.class);

    private final IGameMap<T> gameMap;
    private final boolean isShowing;
    private AbsGenerator<T> usedGenerator;

    private LinkedList<Consumer<List<T>>> postProssesors = new LinkedList<>();

    /**
     * Create a generator with the game map in parameters
     * @param gameMap the game map to generate on
     */
    public GameGenerator(IGameMap<T> gameMap) {
        this.gameMap = checkNotNull(gameMap, "GameMap should not be null");
        isShowing = false;
        checkIntegrityMap();
        setupMap();
    }
    /**
     * Create a generator with the game map in parameters
     * @param gameMap the game map to generate on
     * @param debugPrinting if set to true, will display a debug visual interface of the generation
     */
    public GameGenerator(IGameMap<T> gameMap, boolean debugPrinting) {
        this.gameMap = checkNotNull(gameMap, "GameMap should not be null");
        isShowing = debugPrinting;
        checkIntegrityMap();
        setupMap();
        if (isShowing){
            setupShowing();
        }
    }

    /**
     * Add a post prosessor to the dungeon, will be called in order after the dungeon is generated.
     * @param constor the consumer to add
     * @return this object
     */
    public GameGenerator<T> addPostProsessor(Consumer<List<T>> constor){
        postProssesors.add(constor);
        log.trace("Adding post prosessor");
        return this;
    }

    /**
     * Use a Generator for this generation
     * @param generator a generator
     * @return this object
     */
    public GameGenerator<T> useGenerator(AbsGenerator<T> generator){
        generator.setGameGenerator(this);
        usedGenerator = generator;
        log.debug("Using generator {}", generator);
        return this;
    }

    /**
     * Use a <code>{@link MazeGenerator}</code> for generation
     * @return this object
     */
    public GameGenerator<T> useMazeGenerator(){
        usedGenerator = new MazeGenerator<>(this);
        log.debug("Usinging mazeGenerator");
        return this;
    }

    /**
     * Use a <code>{@link MazeGeneratorClean}</code> for generation
     * @return this object
     */
    public GameGenerator<T> useMazeGeneratorClean(){
        usedGenerator = new MazeGeneratorClean<>(this);
        log.debug("Using MazeGeneratorClean");
        return this;
    }

    /**
     * Use a <code>{@link DungeonGenerator}</code> for generation
     * @return this object
     */

    public GameGenerator<T> useDungeonGenerator(){
        usedGenerator = new DungeonGenerator<>(this);
        log.debug("Using DungeonGenerator");
        return this;
    }

    public AbsGenerator<T> getUsedGenerator() {
        return usedGenerator;
    }

    /**
     * Generate the dungeon
     * @return the map generated
     */
    public IGameMap<T> generate(){
        log.debug("Starting to generate dungeon");
        checkNotNull(usedGenerator, "The generator was null").generate();
        log.debug("Generating done, lanching postProsessors");

        while (! postProssesors.isEmpty())
            postProssesors.remove().accept(new ArrayList<>(gameMap.getMapData().values()));
        gameMap.setChanged(null);
        log.debug("My job is done.");
        return gameMap;
    }

    private void setupShowing(){
        log.debug("Using showing setings");
        GameMapWrapper gm = new GameMapWrapper();
        gm.setMapData(
                gameMap.getMapData().values()
                        .stream().map(t ->  new ColorWrapper(t, gm))
                        .collect(Collectors.toMap(ColorWrapper::getCord, o -> o))
        );
        MapPrinter<ColorWrapper> map = new MapPrinter<>(gm);
        GameScreen.createGameScreen("GENERATOR", map);
    }



    private void checkIntegrityMap(){
        gameMap.removeOutOfBounds();
        checkState(gameMap.sizeX() > 0, "SizeX should be over 0");
        checkState(gameMap.sizeY() > 0, "SizeY should be over 0");
        checkState(checkNotNull(gameMap.getMapData(), "Map data should not be null").size() ==
                gameMap.sizeX()*gameMap.sizeY(), "The map is not the correct lenght, expected %s but got %s tiles",
                gameMap.sizeX()*gameMap.sizeY(), gameMap.getMapData().size());
    }


    private void setupMap(){
        gameMap.getMapData().values().forEach(t -> t.setState(IState.WALL));
    }

    public IGameMap<T> getGameMap() {
        return gameMap;
    }

    public boolean isShowing() {
        return isShowing;
    }

    private class GameMapWrapper implements IGameMap<ColorWrapper>{
        private Map<Cord, ColorWrapper> wrapper = new HashMap<>();
        private  List<Consumer<Optional<ColorWrapper>>> data = new ArrayList<>();

        @Override
        public Map<Cord, ColorWrapper> getMapData() {
            return wrapper;
        }

        @Override
        public void setMapData(Map<Cord, ColorWrapper> data) {
            wrapper = data;
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
                            Thread.sleep(PAUSE_DURATION);
                        } catch (InterruptedException e) {
                            log.warn("Error was thrown when sleeping for pause {}", e);
                            Thread.currentThread().interrupt();
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
                default:
                    return COLOR_DEFAULT;
            }
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
            t.setState(state);
        }
    }
}
