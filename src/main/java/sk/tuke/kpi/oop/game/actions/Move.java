package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.actions.Action;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.characters.Alive;

import java.util.Objects;

public class Move<A extends Movable> implements Action<A>{

    private A actor;
    private boolean isDone;
    private Direction direction;
    private float timeleft;
    private boolean firstExec;

    public Move(Direction direction, float duration){
        actor = null;
        isDone = false;
        this.direction = direction;
        timeleft = duration;
        firstExec = true;
    }

    public Move(Direction direction){
        actor = null;
        isDone = false;
        this.direction = direction;
        timeleft = 0;
        firstExec = true;
    }

    @Override
    public @Nullable A getActor() {
        return actor;
    }

    @Override
    public void setActor(@Nullable A actor) {
        this.actor = actor;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public void execute(float deltaTime){
        if(getActor() == null || isDone){
            isDone = true;
            return;
        }

        if(firstExec)
            actor.startedMoving(direction);

        firstExec = false;
        timeleft -= deltaTime;

        if(isDone || timeleft <= 0){
            isDone = true;
            actor.stoppedMoving();
            return;
        }

        int previousY = actor.getPosY();
        int previousX = actor.getPosX();

        updatePos(actor);
        stopIfCollided(actor, previousX, previousY);

        if(actor instanceof Alive && ((Alive) actor).getHealth().getValue() == 0)
            isDone = true;
    }

    private void stopIfCollided(Movable actor, int previousX, int previousY){
        if(Objects.requireNonNull(actor.getScene()).getMap().intersectsWithWall(actor)){
            actor.setPosition(previousX, previousY);
            actor.collidedWithWall();
            isDone = true;
        }
    }

    private void updatePos(Movable actor){
        if(direction.getDy() == 1)
            actor.setPosition(actor.getPosX(), actor.getPosY() + actor.getSpeed());
        if(direction.getDy() == -1)
            actor.setPosition(actor.getPosX(), actor.getPosY() - actor.getSpeed());
        if(direction.getDx() == 1)
            actor.setPosition(actor.getPosX() + actor.getSpeed(), actor.getPosY());
        if(direction.getDx() == -1)
            actor.setPosition(actor.getPosX() - actor.getSpeed(), actor.getPosY());
    }

    @Override
    public void reset(){
        timeleft = 5.0f;
        firstExec = true;
        isDone = false;
    }

    public void stop(){
        if (actor == null)
            return;

        isDone = true;
        actor.stoppedMoving();
    }
}
