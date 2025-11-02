package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class Teleport extends AbstractActor{

    private Teleport destination;
    private boolean recentlyTeleportedIn;

    public Teleport(Teleport destinationTeleport){
        super();
        this.recentlyTeleportedIn = false;
        if(destinationTeleport == this){
            this.destination = null;
        }
        else{
            this.destination = destinationTeleport;
        }

        setAnimation(new Animation("sprites/lift.png"));
    }

    public Teleport getDestination(){
        return destination;
    }

    public void setDestination(Teleport destinationTeleport){
        if(destinationTeleport == this)return;
        this.destination = destinationTeleport;
    }

    public void teleportPlayer(Ripley player){
        if(player == null)return;

        float targetCenterX = getPosX() + (float) getAnimation().getWidth() / 2;
        float targetCenterY = getPosY() + (float) getAnimation().getHeight() / 2;
        float newX = targetCenterX - (float) player.getAnimation().getWidth() / 2;
        float newY = targetCenterY - (float) player.getAnimation().getHeight() / 2;

        player.setPosition((int) newX, (int) newY);
        this.recentlyTeleportedIn = true;
    }

    public void checkTeleportActivation(Ripley player){
        if(player == null || destination == null)return;

        float px = player.getPosX() + (float) player.getAnimation().getWidth() / 2;
        float py = player.getPosY() + (float) player.getAnimation().getHeight() / 2;
        float tx = getPosX();
        float ty = getPosY();
        float tw = (float) getAnimation().getWidth();
        float th = (float) getAnimation().getHeight();

        boolean inside = (px >= tx && px <= tx + tw && py >= ty && py <= ty + th);
        if(inside){
            if(!recentlyTeleportedIn && destination != null && destination != this){
                destination.teleportPlayer(player);
            }
        }
        else{
            recentlyTeleportedIn = false;
        }
    }
}
