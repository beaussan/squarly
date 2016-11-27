package io.nbe.squarly.model.generator;

import io.nbe.squarly.model.generator.wrapper.GameMapWrapper;
import io.nbe.squarly.model.generator.wrapper.Room;
import io.nbe.squarly.model.interfaces.ICoordinateSquare;
import io.nbe.squarly.util.RmdUtils;
import io.nbe.squarly.model.GameGenerator;
import io.nbe.squarly.model.generator.wrapper.SquareWrapper;
import io.nbe.squarly.model.interfaces.IState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Generate a dungeon
 * @param <T> the type of game squares in the map
 * @author Nicolas Beaussart
 */
public class DungeonGenerator<T extends ICoordinateSquare> extends AbsGenerator<T> {

    private static final Logger log = LoggerFactory.getLogger(DungeonGenerator.class);

    private GameMapWrapper<T> gameMap;
    private final int nmbRoomTry;
    private final int roomSizeMin;
    private final int roomAddingRmdBound;
    private List<Room<T>> lsRooms = new ArrayList<>();


    /**
     * Generate a dungeon based on the gameGenerator
     * @param gameGenerator the game generator to work on
     */
    public DungeonGenerator(GameGenerator<T> gameGenerator) {
        super(gameGenerator);
        nmbRoomTry = 100;
        roomSizeMin = 5;
        roomAddingRmdBound = 3;
    }

    /**
     * Generate a dungeon with rooms
     */
    public DungeonGenerator() {
        super();
        nmbRoomTry = 100;
        roomSizeMin = 5;
        roomAddingRmdBound = 3;
    }

    /**
     * Generate a dungeon with custom properties
     * @param nmbRoomTry the number of try
     * @param roomSizeMin the room size, must be odd
     * @param roomAddingRmdBound the number of room to add
     */
    public DungeonGenerator(int nmbRoomTry, int roomSizeMin, int roomAddingRmdBound) {
        super();
        this.nmbRoomTry = nmbRoomTry;
        this.roomSizeMin = roomSizeMin;
        this.roomAddingRmdBound = roomAddingRmdBound;
    }

    @Override
    public void doGenerate() {
        gameMap = getMapWrapper();
        generateRooms();
        genrateWays();
        connectRooms();
        removeUnessery();
    }

    private void removeUnessery(){
        log.trace("starting removing extra square");
        List<SquareWrapper<T>> data = gameMap.getMapData().values()
                .parallelStream()
                .filter(tSquareWrapper -> tSquareWrapper.getState() == IState.ROOM)
                .collect(Collectors.toList());
        final boolean[] isdone = {false};
        while (!isdone[0]){
            isdone[0] = true;
            data.forEach(tSquareWrapper -> {
                if (tSquareWrapper.getState() == IState.ROOM
                        && tSquareWrapper.getNeighs(IState.ROOM).size() <= 1) {
                    tSquareWrapper.setState(IState.WALL);
                    isdone[0] = false;
                }
            });
        }
        log.trace("Done");


    }

    private void connectRooms() {
        log.trace("Starting to connecting rooms");
        lsRooms.forEach(tRoom -> {
            List<SquareWrapper<T>> neigh = tRoom.getNeigh();

            RmdUtils.getRandom(neigh, getR()).setState(IState.ROOM);
            neigh.forEach(tSquareWrapper -> {
                if (getR().nextInt(10) > 8){
                    tSquareWrapper.setState(IState.ROOM);
                }
            });
        });
        log.trace("Done adding random connectors, gonna mark one if connected");
        Optional<SquareWrapper<T>> startOPt = gameMap.getFromCords(lsRooms.get(0).getStartingCords());
        SquareWrapper<T> start;
        if (startOPt.isPresent()) {
            start = startOPt.get();
        } else {
            throw new IllegalStateException("There must be a bug in the matrice");
        }
        start.setVisited(true);
        List<SquareWrapper<T>> fronts = start.getNeighs(IState.ROOM);
        while (!fronts.isEmpty()){
            SquareWrapper<T> curr = fronts.remove(0);
            if (curr.isVisited()){
                continue;
            }
            curr.setVisited(true);
            fronts.addAll(curr.getNeighs(IState.ROOM));
        }
        log.trace("Removing all the non visited walls");
        gameMap.getMapData().values().parallelStream().filter(tSquareWrapper -> !tSquareWrapper.isVisited())
                .forEach(tSquareWrapper -> tSquareWrapper.setState(IState.WALL));
        log.trace("Done connecting rooms");
    }


    private void genrateWays() {
        log.trace("Generating maze around rooms");
        List<SquareWrapper<T>> wallList = new ArrayList<>();
        Random r = new Random();

        for (SquareWrapper<T> sw : gameMap.getMapData().values()) {
            if (sw.getDiagonals(IState.WALL).size() != 8){
                continue;
            }
            sw.setState(IState.ROOM);
            wallList.addAll(sw.getNeighs(IState.WALL));

            while (!wallList.isEmpty()){
                SquareWrapper<T> rmd = wallList.remove(r.nextInt(wallList.size()));


                List<SquareWrapper<T>> di = rmd.getDiagonals(IState.ROOM);
                di.removeAll(rmd.getNeighs(IState.ROOM));
                if (di.size() == 4){
                    continue;
                }
                if (rmd.getNeighs(IState.ROOM).size() == 1 &&
                        rmd.getDiagonals(IState.WALL).size() >= 6){
                    rmd.setState(IState.ROOM);
                    wallList.addAll(rmd.getNeighs(IState.WALL));
                }
            }
        }

    }


    private void generateRooms(){
        log.trace("Generating rooms, trying {} times", nmbRoomTry);
        int currentRoomTry = 0;
        Room<T> room;
        while (currentRoomTry < nmbRoomTry){
            currentRoomTry++;
            SquareWrapper<T> tmp;
            tmp = RmdUtils.getRandom(gameMap.getDataMapped().values(), getR());
            while (tmp == null || tmp.getState() == IState.ROOM){
                tmp = RmdUtils.getRandom(gameMap.getDataMapped().values(), getR());
            }

            room = new Room<>(
                    tmp.getCord(),
                    roomSizeMin + (getR().nextInt(roomAddingRmdBound)*2),
                    roomSizeMin + (getR().nextInt(roomAddingRmdBound)*2),
                    gameMap);

            if (room.canBePlaced() && isNotOverlaping(room)) {
                room.populateCords();
                lsRooms.add(room);
            }
        }
        log.trace("Generating rooms done, we generated {} rooms.", lsRooms.size());

    }

    private boolean isNotOverlaping(Room<T> room) {
        for (Room<T> roomList : lsRooms) {
            if (roomList.isOverlapping(room)) {
                return false;
            }
        }
        return true;
    }


}
