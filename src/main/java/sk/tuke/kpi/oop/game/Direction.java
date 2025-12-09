package sk.tuke.kpi.oop.game;

import java.util.Random;

public enum Direction {
    NORTH(0,1),
    EAST(1,0),
    SOUTH(0, -1),
    WEST(-1, 0),
    NORTHEAST(1, 1),
    NORTHWEST(-1,1),
    SOUTHEAST(1,-1),
    SOUTHWEST(-1,-1),
    NONE(0,0);

    private final int dx;
    private final int dy;

    private float angle;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public float getAngle() {
        return angle;
    }

    public Direction combine(Direction other) {
        if (other == null)
            return null;

        int y = 0;
        int x = 0;

        y += getDy() + other.getDy();
        x += getDx() + other.getDx();

        if (y > 1) y = 1;
        if (y < -1) y = -1;
        if (x > 1) x = 1;
        if (x < -1) x = -1;

        Direction newDirection = NONE;
        for (Direction value : Direction.values()) {
            if (value.getDx() == x && value.getDy() == y)
                newDirection=value;
        }

        return newDirection;
    }

    public static Direction fromAngle(float angle) {
        switch ((int) angle) {
            case 0: return NORTH;
            case 45: return NORTHWEST;
            case 180: return SOUTH;
            case 90: return WEST;
            case 135: return SOUTHWEST;
            case 225: return SOUTHEAST;
            case 270: return EAST;
            case 315 :return NORTHEAST;
            default: return NONE;
        }
    }


    public static Direction randomDirection() {
        return Direction.values()[new Random().nextInt(Direction.values().length) - 1];
    }


    static {
        NORTH.angle = 0;
        EAST.angle = 270;
        SOUTH.angle = 180;
        WEST.angle = 90;
        NORTHEAST.angle =315;
        SOUTHEAST.angle =225;
        SOUTHWEST.angle =135;
        NORTHWEST.angle =45;
    }


}
