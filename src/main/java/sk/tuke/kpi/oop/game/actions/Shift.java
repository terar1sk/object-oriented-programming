package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;


public class Shift<A extends Keeper> extends AbstractAction<A>{

    public Shift(){

    }

    @Override
    public void execute(float deltaTime){
        Keeper actor = getActor();
        if(actor == null || isDone() || actor.getBackpack().peek()==null){
            setDone(true);
            return;
        }

        actor.getBackpack().shift();
        setDone(true);
    }
}
