package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;
import sk.tuke.kpi.oop.game.behaviours.Observing;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.AlienMother;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.openables.Door;

import java.util.Objects;

public class EscapeRoom implements SceneListener {
    private Ripley ripley;

    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        ripley = scene.getFirstActorByType(Ripley.class);
        if (ripley == null)
            return;

        ripley.setAmmo(100);
        Disposable movableCon = scene.getInput().registerListener(new MovableController(ripley));
        Disposable keeperCon = scene.getInput().registerListener(new KeeperController(ripley));
        Disposable shooterCon = scene.getInput().registerListener(new ShooterController(ripley));
        scene.follow(ripley);

        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> movableCon.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> keeperCon.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> shooterCon.dispose());

        scene.getMessageBus().subscribe(Door.DOOR_OPENED, (Door) ->{
            if (!Door.getName().equals("exit door"))
                return;

            movableCon.dispose();
            keeperCon.dispose();
            shooterCon.dispose();

        });
    }

    @Override
    public void sceneCreated(@NotNull Scene scene) {

    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        if (ripley != null)
            ripley.showRipleyState();
        if (((Door) Objects.requireNonNull(scene.getFirstActorByName("exit door"))).isOpen())
            scene.getGame().getOverlay().drawText("Mission accomplished", 120, 120);
    }


    public static class Factory implements ActorFactory {
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            if (name == null)
                return null;

            switch (name) {
                case "ellen":
                    return new Ripley();
                //case "energy":
                //    return new Energy();
                case "ammo":
                   return new Ammo();
                case "alien":
                    return createAlien(type);
                case "alien mother":
                    return new AlienMother();
                case "front door":
                case "exit door":
                    return new Door(name, Door.Orientation.VERTICAL);
                case "back door":
                    return new Door(name, Door.Orientation.HORIZONTAL);
                default: return null;
            }
        }

        private Alien createAlien(String type) {
            Behaviour<Movable> behaviour = null;
            if (type != null && type.equals("running"))
                behaviour = new RandomlyMoving();
            else if (type != null && type.equals("waiting1"))
                behaviour = new Observing<>(
                    Door.DOOR_OPENED,
                    actor -> actor instanceof Door && actor.getName().equals("front door"),
                    new RandomlyMoving()
                );
            else if (type != null && type.equals("waiting2"))
                behaviour = new Observing<>(
                    Door.DOOR_OPENED,
                    actor -> actor instanceof Door && actor.getName().equals("back door"),
                    new RandomlyMoving()
                );
            return new Alien(100, behaviour);
        }
    }
}
