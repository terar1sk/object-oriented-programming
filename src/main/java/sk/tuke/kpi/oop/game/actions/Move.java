package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.actions.Action;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Move<A extends Movable> implements Action<A> {
    private A actor;
    private float duration;
    private boolean isDone;
    private int firstTime;
    private Direction direction;

    public Move(Direction direction, float duration){
        this.direction = direction;
        this.duration = duration;
        isDone = false;
        firstTime = 0;
    }

    private Move(Direction direction){
        this.direction = direction;
        duration = 0;
        isDone = false;
        firstTime = 0;
    }

    @Nullable
    @Override
    public A getActor(){
        return actor;
    }

    @Override
    public void setActor(@Nullable A actor){
        this.actor = actor;
    }

    @Override
    public boolean isDone(){
        return isDone;
    }

    @Override
    public void execute(float deltaTime){
        if(actor != null){
            duration = duration - deltaTime;
            firstTime = firstTime + 1;
            if(firstTime == 1 && !isDone){
                actor.startedMoving(direction);
            }
            actor.setPosition(actor.getPosX() + direction.getDx() * actor.getSpeed(), actor.getPosY() + direction.getDy() * actor.getSpeed());
            assert getActor() != null;
            if(Objects.requireNonNull(getActor().getScene()).getMap().intersectsWithWall(actor)){
                actor.setPosition(actor.getPosX() - direction.getDx() * actor.getSpeed(), actor.getPosY() - direction.getDy() * actor.getSpeed());
            }
            if(Math.abs(deltaTime - this.duration) <= 1e-5){
                this.stop();
                firstTime = 0;
            }
        }
    }

    @Override
    public void reset(){
        actor.stoppedMoving();
        isDone = false;
        firstTime = 0;
        duration = 0;
    }

    public void stop(){
        isDone = true;
        actor.stoppedMoving();
    }
}
