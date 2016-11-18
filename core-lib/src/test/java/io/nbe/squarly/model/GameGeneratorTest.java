package io.nbe.engine.model;

import com.google.common.base.MoreObjects;
import io.nbe.engine.model.generator.AbsGenerator;
import io.nbe.engine.model.generator.DungeonGenerator;
import io.nbe.engine.model.generator.MazeGenerator;
import io.nbe.engine.model.generator.MazeGeneratorClean;
import io.nbe.engine.model.interfaces.ICoordinateSquare;
import io.nbe.engine.model.interfaces.IGameMap;
import io.nbe.engine.model.interfaces.IState;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Nicolas Beaussart
 * @since 13/11/16
 */
public class GameGeneratorTest {
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

            startingSquare.setVisited(true);

            List<CaraSquare> nonVisited = new ArrayList<CaraSquare>();
            nonVisited.addAll(startingSquare.getNeiNonVisited());

            while (!nonVisited.isEmpty()){
                CaraSquare remove = nonVisited.remove(0);
                remove.setVisited(true);
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

    @Test
    public void addPostProsessor() throws Exception {

        GameMap gm = new GameMap(30,30);
        final boolean[] isGood = {false};
        new GameGenerator<CaraSquare>(gm).useMazeGeneratorClean().addPostProsessor(caraSquares -> {
            isGood[0] = true;
        }).generate();
        assertThat(isGood[0]).isTrue().describedAs("Post Porossesor called");
    }

    @Test
    public void generate() throws Exception {

    }



    private class CaraSquare implements ICoordinateSquare {

        private final Cord cord;
        private IState state = IState.WALL;
        private final GameMap gameMap;
        private boolean visited;

        private CaraSquare(Cord cord, GameMap gameMap) {
            this.cord = cord;
            this.gameMap = gameMap;
            visited = false;
        }

        @Override
        public List<CaraSquare> getNeighbors() {
            return Arrays.stream(Direction.values())
                    .map(direction ->  getGameMap().getFromCords(getCord().add(direction)))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
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
        public Cord getCord() {
            return cord;
        }

        @Override
        public GameMap getGameMap() {
            return gameMap;
        }

        @Override
        public void setUpdated() {
            gameMap.setChanged(this);
        }

        @Override
        public IState getState() {
            return state;
        }

        @Override
        public void setState(IState state) {
            this.state = state;
        }

        public boolean isVisited() {
            return visited;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("cord", cord)
                    .add("state", state)
                    .add("visited", visited)
                    .toString();
        }
    }

    private class GameMap implements IGameMap<CaraSquare> {

        Map<Cord, CaraSquare> mapdata = new HashMap<>();
        List<Consumer<Optional<CaraSquare>>> updatesHandleurs = new ArrayList<>();
        private final int sizex;
        private final int sizey;

        private GameMap(int sizex, int sizey) {
            this.sizex = sizex;
            this.sizey = sizey;
            for (int x = 0; x < sizex; x++) {
                for (int y = 0; y < sizey; y++) {
                    mapdata.put(Cord.get(x,y),new CaraSquare(Cord.get(x,y), this));
                }
            }
            System.out.println(mapdata.get(Cord.get(0, 0)));
            System.out.println(mapdata.get(Cord.get(sizex, sizey)));
            System.out.println(mapdata.size());
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
    }




}