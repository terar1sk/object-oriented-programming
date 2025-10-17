package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor implements Switchable, EnergyConsumer{
    private boolean isOn;
    private boolean isPowered;

    public Light(){
        super("Light");
        this.isOn = false;
        this.isPowered = false;
        setAnimation(new Animation("sprites/light_off.png"));
    }

    public void toggle(){
        isOn = !isOn;
        updateAnimation();
    }

    public void turnOn(){
        this.isOn = true;
        updateAnimation();
    }

    public void turnOff(){
        this.isOn= false;
        updateAnimation();
    }

    public boolean isOn(){
        return isOn;
    }

    public void setPowered(boolean powered){
        this.isPowered = powered;
        updateAnimation();
    }

    private void updateAnimation(){
        if(isOn && isPowered){
            setAnimation(new Animation("sprites/light_on.png"));
        }
        else{
            setAnimation(new Animation("sprites/light_off.png"));
        }
    }
}
