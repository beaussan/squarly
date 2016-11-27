package io.nbe.squarly.model;

import com.google.common.base.*;
import com.google.common.base.Objects;
import io.nbe.squarly.model.generator.AbsGenerator;
import io.nbe.squarly.model.generator.DungeonGenerator;
import io.nbe.squarly.model.generator.MazeGenerator;
import io.nbe.squarly.model.generator.MazeGeneratorClean;
import io.nbe.squarly.model.interfaces.ICoordinateSquare;
import io.nbe.squarly.model.interfaces.IGameMap;
import io.nbe.squarly.model.interfaces.IState;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.*;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;

/**
 * @author Nicolas Beaussart
 * @since 13/11/16
 */
public class GameGeneratorTest {
    @Test
    public void addPostProsessor() throws Exception {

        GameMap gm = new GameMap(30,30);
        final boolean[] isGood = {false};
        new GameGenerator<>(gm).useMazeGeneratorClean().addPostProsessor(caraSquares -> {
            isGood[0] = true;
        }).generate();
        assertThat(isGood[0]).isTrue().describedAs("Post Porossesor called");
    }

    @Test
    public void testSameWithSameRandom() throws Exception {
        Random r = new Random(30);
        Random r2 = new Random(30);
        assertThat(new GameGenerator<>(new GameMap(30,30))
                .useMazeGeneratorClean()
                .useRandom(r)
                .generate())
                .isEqualTo(
                        new GameGenerator<>(new GameMap(30,30))
                                .useMazeGeneratorClean()
                                .useRandom(r2)
                                .generate());

    }


    @Test
    public void testSameWithSameSeed() throws Exception {
        String seed = "hello";
        assertThat(new GameGenerator<>(new GameMap(30,30))
                .useMazeGeneratorClean()
                .useSeed(seed)
                .generate())
                .isEqualTo(
                        new GameGenerator<>(new GameMap(30,30))
                                .useMazeGeneratorClean()
                                .useSeed(seed)
                                .generate());

    }

    @Test
    public void testGetterGenerator() throws Exception {

        GameGenerator<CaraSquare> generator = new GameGenerator<>(new GameMap(30, 30)).useMazeGeneratorClean().useSeed("seed");
        assertThat(generator.getUsedGenerator().getGameGenerator()).isEqualTo(generator);
    }

    @Test
    public void testGeneratorFail() throws Exception {
        assertThatThrownBy(() -> new AbsGenerator<CaraSquare>() {
                @Override
                public void doGenerate() {

                }
            }.generate())
                .isInstanceOf(NullPointerException.class)
                .describedAs("Game map is null");

    }


    @Test
    public void drawedGood() throws Exception {

        new GameGenerator<>(new GameMap(30,30), true)
                .useMazeGeneratorClean()
                .useSeed("seed")
                .generate();
        //System.out.println(gm.getUpdatesHandlers());
    }
    @Test
    public void testEqualsSetters() throws Exception {
        GameMap gm = new GameMap(30,30);
        assertThat(new GameGenerator<>(gm).useDungeonGenerator().getUsedGenerator())
                .isInstanceOf(DungeonGenerator.class);
        assertThat(new GameGenerator<>(gm).useMazeGenerator().getUsedGenerator())
                .isInstanceOf(MazeGenerator.class);
        assertThat(new GameGenerator<>(gm).useMazeGeneratorClean().getUsedGenerator())
                .isInstanceOf(MazeGeneratorClean.class);
    }


    @Test
    public void addPostProsessorInOrder() throws Exception {

        GameMap gm = new GameMap(30,30);
        final boolean[] isGood = {false, false};
        new GameGenerator<CaraSquare>(gm)
                .useMazeGeneratorClean()
                .addPostProsessor(caraSquares -> {
                    isGood[0] = true;
                    if (isGood[1]){
                        fail("The order wasen't respected, 2 got fired before 1");
                    }
                })
                .addPostProsessor(caraSquares -> {
                    isGood[1] = true;
                    if (!isGood[0]) {
                        fail("The order wasen't respected, 2 got fired before 1");
                    }
                })

                .generate();
        assertThat(isGood).describedAs("All postProsessor called correctly").containsOnly(true);
    }

    @Test
    public void generateExceptionsizeX() throws Exception {
        final GameMap gm = new GameMap(0,30);
        assertThatThrownBy(() -> new GameGenerator<>(gm))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("SizeX should be over 0");
    }

    @Test
    public void generateExceptionSizeY() throws Exception {
        final GameMap gm = new GameMap(30,0);
        assertThatThrownBy(() -> new GameGenerator<>(gm))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("SizeY should be over 0");
    }
    @Test
    public void generateExceptionNotNull() throws Exception {
        assertThatThrownBy(() -> new GameGenerator<>(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("GameMap should not be null");
    }

    @Test
    public void generateExceptionNotNullData() throws Exception {
        final GameMap gm = new GameMap(30,30);
        gm.setMapData(null);
        assertThatThrownBy(() -> new GameGenerator<>(gm))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Map data should not be null");
    }
    @Test
    public void generateExceptionIncorectSize() throws Exception {
        final GameMap gm = new GameMap(30,30);
        gm.getMapData().remove(Cord.get(0,0));
        assertThatThrownBy(() -> new GameGenerator<>(gm))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("The map is not the correct lenght, expected 900 but got 899 tiles");
    }

    /*


    private void checkIntegrityMap(){
        gameMap.removeOutOfBounds();
        checkState(checkNotNull(gameMap.getMapData(), "Map data should not be null").size() ==
                gameMap.sizeX()*gameMap.sizeY(), "The map is not the correct lenght, expected %s but got %s tiles",
                gameMap.sizeX()*gameMap.sizeY(), gameMap.getMapData().size());
    }

     */

    @Test
    public void testAllConnectedEveryThing() throws Exception {
        List<AbsGenerator<CaraSquare>> lsGen = new ArrayList<>();
        lsGen.add(new DungeonGenerator<CaraSquare>());
        lsGen.add(new MazeGeneratorClean<>());
        lsGen.add(new MazeGenerator<>());

        lsGen.forEach(generator -> {
            GameMap gm = new GameMap(30,30);

            new GameGenerator<CaraSquare>(gm).useGenerator(generator).generate();

            Map<Cord, CaraSquare> mapData = gm.getMapData();

            CaraSquare startingSquare = null;
            for (CaraSquare caraSquare : mapData.values()) {
                if (caraSquare.getState() == IState.ROOM || caraSquare.getState() == IState.DOOR){
                    startingSquare = caraSquare;
                    break;
                }
            }
            if (startingSquare == null){
                Assertions.fail("We don't have any room or door for the generator " + generator + " !");
                return;
            }

            startingSquare.setVisited();

            List<CaraSquare> nonVisited = new ArrayList<CaraSquare>();
            nonVisited.addAll(startingSquare.getNeiNonVisited());

            while (!nonVisited.isEmpty()){
                CaraSquare remove = nonVisited.remove(0);
                remove.setVisited();
                nonVisited.addAll(remove.getNeiNonVisited());
                nonVisited.removeIf(CaraSquare::isVisited);
            }

            long unvisites = mapData.values().parallelStream()
                    .filter(caraSquare -> caraSquare.getState() != IState.WALL)
                    .filter(caraSquare -> !caraSquare.isVisited())
                    .count();

            assertThat(unvisites).isEqualTo(0).as("There is non connected squaure");



        });
    }

    private class CaraSquare implements ICoordinateSquare {

        private final Cord cord;
        private final GameMap gameMap;
        private IState state = IState.WALL;
        private boolean visited;

        private CaraSquare(Cord cord, GameMap gameMap) {
            this.cord = cord;
            this.gameMap = gameMap;
            visited = false;
        }

        @Override
        public Cord getCord() {
            return cord;
        }

        @Override
        public GameMap getGameMap() {
            return gameMap;
        }

        public List<CaraSquare> getNeiNonVisited(){
            return Arrays.stream(Direction.values())
                    .map(direction ->  getGameMap().getFromCords(getCord().add(direction)))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .filter((caraSquare) -> !caraSquare.isVisited())
                    .filter(caraSquare -> caraSquare.getState() != IState.WALL)
                    .collect(Collectors.toList());
        }

        @Override
        public List<CaraSquare> getNeighbors() {
            return Arrays.stream(Direction.values())
                    .map(direction ->  getGameMap().getFromCords(getCord().add(direction)))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
        }

        @Override
        public IState getState() {
            return state;
        }

        @Override
        public void setState(IState state) {
            this.state = state;
            setUpdated();
        }

        public boolean isVisited() {
            return visited;
        }

        public void setVisited() {
            this.visited = true;
        }

        @Override
        public void setUpdated() {
            gameMap.setChanged(this);
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("cord", cord)
                    .add("state", state)
                    .add("visited", visited)
                    .toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CaraSquare that = (CaraSquare) o;
            return visited == that.visited &&
                    Objects.equal(cord, that.cord) &&
                    // Objects.equal(gameMap, that.gameMap) &&
                    state == that.state;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(cord, state, visited);
        }
    }

    private class GameMap implements IGameMap<CaraSquare> {

        private final int sizex;
        private final int sizey;
        Map<Cord, CaraSquare> mapdata = new HashMap<>();
        List<Consumer<Optional<CaraSquare>>> updatesHandleurs = new ArrayList<>();

        private GameMap(int sizex, int sizey) {
            this.sizex = sizex;
            this.sizey = sizey;
            for (int x = 0; x < sizex; x++) {
                for (int y = 0; y < sizey; y++) {
                    mapdata.put(Cord.get(x,y),new CaraSquare(Cord.get(x,y), this));
                }
            }
        }

        @Override
        public Map<Cord, CaraSquare> getMapData() {
            return mapdata;
        }

        @Override
        public void setMapData(Map<Cord, CaraSquare> data) {
            mapdata = data;
        }

        @Override
        public List<Consumer<Optional<CaraSquare>>> getUpdatesHandlers() {
            return updatesHandleurs;
        }

        @Override
        public int sizeX() {
            return sizex;
        }

        @Override
        public int sizeY() {
            return sizey;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GameMap gameMap = (GameMap) o;
            return sizex == gameMap.sizex &&
                    sizey == gameMap.sizey &&
                    Objects.equal(mapdata, gameMap.mapdata) &&
                    Objects.equal(updatesHandleurs, gameMap.updatesHandleurs);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(sizex, sizey, mapdata, updatesHandleurs);
        }
    }




}