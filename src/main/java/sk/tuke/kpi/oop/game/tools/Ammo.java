package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class Ammo extends AbstractActor implements Usable<Ripley>{
    public Ammo(){
        Animation ammoAnimation = new Animation("sprites/ammo.png", 16, 16);
        setAnimation(ammoAnimation);
    }

    @Override
    public void useWith(Ripley ripley){
        if(ripley == null){
            return;
        }
        if(ripley.getAmmo() < 500){
            int ammoToAdd = Math.min(50, 500 - ripley.getAmmo());
            ripley.setAmmo(ripley.getAmmo() + ammoToAdd);
            getScene().removeActor(this);
        }
    }

    @Override
    public Class<Ripley> getUsingActorClass(){
        return Ripley.class;
    }
}
