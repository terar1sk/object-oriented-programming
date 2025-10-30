package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Reactor;

public class Hammer extends BreakableTool<Reactor> implements Collectible{

    public Hammer(){
        super(2);
        setAnimation(new Animation("sprites/hammer.png"));
    }

    public Hammer(String name, int remainingUses){
        super(remainingUses);
        setAnimation(new Animation("sprites/hammer.png"));
    }

    @Override
    public void useWith(Reactor repairable){
        if(repairable == null){
            return;
        }
        else if(repairable.repair()){
            super.useWith(repairable);
        }
    }

    public boolean repair(Reactor reactor){
        if(reactor.getDamage() > 0){
            return true;
        }
        return false;
    }

    @Override
    public Class<Reactor> getUsingActorClass(){
        return Reactor.class;
    }
}
