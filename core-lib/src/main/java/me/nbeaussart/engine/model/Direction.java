package me.nbeaussart.engine.model;

/**
 * @author Nicolas Beaussart
 * @since 26/10/16
 */
public enum Direction {
    UP(new Cord(0, +1)),
    DOWN(new Cord(0, -1)),
    RIGHT(new Cord(+1, 0)),
    LEFT(new Cord(-1, 0));


    static {
        UP.setOpposite(DOWN);
        DOWN.setOpposite(UP);
        RIGHT.setOpposite(LEFT);
        LEFT.setOpposite(RIGHT);
    }

    private final Cord cords;
    private Direction opposite;

    Direction(Cord cords) {
        this.cords = cords;
    }


    public Cord getCords() {
        return cords;
    }

    public Direction getOpposite() {
        return opposite;
    }

    private void setOpposite(Direction opposite) {
        this.opposite = opposite;
    }
}
