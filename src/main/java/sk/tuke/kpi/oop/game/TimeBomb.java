package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class TimeBomb extends AbstractActor {

    private float timeToDetonate;
    private boolean activated;
    private Animation activatedAnimation;
    private Animation explodingAnimation;

    public TimeBomb(float time) {
        setAnimation(new Animation("sprites/bomb.png"));
        activatedAnimation = new Animation("sprites/bomb_activated.png", 16, 16, 0.2f,
            Animation.PlayMode.LOOP_PINGPONG);
        explodingAnimation = new Animation("sprites/small_explosion.png", 64, 32, 0.5f,
            Animation.PlayMode.ONCE);

        timeToDetonate = time;
        activated = false;
    }

    public float getTimeToDetonate() {
        return timeToDetonate;
    }

    public boolean isActivated() {
        return activated;
    }

    public void activate() {
        if (activated)
            return;

        activated = true;

        setAnimation(activatedAnimation);
        new ActionSequence<>(
            new Wait<>(timeToDetonate),
            new Invoke<>( this::explode ),
            new Invoke<>( () -> getScene().removeActor(this))
        ).scheduleFor(this);
    }

    protected void explode() {
        setAnimation(explodingAnimation);
    }
}
