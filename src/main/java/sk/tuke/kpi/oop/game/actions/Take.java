package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

import java.util.List;

public class Take<A extends Keeper> extends AbstractAction<A>{


    public Take(){

    }

    @Override
    public void execute(float deltaTime){
        if(isDone())
            return;

        setDone(true);
        if(getActor() == null || getActor().getScene()==null)
            return;

        Scene scene = getActor().getScene();
        List<Actor> actorList = scene.getActors();
        for(Actor item: actorList){
            if(item instanceof Collectible && item.intersects(getActor())){
                try{
                    getActor().getBackpack().add((Collectible) item);
                    scene.removeActor(item);
                    break;
                } catch (IllegalStateException e){
                    scene.getOverlay().drawText(e.getMessage(), 0, 0).showFor(2);
                }
            }
        }

    }
}
