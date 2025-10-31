package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class Energy extends AbstractActor implements Usable<Ripley>{
    public Energy(){
        Animation energyAnimation = new Animation("sprites/energy.png", 16, 16);
        setAnimation(energyAnimation);
    }

    @Override
    public void useWith(Ripley ripley){
        if(ripley == null){
            return;
        }
        if(ripley.getEnergy() < 100){
            ripley.setEnergy(100);
            getScene().removeActor(this);
        }
    }

    @Override
    public Class<Ripley> getUsingActorClass(){
        return Ripley.class;
    }
}


