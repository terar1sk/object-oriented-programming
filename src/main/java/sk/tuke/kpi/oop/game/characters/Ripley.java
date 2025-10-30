package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Backpack;

public class Ripley extends AbstractActor implements Movable, Keeper{
    private Animation ripleyAnimation;
    private int speed = 2;
    private int energy = 50;
    private int ammo = 150;
    private Backpack backpack;

    public Ripley(){
        super("Ellen");
        ripleyAnimation = new Animation("sprites/player.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(ripleyAnimation);
        this.backpack = new Backpack("Ripley's backpack", 10);
    }

    @Override
    public int getSpeed(){
        return speed;
    }

    @Override
    public void startedMoving(Direction direction){
        ripleyAnimation.setRotation(direction.getAngle());
        ripleyAnimation.play();
    }

    @Override
    public void stoppedMoving(){
        ripleyAnimation.stop();
    }

    public int getEnergy(){
        return energy;
    }

    public void setEnergy(int energy){
        if(energy < 0){
            this.energy = 0;
        }
        else if(energy > 100){
            this.energy = 100;
        }
        else{
            this.energy = energy;
        }
    }

    public int getAmmo(){
        return ammo;
    }

    public void setAmmo(int ammo){
        if(ammo < 0){
            this.ammo = 0;
        }
        else if(ammo > 500){
            this.ammo = 500;
        }
        else{
            this.ammo = ammo;
        }
    }

    @Override
    public Backpack getBackpack(){
        return this.backpack;
    }

    /*
    public void showRipleyState(){
        int windowHeight = Objects.requireNonNull(getScene()).getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        getScene().getGame().getOverlay().drawText("Energy " + energy, 120, yTextPos);
        getScene().getGame().getOverlay().drawText("Ammo: " + ammo, 250, yTextPos);
    }
     */
}
