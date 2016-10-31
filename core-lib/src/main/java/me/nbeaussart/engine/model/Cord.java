package me.nbeaussart.engine.model;

import com.google.common.base.MoreObjects;

import java.util.Objects;

/**
 * Very simple coordinate system for a 2d environment
 *
 * @author Nicolas Beaussart
 * @since 12/10/16
 */
public class Cord {
    private final int x;
    private final int y;

    public Cord(int x, int y) {
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
     * Add x and y to this {@link Cord} to get a new one
     * @param x The x to add
     * @param y The y to add
     * @return The new {@link Cord} generated
     */
    public Cord add(int x, int y) {
        return new Cord(this.x + x, this.y + y);
    }

    /**
     *
     * Add a {@link Direction} to this one
     * @param direction The {@link Direction} to add to this one
     * @return The new {@link Cord} generated
     */
    public Cord add(Direction direction) {
        return add(direction.getCords());
    }

    /**
     *
     * Add a {@link Cord} to this one
     * @param cord The {@link Cord} to add to this one
     * @return The new {@link Cord} generated
     */
    public Cord add(Cord cord) {
        return add(cord.getX(), cord.getY());
    }

    /**
     * Minus x and y to this {@link Cord} to get a new one
     * @param x The x to minus
     * @param y The y to minus
     * @return The new {@link Cord} generated
     */
    public Cord minus(int x, int y) {
        return new Cord(this.x - x, this.y - y);
    }

    /**
     * Minus a {@link Cord} to this one
     * @param cord The {@link Cord} to minus to this one
     * @return The new {@link Cord} generated
     */
    public Cord minus(Cord cord) {
        return minus(cord.getX(), cord.getY());
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
        Cord cord = (Cord) o;
        return x == cord.x &&
                y == cord.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
