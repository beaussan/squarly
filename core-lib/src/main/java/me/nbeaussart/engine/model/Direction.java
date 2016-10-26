package me.nbeaussart.engine.model;

/**
 * @author Nicolas Beaussart
 * @since 26/10/16
 */
public enum Direction {
    UP(new Coord(0, +1)),
    DOWN(new Coord(0, -1)),
    RIGHT(new Coord(+1, 0)),
    LEFT(new Coord(-1, 0));


    static {
        UP.setOpposite(DOWN);
        DOWN.setOpposite(UP);
        RIGHT.setOpposite(LEFT);
        LEFT.setOpposite(RIGHT);
    }

    private final Coord cords;
    private Direction opposite;

    Direction(Coord cords) {
        this.cords = cords;
    }


    public Coord getCords() {
        return cords;
    }

    public Direction getOpposite() {
        return opposite;
    }

    private void setOpposite(Direction opposite) {
        this.opposite = opposite;
    }
}
