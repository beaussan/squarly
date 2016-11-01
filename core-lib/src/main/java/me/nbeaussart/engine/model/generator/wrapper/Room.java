package me.nbeaussart.engine.model.generator.wrapper;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import me.nbeaussart.engine.model.Cord;
import me.nbeaussart.engine.model.interfaces.ICoordinateSquare;
import me.nbeaussart.engine.model.interfaces.IState;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Nicolas Beaussart
 * @since 31/10/16
 */
public class Room<T extends ICoordinateSquare> {
    private final Cord startingCords;
    private final int width;
    private final int height;
    private final Cord otherCorner;
    private final GameMapWrapper<T> gameMapWrapper;
    private Rectangle2D.Double rect;
    private boolean isConnected = false;

    public Room(Cord startingCords, int width, int height, GameMapWrapper<T> gameMapWrapper) {
        this.startingCords = checkNotNull(startingCords);
        checkArgument(width > 0);
        checkArgument(height > 0);
        this.width = width;
        this.height = height;
        this.gameMapWrapper = checkNotNull(gameMapWrapper);
        otherCorner = startingCords.add(width, height);
        rect = new Rectangle2D.Double(startingCords.getX(), startingCords.getY(), width, height);
    }

    public List<SquareWrapper<T>> getNeigh(){

        List<SquareWrapper<T>> list = new ArrayList<>();
        for (int x = -1; x <= width; x++) {
            for (int y = -1; y <= height; y++) {
                gameMapWrapper.getFromCords(startingCords.add(x,y)).ifPresent(list::add);
            }
        }
        list = list.stream()
                .filter(tSquareWrapper -> {
                    return tSquareWrapper.getState() == IState.WALL;
                })
                .filter(tSquareWrapper -> tSquareWrapper.getNeighs(IState.ROOM).size() == 2)
                .collect(Collectors.toList());
       // System.out.println(list);
        return list;
    }

    public void setVisited(){
        for (int x = -1; x <= width; x++) {
            for (int y = -1; y <= height; y++) {
                gameMapWrapper.getFromCords(startingCords.add(x,y)).ifPresent(tSquareWrapper -> tSquareWrapper.setVisited(true));
            }
        }
    }

    public void populateCords(){
        if (!canBePlaced()){
            return;
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                gameMapWrapper.getFromCords(startingCords.add(x,y)).ifPresent(tSquareWrapper -> {tSquareWrapper.setState(IState.ROOM);});
            }
        }
    }

    public boolean canBePlaced(){

        final boolean[] isGood = {true};

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                gameMapWrapper.getFromCords(startingCords.add(x,y)).ifPresent(tSquareWrapper -> {
                    if (tSquareWrapper.getState() != IState.WALL) {
                        isGood[0] = false;
                    }
                    if (tSquareWrapper.getDiagonals(IState.WALL).size() != 8){
                        isGood[0] = false;
                    }
                });
                if (!isGood[0]){
                    return false;
                }
            }
        }
        return true;

    }
    public boolean canBePlacedOld(){
        Optional<SquareWrapper<T>> fc = gameMapWrapper.getFromCords(startingCords);

        if (!fc.isPresent()) {
            return false;
        } else {
            if (fc.get().getDiagonals(IState.WALL).size() != 8){
                return false;
            }
        }
        Optional<SquareWrapper<T>> fco = gameMapWrapper.getFromCords(otherCorner);
        if (!fco.isPresent()) {
            return false;
        } else {
            if (fco.get().getDiagonals(IState.WALL).size() != 8){
                return false;
            }
        }
        return true;

    }

    public boolean isOverlapping(Room room){
        return rect.intersects(room.rect);
    }


    public Cord getStartingCords() {
        return startingCords;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Cord getOtherCorner() {
        return otherCorner;
    }

    public GameMapWrapper<T> getGameMapWrapper() {
        return gameMapWrapper;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("startingCords", startingCords)
                .add("width", width)
                .add("height", height)
                .add("otherCorner", otherCorner)
                .add("rect", rect)
                .add("gameMapWrapper", gameMapWrapper)
                .toString();
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}
