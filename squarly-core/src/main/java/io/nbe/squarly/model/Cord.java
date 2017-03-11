package io.nbe.squarly.model;

import com.google.common.base.MoreObjects;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.nbe.squarly.util.Pair;

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

    private static final LoadingCache<Pair<Integer, Integer>, Cord> cacheGraph = CacheBuilder.newBuilder()
            .build(new CacheLoader<Pair<Integer, Integer>, Cord>() {
                @Override
                public Cord load(Pair<Integer, Integer> key) throws Exception {
                    return new Cord(key.getKey(), key.getValue());
                }
            });

    /**
     * Create a Cord from x y
     * @param x the x Cord
     * @param y the y Cord
     */
    private Cord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get a coordinate, if not cached, creating it
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the coordinate got
     */
    public static Cord get(int x, int y){
        return cacheGraph.getUnchecked(new Pair<>(x,y));
    }

    /**
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y coordinate
     */
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
     * Add a {@link Direction} to this one
     * @param direction The {@link Direction} to add to this one
     * @return The new {@link Cord} generated
     */
    public Cord add(Direction direction) {
        return add(direction.getCords());
    }

    /**
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
        return this == o;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
