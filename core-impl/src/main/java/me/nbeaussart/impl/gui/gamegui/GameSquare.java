package me.nbeaussart.impl.gui.gamegui;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import me.nbeaussart.engine.model.Color;
import me.nbeaussart.engine.model.Coord;
import me.nbeaussart.engine.model.interfaces.IColoredSquare;
import me.nbeaussart.engine.model.interfaces.ICoordinateSquare;
import me.nbeaussart.engine.model.interfaces.IGameMap;

/**
 * @author Nicolas Beaussart
 * @since 20/10/16
 */
public class GameSquare implements IColoredSquare, ICoordinateSquare {

    private final Coord coord;
    private final IGameMap<? extends GameSquare> gameMap;
    private Color color;

    public GameSquare(Color color, Coord coord, IGameMap<? extends GameSquare> gameMap) {
        this.color = color;
        this.coord = coord;
        this.gameMap = gameMap;
    }


    @Override
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public IGameMap<? extends GameSquare> getGameMap() {
        return gameMap;
    }

    @Override
    public Coord getCoord() {
        return coord;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("color", color)
                .add("coord", coord)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameSquare that = (GameSquare) o;
        return Objects.equal(color, that.color) &&
                Objects.equal(coord, that.coord);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(color, coord);
    }
}
