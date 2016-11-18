package io.nbe.squarly.model;

import com.google.common.base.MoreObjects;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Very simple coordinate system for a 2d environment
 *
 * @author Nicolas Beaussart
 * @since 12/10/16
 */
public class Cord {
    private static class Pair{
        private Pair(int x, int y){
            this.x = x;
            this.y = y;
        }
        int x;
        int y;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return x == pair.x &&
                    y == pair.y;
        }

        @Override
        public int hashCode() {
            return com.google.common.base.Objects.hashCode(x, y);
        }
    }

    private final int x;
    private final int y;

    private static final Map<Pair, Cord> cache = new ConcurrentHashMap<>();

    /**
     * Get a coordinate, if not cached, creating it
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the coordinate got
     */
    public static Cord get(int x, int y){
        Pair hash = new Pair(x,y);

        if (!cache.containsKey(hash)) {
            Cord c = new Cord(x,y);
            cache.put(hash, c);
            return c;
        }
        return cache.get(hash);
    }

    private Cord(int x, int y) {
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
        return Cord.get(this.x + x, this.y + y);
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
        return Cord.get(this.x - x, this.y - y);
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
