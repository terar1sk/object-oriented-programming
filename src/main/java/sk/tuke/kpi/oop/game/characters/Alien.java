package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

import java.util.List;
import java.util.Objects;

public class Alien extends AbstractActor implements Movable, Enemy, Alive {

    private Health health;
    private int speed = 1;

    private Behaviour<? super Alien> behaviour;
    public Alien() {
        setAnimation(new Animation("sprites/alien.png", 32,32, 0.1f,
            Animation.PlayMode.LOOP_PINGPONG));

        health = new Health(100);
        behaviour = null;
    }

    public Alien(int healthVal, Behaviour<? super Alien> behaviour) {
        setAnimation(new Animation("sprites/alien.png", 32,32, 0.1f,
            Animation.PlayMode.LOOP_PINGPONG));

        health = new Health(healthVal);
        this.behaviour = behaviour;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public Health getHealth() {
        return health;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        health.onExhaustion(() -> Objects.requireNonNull(getScene()).removeActor(this));

        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(this::attackNonEnemy),
                new Wait<>(0.5f)
            )).scheduleFor(this);

        if (behaviour != null)
            behaviour.setUp(this);
    }

    private void attackNonEnemy() {
        Scene scene = getScene();
        if (scene == null)
            return;

        List<Actor> actorsList;
        actorsList = scene.getActors();

        for (Actor item : actorsList) {
            if (item instanceof Alive && !(item instanceof Enemy) && this.intersects(item)) {
                int damage = 3;
                ((Alive) item).getHealth().drain(damage);
            }
        }

    }
}
