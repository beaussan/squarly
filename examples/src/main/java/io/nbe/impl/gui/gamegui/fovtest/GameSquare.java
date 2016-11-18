package io.nbe.impl.gui.gamegui.fovtest;

import io.nbe.squarly.model.Color;
import io.nbe.squarly.model.interfaces.IColoredSquare;
import io.nbe.squarly.model.interfaces.ICoordinateSquare;
import io.nbe.squarly.model.interfaces.IState;
import io.nbe.squarly.model.Cord;

/**
 * @author Nicolas Beaussart
 * @since 13/11/16
 */
public class GameSquare implements ICoordinateSquare, IColoredSquare {

    private final static Color COLOR_WALL=new Color(0,0,0);
    private final static Color COLOR_ROOM=new Color(255,255,255);
    private final static Color COLOR_DOOR=new Color(255,0,0);

    private final Cord cord;
    private final GameMap gameMap;
    private IState state = IState.WALL;
    private boolean isViewed = false;
    private Color color;

    public GameSquare(Cord cord, GameMap gameMap) {
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
        setUpdated();
    }

    private Color getColorFromState(){
        if (color != null){
            return color;
        }
        switch (state){
            case WALL:
                return COLOR_WALL;
            case ROOM:
                return COLOR_ROOM;
            default:
                return COLOR_DOOR;
        }
    }

    @Override
    public Color getColor() {
        if (isViewed){
            return getColorFromState();
        } else {
            return getColorFromState().darker();
        }
    }

    public boolean isViewed() {
        return isViewed;
    }

    public void setViewed(boolean viewed) {
        setUpdated();
        isViewed = viewed;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
