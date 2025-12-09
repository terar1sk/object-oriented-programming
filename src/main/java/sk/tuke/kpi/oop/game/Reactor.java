package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;

import java.util.HashSet;
import java.util.Set;

public class Reactor extends AbstractActor implements Switchable, Repairable{
    private int temperature;
    private int damage;
    private Animation normalAnimation;
    private Animation hotAnimation;
    private Animation brokenAnimation;
    private Animation offAnimation;
    private Animation extAnimation;
    private Boolean isOn;
    private Set<EnergyConsumer> devices;
    public Reactor() {
        this.temperature = 0;
        this.damage = 0;
        this.normalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f,
            Animation.PlayMode.LOOP_PINGPONG);
        this.hotAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.05f,
            Animation.PlayMode.LOOP_PINGPONG);
        this.brokenAnimation = new Animation("sprites/reactor_broken.png", 80, 80, 0.05f,
            Animation.PlayMode.LOOP_PINGPONG);
        this.offAnimation = new Animation("sprites/reactor.png", 80, 80, 0.1f,
            Animation.PlayMode.LOOP_PINGPONG);
        this.extAnimation = new Animation("sprites/reactor_extinguished.png", 80, 80, 0.1f,
            Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(offAnimation);
        isOn = false;
        devices = new HashSet<>();
    }

    public int getTemperature() {
        return this.temperature;
    }

    public int getDamage() {
        return this.damage;
    }

    public void increaseTemperature(int val) {
        if (damage >= 100 || !isOn || val < 0)
            return;

        int param = val;

        if (damage >= 33 && damage <= 66) param *= 1.5;
        if (damage > 66) param *= 2;

        this.temperature += param;

        if (temperature >= 2000) {
            damage = (int) Math.round(((temperature - 2000.0)/4000.0)*100);
        }
        if (temperature >= 6000) {
            turnOff();
            damage = 100;
        }

        updateAnimation();
    }

    public void decreaseTemperature(int val) {
        if (this.damage >= 100 || !isOn || val < 0)
            return;

        int param = val;
        if (this.damage >= 50){
            param /= 2;
        }

        this.temperature -= param;
        if (temperature < 0)
            temperature = 0;

        updateAnimation();
    }

    private void updateAnimation() {
        updateDevicesPower();

        if (damage >= 100) {
            setAnimation(brokenAnimation);
            return;
        }

        if (!isOn) {
            setAnimation(offAnimation);
            return;
        }

        if (temperature >= 4000 && temperature < 6000) {
            setAnimation(hotAnimation);
            return;
        }

        setAnimation(normalAnimation);
    }

    public boolean repair() {
        if (damage <= 0 || damage >= 100) {
            return false;
        }


        damage -= 50;
        if (damage < 0)
            damage = 0;

        temperature = (damage * 40) + 2000;
        updateAnimation();
        return true;
    }

    public boolean extinguish() {
        if (damage == 0 || !isOn) {
            return false;
        }

        temperature -= 4000;
        setAnimation(extAnimation);
        return true;
    }

    public void turnOn() {
        if (damage >= 100)
            return;

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

    public void addDevice(EnergyConsumer device) {
        if (device != null) {
            devices.add(device);
            device.setPowered(isOn);
        }
    }

    public void removeDevice(EnergyConsumer device) {
        if (device != null) {
            device.setPowered(false);
            devices.remove(device);
        }
    }

    private void updateDevicesPower() {
        devices.forEach( (EnergyConsumer device) -> device.setPowered(isOn) );
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        updateDevicesPower();
        new PerpetualReactorHeating(1).scheduleFor(this);
    }
}
