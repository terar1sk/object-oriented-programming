package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.Locker;
import sk.tuke.kpi.oop.game.Ventilator;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.AccessCard;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.items.Hammer;
import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.openables.LockedDoor;


public class MissionImpossible implements SceneListener {

    private Ripley ripley;
    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        ripley = scene.getFirstActorByType(Ripley.class);

        if (ripley == null)
            return;

        ripley.setAmmo(100);
        Disposable movableCon = scene.getInput().registerListener(new MovableController(ripley));
        Disposable keeperCon = scene.getInput().registerListener(new KeeperController(ripley));
        scene.follow(ripley);

        var hammer = new Hammer();
        scene.addActor(hammer, 30,30);

        ripley.getBackpack().add(hammer);

        scene.getMessageBus().subscribe(Door.DOOR_OPENED, (Ripley) -> ripley.decreaseEnergy());

        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> movableCon.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> keeperCon.dispose());

        scene.getMessageBus().subscribe(Ventilator.VENTILATOR_REPAIRED, (Ripley) -> ripley.removeDisposable());

    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        if (ripley != null)
            ripley.showRipleyState();
    }

    public static class Factory implements ActorFactory {
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            if (name == null)
                return null;

            switch (name) {
                case "ellen":
                    return new Ripley();
                case "energy":
                    return new Energy();
                case "door":
                    return new LockedDoor();
                case "access card":
                    return new AccessCard();
                case "locker":
                    return new Locker();
                case "ventilator":
                    return new Ventilator();
                default: return null;
            }
        }
    }
}
