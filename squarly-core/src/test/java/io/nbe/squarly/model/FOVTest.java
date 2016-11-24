package io.nbe.squarly.model;

import com.google.common.base.MoreObjects;
import io.nbe.squarly.model.interfaces.ICoordinateSquare;
import io.nbe.squarly.model.interfaces.IGameMap;
import io.nbe.squarly.model.interfaces.IState;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;


/**
 * @author Nicolas Beaussart
 * @since 24/11/16
 */
public class FOVTest {
    @Test
    public void generateFOV() throws Exception {
        GameMap gameMap = new GameMap(10, 10);
        setState(gameMap, Cord.get(5, 5), IState.ROOM);
        setState(gameMap, Cord.get(5, 6), IState.ROOM);
        setState(gameMap, Cord.get(5, 7), IState.ROOM);
        setState(gameMap, Cord.get(6, 5), IState.ROOM);
        setState(gameMap, Cord.get(7, 5), IState.ROOM);
        gameMap.getMapData().put(Cord.get(8,5), null);

        Assertions.assertThat(
                new FOV<CaraSquare>()
                        .generateFOV(Cord.get(5,5), gameMap)
                        .stream()
                        .map(CaraSquare::getCord)
                        .collect(Collectors.toList()))
        .containsExactlyInAnyOrder(Cord.get(5,5),
                Cord.get(5,6),
                Cord.get(5,7),
                Cord.get(6,5),
                Cord.get(7,5));
    }

    private void setState(GameMap gameMap, Cord key, IState state) {
        gameMap.getFromCords(key).ifPresent(caraSquare -> caraSquare.setState(state));
    }


    private class CaraSquare implements ICoordinateSquare {

        private final Cord cord;
        private final GameMap gameMap;
        private IState state = IState.WALL;

        private CaraSquare(Cord cord, GameMap gameMap) {
            this.cord = cord;
            this.gameMap = gameMap;
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
        public IState getState() {
            return state;
        }

        @Override
        public void setState(IState state) {
            this.state = state;
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
                    .toString();
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
    }

}
