package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor implements Switchable, EnergyConsumer{
    private Animation onAnimation;
    private Animation offAnimation;
    private boolean isOn;
    private boolean isPowered;

    public Light() {
        this.onAnimation = new Animation("sprites/light_on.png");
        this.offAnimation = new Animation("sprites/light_off.png");
        setAnimation(offAnimation);
        isOn = false;
        isPowered = false;
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

    public boolean isOn() {
        return isOn;
    }

    public void setPowered(boolean powered) {
        isPowered = powered;
        updateAnimation();
    }

    private void updateAnimation() {
        if (isOn && isPowered)
            setAnimation(onAnimation);
        else
            setAnimation(offAnimation);
    }
}
