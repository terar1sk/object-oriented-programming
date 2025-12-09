package sk.tuke.kpi.oop.game.weapons;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Alive;

import java.util.Objects;

public class Bullet extends AbstractActor implements Fireable {

    private final int speed = 4;

    public Bullet() {
        setAnimation(new Animation("sprites/bullet.png"));
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void startedMoving(Direction direction) {
        if (direction != null && direction != Direction.NONE)
            this.getAnimation().setRotation(direction.getAngle());
    }

    @Override
    public void stoppedMoving() {
        Fireable.super.stoppedMoving();
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(
            new Invoke<>(() -> {
                for (Actor actor : Objects.requireNonNull(getScene()).getActors()) {
                    if (this.intersects(actor) && (actor instanceof Alive)) {
                        ((Alive) actor).getHealth().drain(50);
                        collidedWithWall();
                    }
                }
            })
        ).scheduleFor(this);

    }

    @Override
    public void collidedWithWall() {
        Objects.requireNonNull(getScene()).removeActor(this);
    }

}
