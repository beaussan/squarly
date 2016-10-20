package me.nbeaussart.engine.model;

import com.google.common.base.MoreObjects;

import java.util.Objects;

/**
 * Very simple coordinate system for a 2d environment
 *
 * @author Nicolas Beaussart
 * @since 12/10/16
 */
public class Coord {
    private final int x;
    private final int y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Add x and y to this {@link Coord} to get a new one
     * @param x The x to add
     * @param y The y to add
     * @return The new {@link Coord} generated
     */
    public Coord add(int x, int y){
        return new Coord(this.x + x, this.y + y);
    }

    /**
     *
     * Add a {@link Coord} to this one
     * @param coord The {@link Coord} to add to this one
     * @return The new {@link Coord} generated
     */
    public Coord add(Coord coord){
        return add(coord.getX(), coord.getY());
    }

    /**
     * Minus x and y to this {@link Coord} to get a new one
     * @param x The x to minus
     * @param y The y to minus
     * @return The new {@link Coord} generated
     */
    public Coord minus(int x, int y){
        return new Coord(this.x - x, this.y - y);
    }

    /**
     * Minus a {@link Coord} to this one
     * @param coord The {@link Coord} to minus to this one
     * @return The new {@link Coord} generated
     */
    public Coord minus(Coord coord){
        return minus(coord.getX(), coord.getY());
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("x", x)
                .add("y", y)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coord coord = (Coord) o;
        return x == coord.x &&
                y == coord.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
