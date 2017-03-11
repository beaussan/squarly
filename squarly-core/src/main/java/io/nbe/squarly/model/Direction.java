package io.nbe.squarly.model;

/**
 * Class which represent a direction and some methods to deal with.
 *
 * @author Nicolas Beaussart
 * @since 26/10/16
 */
public enum Direction {
    UP(Cord.get(0, +1)),
    DOWN(Cord.get(0, -1)),
    RIGHT(Cord.get(+1, 0)),
    LEFT(Cord.get(-1, 0));


    static {
        UP.setOpposite(DOWN);
        DOWN.setOpposite(UP);
        RIGHT.setOpposite(LEFT);
        LEFT.setOpposite(RIGHT);
    }

    private final Cord cords;
    private Direction opposite;

    /**
     * Construct a specific direction from a coordinate
     * @param cords the source coordinate
     */
    Direction(Cord cords) {
        this.cords = cords;
    }

    /**
     * Adding a direction to this one, get the coordinate done
     * @param direction the direction to add
     * @return the coordinate got
     */
    public Cord add(Direction direction){
        return cords.add(direction);
    }

    /**
     * @return the coordinate of the direction
     */
    public Cord getCords() {
        return cords;
    }

    /**
     * @return the opposite direction of the current direction
     */
    public Direction getOpposite() {
        return opposite;
    }

    /**
     * Define the opposite of the direction by setting the opposite one
     * @param opposite the opposite direction
     */
    private void setOpposite(Direction opposite) {
        this.opposite = opposite;
    }
}
