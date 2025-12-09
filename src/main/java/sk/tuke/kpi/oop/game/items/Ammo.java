package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Armed;
import sk.tuke.kpi.oop.game.weapons.Firearm;

import java.util.Objects;

public class Ammo extends AbstractActor implements Usable<Armed> {

    public Ammo() {
        setAnimation(new Animation("sprites/ammo.png"));
    }
    @Override
    public void useWith(Armed actor) {
        if (actor == null)
            return;

        Firearm firearm = actor.getFirearm();
        if (firearm.getAmmo() >= firearm.getMaxAmmo())
            return;

        firearm.reload(50);
        Objects.requireNonNull(getScene()).removeActor(this);
    }

    @Override
    public Class<Armed> getUsingActorClass() {
        return Armed.class;
    }

}
