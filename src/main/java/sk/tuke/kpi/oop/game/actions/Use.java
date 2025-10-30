package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.oop.game.items.Usable;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;

public class Use<A extends Actor> extends AbstractAction<A>{
    private Usable<A> usableActor;

    public Use(Usable<A> usableActor){
        this.usableActor = usableActor;
    }

    @Override
    public void execute(float deltaTime){
        if(!isDone()){
            usableActor.useWith(getActor());
            setDone(true);
        }
    }

    public void scheduleForIntersectingWith(Actor mediatingActor){
        var scene = mediatingActor.getScene();
        if(scene == null){
            return;
        }
        Class<A> usingActorClass = usableActor.getUsingActorClass();
        for(Actor actor : scene.getActors()){
            if(mediatingActor != actor && usingActorClass.isInstance(actor)){
                scheduleFor(usingActorClass.cast(actor));
                break;
            }
        }
    }
}

