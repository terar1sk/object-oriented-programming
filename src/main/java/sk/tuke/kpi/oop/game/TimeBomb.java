package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class TimeBomb extends AbstractActor{
    private Animation explodedBomb;
    private float time;
    private boolean isActivated;
    private boolean isExploded;
    private Animation activatedBomb;

    public TimeBomb(float time){
        this.time = time;
        isActivated = false;
        isExploded = false;

        Animation normalBomb = new Animation("sprites/bomb.png");
        activatedBomb = new Animation("sprites/bomb_activated.png", 16, 16, 0.2f);
        explodedBomb = new Animation("sprites/small_explosion.png", 64, 32, 0.1f, Animation.PlayMode.ONCE);
        setAnimation(normalBomb);
    }

    public boolean isActivated(){
        return isActivated;
    }

    public boolean isExploded(){
        return isExploded;
    }

    public float getTime(){
        return time;
    }

    public void activate(){
        if(!isActivated && !isExploded){
            isActivated = true;
            setAnimation(activatedBomb);
            new ActionSequence<>(
                new Wait<>(this.time),
                new Invoke<>(this::explode),
                new Invoke<>(this::remove)
            ).scheduleFor(this);
        }
    }

    private void remove(){
        getScene().removeActor(this);
    }

    public void explode(){
        if (!isExploded){
            setAnimation(explodedBomb);
            isExploded = true;
        }
    }
}
