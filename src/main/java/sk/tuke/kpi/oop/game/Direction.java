package sk.tuke.kpi.oop.game;

public enum Direction{
    NORTH(0, 1),
    EAST(1, 0),
    SOUTH(0, -1),
    WEST(-1, 0),
    NONE(0, 0),
    NORTHEAST(1, 1),
    SOUTHEAST(1, -1),
    SOUTHWEST(-1, -1),
    NORTHWEST(-1, 1);

    private final int dx;
    private final int dy;

    public int getDx(){
        return dx;
    }

    public int getDy(){
        return dy;
    }

    Direction(int dx, int dy){
        this.dx = dx;
        this.dy = dy;
    }

    public float getAngle(){
        if(getDx() == 0 && getDy() == 1){
            return 0;
        }
        if(getDx() == 1 && getDy() == 0){
            return 270;
        }
        if(getDx() == 0 && getDy() == -1){
            return 180;
        }
        if(getDx() == -1 && getDy() == 0){
            return 90;
        }
        if(getDx() == 1 && getDy() == 1){
            return 315;
        }
        if(getDx() == 1 && getDy() == -1){
            return 225;
        }
        if(getDx() == -1 && getDy() == -1){
            return 135;
        }
        return 45;
    }

    public Direction combine(Direction other){
        int newDx = this.dx + other.dx;
        int newDy = this.dy + other.dy;

        for(Direction direction : Direction.values()){
            if(direction.getDx() == newDx && direction.getDy() == newDy){
                return direction;
            }
        }
        return NONE;
    }
}
