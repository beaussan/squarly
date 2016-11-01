package me.nbeaussart.engine.model.generator;

import me.nbeaussart.engine.model.Cord;
import me.nbeaussart.engine.model.GameGenerator;
import me.nbeaussart.engine.model.generator.wrapper.GameMapWrapper;
import me.nbeaussart.engine.model.generator.wrapper.Room;
import me.nbeaussart.engine.model.generator.wrapper.SquareWrapper;
import me.nbeaussart.engine.model.interfaces.ICoordinateSquare;
import me.nbeaussart.engine.model.interfaces.IGameMap;
import me.nbeaussart.engine.model.interfaces.IState;
import me.nbeaussart.engine.util.RmdUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Nicolas Beaussart
 * @since 31/10/16
 */
public class DungeonGenerator<T extends ICoordinateSquare> extends AbsGenerator<T> {

    private GameMapWrapper<T> gameMap;
    private final int NMB_ROOM_TRY;
    private final int ROOM_SIZE_MIN;
    private final int ROOM_ADDING_RMD_BOUND;
    private List<Room<T>> lsRooms = new ArrayList<>();

    

    public DungeonGenerator(GameGenerator<T> gameGenerator) {
        super(gameGenerator);
        NMB_ROOM_TRY = 100;
        ROOM_SIZE_MIN = 5;
        ROOM_ADDING_RMD_BOUND = 3;
    }

    public DungeonGenerator(int nmbRoomTry, int roomSizeMin, int roomAddingRmdBound) {
        super();
        NMB_ROOM_TRY = nmbRoomTry;
        ROOM_SIZE_MIN = roomSizeMin;
        ROOM_ADDING_RMD_BOUND = roomAddingRmdBound;
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
        List<SquareWrapper<T>> data = gameMap.getMapData()
                .stream()
                .filter(tSquareWrapper -> tSquareWrapper.getState() == IState.ROOM)
                .collect(Collectors.toList());
        final boolean[] isdone = {false};
        while (!isdone[0]){
            isdone[0] = true;
            data.forEach(tSquareWrapper -> {
                if (tSquareWrapper.getState() == IState.ROOM){
                    if (tSquareWrapper.getNeighs(IState.ROOM).size() <= 1){
                        tSquareWrapper.setState(IState.WALL);
                        isdone[0] = false;
                    }
                }
            });
        }


    }

    private void connectRooms() {

        lsRooms.forEach(tRoom -> {
            List<SquareWrapper<T>> neigh = tRoom.getNeigh();

            RmdUtils.getRandom(neigh, r).setState(IState.ROOM);
            neigh.forEach(tSquareWrapper -> {
                if (r.nextInt(10) > 8){
                    tSquareWrapper.setState(IState.ROOM);
                }
            });
        });
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
        gameMap.getMapData().stream().filter(tSquareWrapper -> !tSquareWrapper.isVisited())
                .forEach(tSquareWrapper -> tSquareWrapper.setState(IState.WALL));
    }


    private void genrateWays() {


        List<SquareWrapper<T>> wallList = new ArrayList<>();
        Random r = new Random();

        for (SquareWrapper<T> sw : gameMap.getMapData()) {
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
        int nmbRoomTry = 0;
        Room<T> room;
        boolean isGood = true;
        while (nmbRoomTry < NMB_ROOM_TRY){
            nmbRoomTry++;
            SquareWrapper<T> tmp;
            tmp = RmdUtils.getRandom(gameMap.getDataMapped().values(), r);
            while (tmp == null || tmp.getState() == IState.ROOM){
                tmp = RmdUtils.getRandom(gameMap.getDataMapped().values(), r);
            }

            room = new Room<T>(
                    tmp.getCord(),
                    ROOM_SIZE_MIN+ (r.nextInt(ROOM_ADDING_RMD_BOUND)*2),
                    ROOM_SIZE_MIN + (r.nextInt(ROOM_ADDING_RMD_BOUND)*2),
                    gameMap);

            if (!room.canBePlaced()){
                continue;
            }

            for (Room<T> roomList : lsRooms) {
                if (roomList.isOverlapping(room)) {
                    isGood = false;
                    break;
                }
            }
            if (!isGood){
                continue;
            }
            room.populateCords();
            lsRooms.add(room);

        }
        //     public Room(Cord startingCords, int width, int height, GameMapWrapper<T> gameMapWrapper) {

    }






}
