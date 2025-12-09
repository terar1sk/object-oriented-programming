package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Teleport extends AbstractActor {

    private Teleport destination;
    private boolean arrived;

    public Teleport(Teleport target) {
        setAnimation(new Animation("sprites/lift.png"));
        destination = target;
        arrived = false;
    }

    public Teleport getDestination() {
        return destination;
    }

    public void setDestination(Teleport newDestination) {
        if (newDestination != this)
            destination = newDestination;
    }

    private void playerLeft() {
        if (!arrived)
            return;

        Actor playerReference = getScene().getFirstActorByName("Player");
        if (!playerReference.intersects(this))
            arrived = false;
    }

    public void teleportPlayer(Player player) {
        if (player == null)
            return;

        arrived = true;
        player.setPosition(getPosX() + 8, getPosY() + 8);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        if (destination == this)
            destination = null;

        new Loop<>(
            new Invoke<>( () -> {
                Player playerReference = (Player) this.getScene().getFirstActorByName("Player");
                if (playerReference == null || destination == null || arrived)
                    return;

                int a = Math.abs(playerReference.getPosX() + 16 - getPosX() - 24);
                int b = Math.abs(playerReference.getPosY() + 16 - getPosY() - 24);

                if ( a < 24 && b < 24)
                    destination.teleportPlayer( playerReference );
            })
        ).scheduleFor(this);

        new Loop<>(
            new Invoke<>(this::playerLeft)
        ).scheduleOn(scene);
    }

}
