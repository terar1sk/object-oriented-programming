package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.tools.Collectible;
import sk.tuke.kpi.gamelib.Scene;

import java.util.List;

public class Take<K extends Keeper> extends AbstractAction<K>{
    @Override
    public void execute(float deltaTime){
        K keeper = getActor();
        if(getActor() == null || getActor().getScene() == null){
            setDone(true);
            return;
        }
        Scene scene=keeper.getScene();
        List<Actor> actors = scene.getActors();
        for(Actor actor : actors){
            if(actor instanceof Collectible && actor.intersects(keeper)){
                try{
                    keeper.getBackpack().add((Collectible) actor);
                    scene.removeActor(actor);
                    break;
                }
                catch(IllegalStateException exception){
                    scene.getOverlay()
                        .drawText(exception.getMessage(), 10, 10)
                        .showFor(2);
                }
            }
        }
        setDone(true);
    }
}
