package me.nbeaussart.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import me.nbeaussart.engine.model.Color;
import me.nbeaussart.engine.model.Coord;
import me.nbeaussart.engine.model.GameMap;
import me.nbeaussart.engine.model.GameSquare;

/**
 * @author Nicolas Beaussart
 * @since 20/10/16
 */
public class GameSquareImpl implements GameSquare {

    private Color color;
    private final Coord coord;
    private final GameMap gameMap;

    public GameSquareImpl(Color color, Coord coord, GameMap gameMap) {
        this.color = color;
        this.coord = coord;
        this.gameMap = gameMap;
    }


    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public GameMap getGameMap() {
        return gameMap;
    }

    public void setColor(Color color) {
        this.color = color;
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
        GameSquareImpl that = (GameSquareImpl) o;
        return Objects.equal(color, that.color) &&
                Objects.equal(coord, that.coord);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(color, coord);
    }
}
