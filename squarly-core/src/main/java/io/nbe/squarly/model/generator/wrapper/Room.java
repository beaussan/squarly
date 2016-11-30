package io.nbe.squarly.model.generator.wrapper;

import io.nbe.squarly.model.Cord;
import io.nbe.squarly.model.interfaces.ICoordinateSquare;
import io.nbe.squarly.model.interfaces.IState;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
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
    private final GameMapWrapper<T> gameMapWrapper;
    private Rectangle2D.Double rect;

    public Room(Cord startingCords, int width, int height, GameMapWrapper<T> gameMapWrapper) {
        this.startingCords = checkNotNull(startingCords);
        checkArgument(width > 0);
        checkArgument(height > 0);
        this.width = width;
        this.height = height;
        this.gameMapWrapper = checkNotNull(gameMapWrapper);
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
                .filter(tSquareWrapper -> tSquareWrapper.getState() == IState.WALL)
                .filter(tSquareWrapper -> tSquareWrapper.getNeighs(IState.ROOM).size() == 2)
                .collect(Collectors.toList());
        return list;
    }


    public void populateCords(){
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                gameMapWrapper.getFromCords(startingCords.add(x,y))
                        .ifPresent(tSquareWrapper -> tSquareWrapper.setState(IState.ROOM));
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

    public boolean isOverlapping(Room room){
        return rect.intersects(room.rect);
    }


    public Cord getStartingCords() {
        return startingCords;
    }

}
