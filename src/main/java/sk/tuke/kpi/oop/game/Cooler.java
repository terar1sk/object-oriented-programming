package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Cooler extends AbstractActor implements Switchable{
    private Animation normalAnimation;
    private Reactor reactor;

    private boolean isOn;

    public Cooler(Reactor newReactor) {
        isOn = false;
        this.reactor = newReactor;
        this.normalAnimation = new Animation("sprites/fan.png", 32, 32, 0.1f,
            Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(normalAnimation);
        normalAnimation.pause();
    }

    public void toggle() {
        isOn = !isOn;
        updateAnimation();
    }

    public void turnOn() {
        isOn = true;
        updateAnimation();
    }

    public void turnOff() {
        isOn = false;
        updateAnimation();
    }

    protected Reactor getReactor() {
        return reactor;
    }

    public boolean isOn() {
        return this.isOn;
    }

    public void updateAnimation() {
        if (isOn)
                normalAnimation.play();
        else
                normalAnimation.pause();
    }

    private void coolReactor() {
        if (isOn && reactor != null)
            reactor.decreaseTemperature(1);
    }
    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::coolReactor)).scheduleFor(this);
    }
}
