package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Computer extends AbstractActor implements EnergyConsumer{
    private Animation poweredAnimation;
    private Animation offAnimation;
    private boolean powered;

    public Computer(){
        this.poweredAnimation = new Animation("sprites/computer.png", 80, 48, 0.2f);
        this.offAnimation = new Animation("sprites/computer_off.png", 80, 48, 0.2f);
        this.powered = false;
        setAnimation(offAnimation);
    }

    public int add(int a, int b){
        if(!powered){
            return 0;
        }
        return a + b;
    }

    public float add(float a, float b){
        if(!powered){
            return 0;
        }
        return a + b;
    }

    public int sub(int a, int b){
        if(!powered){
            return 0;
        }
        return a - b;
    }

    public float sub(float a, float b){
        if(!powered){
            return 0;
        }
        return a - b;
    }

    public void setPowered(boolean powered){
        this.powered = powered;
        if(powered){
            setAnimation(poweredAnimation);
        }
        else{
            setAnimation(offAnimation);
        }
    }

    public boolean isPowered(){
        return powered;
    }
}
