package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Computer extends AbstractActor implements EnergyConsumer{
    private Animation normalAnimation;

    private boolean isPowered;

    public Computer() {
        this.normalAnimation = new Animation("sprites/computer.png", 80, 48, 0.2f,
            Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(normalAnimation);
        isPowered = false;
        normalAnimation.pause();
    }

    public void setPowered(boolean powered) {
        isPowered = powered;
        updateAnimation();
    }
    public int add(int a, int b) {
        if (isPowered) return a + b;
        else return 0;
    }

    public int sub(int a, int b) {
        if (isPowered) return a - b;
        else return 0;
    }

    public float add(float a, float b) {
        if (isPowered) return a + b;
        else return 0;
    }

    public float sub(float a, float b) {
        if (isPowered) return a - b;
        else return 0;
    }

    private void updateAnimation() {
        if(isPowered) normalAnimation.play();
        else normalAnimation.pause();
    }
}
