package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

public class Drop <A extends Keeper> extends AbstractAction<A>{

    public Drop(){

    }

    @Override
    public void execute(float deltaTime) {

        Keeper actor = getActor();
        if(actor == null || isDone() || actor.getBackpack().peek() == null || actor.getScene() == null){
            setDone(true);
            return;
        }

        Collectible item = getActor().getBackpack().peek();
        if(item == null)
            return;

        actor.getScene().addActor(item,
                                    (actor.getPosX() + (actor.getWidth()-item.getWidth()/2)),
                                    (actor.getPosY() + (actor.getHeight()-item.getHeight()/2))
        );
        actor.getBackpack().remove(item);

        setDone(true);
    }
}
