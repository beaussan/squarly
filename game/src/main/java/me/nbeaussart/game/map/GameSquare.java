package me.nbeaussart.game.map;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import me.nbeaussart.engine.model.Color;
import me.nbeaussart.engine.model.Cord;
import me.nbeaussart.engine.model.interfaces.ICaracterSquare;
import me.nbeaussart.engine.model.interfaces.ICoordinateSquare;
import me.nbeaussart.engine.model.interfaces.IState;
import me.nbeaussart.game.entity.Entity;

/**
 * Created by beaussan on 31/10/16.
 */
public class GameSquare implements ICaracterSquare, ICoordinateSquare{

    private final static SquareContent COLOR_WALL = new SquareContent('#', new Color(0,0,0),      new Color(150,150,150));
    private final static SquareContent COLOR_ROOM = new SquareContent(' ', new Color(255,255,255),new Color(255,255,255));
    private final static SquareContent COLOR_DOOR = new SquareContent('+', new Color(255,255,255),new Color(255,255,255));

    private final Cord cord;
    private final GameMap gamemap;
    private IState state = IState.WALL;
    private Entity entity;
    private SquareContent squareContent;


    public GameSquare(Cord cord, GameMap gm){
        this.cord=cord;
        this.gamemap=gm;
        gamemap.add(this);
    }

    @Override
    public char getChar() {
        return getSquareContent().getCharData();
    }

    @Override
    public Color getBackground() {
        return getSquareContent().getBackground();
    }

    @Override
    public Color getForeground() {
        return getSquareContent().getForeground();
    }

    public SquareContent getSquareContent() {
        if (entity != null){
            return entity.getSquareContent();
        }
        switch (state){
            case ROOM:
                return COLOR_ROOM;
            case DOOR:
                return COLOR_DOOR;
            default:
                return COLOR_WALL;
        }

    }


    public Cord getCord() {
        return this.cord;
    }

    public GameMap getGameMap() {
        return this.gamemap;
    }

    public void setUpdated() {
        gamemap.setChanged(this);
    }

    public IState getState() {
        return this.state;
    }

    public void setState(IState state) {
        this.state=state;
        setUpdated();
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
        setUpdated();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameSquare that = (GameSquare) o;
        return Objects.equal(cord, that.cord) &&
                Objects.equal(gamemap, that.gamemap) &&
                state == that.state;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cord, gamemap, state);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("cord", cord)
                .add("gamemap", gamemap)
                .add("state", state)
                .toString();
    }
}
