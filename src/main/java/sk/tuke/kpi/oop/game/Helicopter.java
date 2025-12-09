package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;


public class Helicopter extends AbstractActor {

    private Player player;

    public Helicopter() {
        setAnimation(new Animation("sprites/heli.png", 64, 64,
            0.1f, Animation.PlayMode.LOOP_PINGPONG)
        );
        player = null;
    }
    public void searchAndDestroy() {
        if (player == null) {
            this.player = (Player) super.getScene().getFirstActorByName("Player");
            new Loop<>(new Invoke<>(this::searchAndDestroy)).scheduleFor(this);
            return;
        }

        int playerX = player.getPosX();
        int playerY = player.getPosY();
        int heliX = getPosX();
        int heliY = getPosY();

        if      (playerX > heliX) heliX++;
        else if (playerX < heliX) heliX--;

        if      (playerY > heliY) heliY++;
        else if (playerY < heliY) heliY--;

        this.setPosition(heliX, heliY);

        if (intersects(player))
            player.setEnergy(player.getEnergy() - 1);
    }
}
