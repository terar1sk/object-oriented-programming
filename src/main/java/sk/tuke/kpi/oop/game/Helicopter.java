package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Helicopter extends AbstractActor{
    private static final int SPEED = 1;

    public Helicopter(){
        setAnimation(new Animation("sprites/heli.png", 64, 64, 0.2f, Animation.PlayMode.LOOP_PINGPONG));
    }

    public void searchAndDestroy(){
        new Loop<>(new Invoke<>(this::searchActor)).scheduleFor(this);
    }

    private void searchActor(){
        Player refActor = getScene().getLastActorByType(Player.class);
        if (refActor == null) return;

        int newPositionX = getPosX();
        int newPositionY = getPosY();
        if(getPosX() > refActor.getPosX()){
            newPositionX -= SPEED;
        }
        else if(getPosX() < refActor.getPosX()){
            newPositionX += SPEED;
        }

        if(getPosY() > refActor.getPosY()){
            newPositionY -= SPEED;
        }
        else if(getPosY() < refActor.getPosY()){
            newPositionY += SPEED;
        }

        setPosition(newPositionX, newPositionY);
        if(intersects(refActor)){
            refActor.setEnergy(refActor.getEnergy() - 1);
        }
    }
}
