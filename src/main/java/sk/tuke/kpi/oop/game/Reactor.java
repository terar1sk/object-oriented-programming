package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.tools.FireExtinguisher;
import sk.tuke.kpi.oop.game.tools.Hammer;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;

import java.util.HashSet;
import java.util.Set;

public class Reactor extends AbstractActor implements Switchable, Repairable{
    private int temperature;
    private int damage;
    private boolean isOn;
    private Animation normalAnimation;
    private Animation hotAnimation;
    private Animation brokenAnimation;
    private Animation offAnimation;

    private Set<EnergyConsumer> devices;

    public Reactor(){
        this.temperature = 0;
        this.damage = 0;
        this.isOn = false;

        this.normalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        this.hotAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.05f, Animation.PlayMode.LOOP_PINGPONG);
        this.brokenAnimation = new Animation("sprites/reactor_broken.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        this.offAnimation = new Animation("sprites/reactor.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(this.offAnimation);

        this.devices = new HashSet<>();
    }

    public int getTemperature(){
        return this.temperature;
    }

    public int getDamage(){
        return this.damage;
    }

    public boolean isRunning(){
        return this.isOn;
    }

    public void turnOn(){
        if(this.getDamage() < 100){
            isOn = true;
            for(EnergyConsumer device : devices){
                device.setPowered(true);
            }
            updateAnimation();
        }
    }

    public void turnOff(){
        if(temperature >= 6000){
            setAnimation(brokenAnimation);
        }
        else{
            setAnimation(offAnimation);
        }
        isOn = false;
        for(EnergyConsumer device : devices){
            device.setPowered(false);
        }
    }

    public void addDevice(EnergyConsumer device){
        if(device != null){
            devices.add(device);
            device.setPowered(isOn && damage < 100);
        }
    }

    public void removeDevice(EnergyConsumer device){
        if(device != null){
            device.setPowered(false);
            devices.remove(device);
        }
    }

    public void increaseTemperature(int increment){
        if(!isOn || increment < 0){
            return;
        }
        if(damage > 66){
            temperature += (int) (2 * increment);
        }
        else if(damage > 33){
            temperature += (int) (1.5 * increment);
        }
        else{
            temperature += increment;
        }
        if(temperature > 2000){
            int curTemperature = temperature - 2000;
            damage = Math.min(100, Math.round((100 * curTemperature) / 4000));
        }
        if(temperature >= 6000){
            damage = 100;
            isOn = false;
            setAnimation(brokenAnimation);
            notifyDevices(false);
        }
        updateAnimation();
    }

    private void updateTemperature(int increment){
        if(damage > 66){
            temperature += (int) (2 * increment);
        }
        else if(damage > 33){
            temperature += (int) (1.5 * increment);
        }
        else{
            temperature += increment;
        }
    }

    private int calculateDamage(int curTemperature){
        return Math.min(100, Math.round((100 * curTemperature) / 4000));
    }

    private void notifyDevices(boolean isPowered){
        for(EnergyConsumer device : devices){
            device.setPowered(isPowered);
        }
    }

    public void decreaseTemperature(int decrement){
        if(decrement < 0 || !isOn || damage >= 100){
            return;
        }

        if(this.damage >= 50){
            this.temperature -= decrement / 2;
        }
        else{
            this.temperature -= decrement;
        }

        if(this.temperature < 0){
            this.temperature = 0;
        }
        updateAnimation();
    }

    public void addLight(Light light){
        addDevice(light);
    }

    public void removeLight(Light light){
        removeDevice(light);
    }

    private void updateAnimation(){
        if(damage >= 100){
            setAnimation(brokenAnimation);
        }
        else if(!isOn){
            setAnimation(offAnimation);
        }
        else if(temperature > 4000){
            setAnimation(hotAnimation);
        }
        else{
            setAnimation(normalAnimation);
        }

        float frameDuration = 0.1f - (0.0005f * damage);
        getAnimation().setFrameDuration(Math.max(frameDuration, 0.02f));
    }

    public boolean repair(Hammer hammer){
        if(hammer != null && damage > 0){
            if(hammer.repair(this)){
                int calculatedTemperature = 2000 + damage * 40;
                if (calculatedTemperature < temperature){
                    temperature = calculatedTemperature;
                }
                updateAnimation();
                return true;
            }
        }
        return false;
    }

    public boolean extinguish(FireExtinguisher extinguisher){
        if(extinguisher != null && damage >= 100){
            extinguisher.use();
            temperature = 4000;
            setAnimation(new Animation("sprites/reactor_extinguished.png"));
            return true;
        }
        return false;
    }

    @Override
    public void addedToScene(@NotNull Scene scene){
        super.addedToScene(scene);
        new PerpetualReactorHeating(1).scheduleFor(this);
    }

    public boolean isOn(){
        return isOn;
    }

    public boolean repair(){
        if(damage > 0){
            temperature = ((damage - 50) * 40) + 2000;
            damage = Math.max(0, damage - 50);
            updateAnimation();
            return true;
        }
        return false;
    }
}
