package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.*;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.actions.Take;


public class FirstSteps implements SceneListener{
    private Ripley ripley;
    private Energy energy;
    private Ammo ammo;

    private Hammer hammer = new Hammer();
    private FireExtinguisher fireExtinguisher = new FireExtinguisher();
    private Wrench wrench = new Wrench();

    @Override
    public void sceneInitialized(@NotNull Scene scene){
        ripley = new Ripley();
        scene.addActor(ripley, 0, 0);

        MovableController movableController = new MovableController(ripley);
        scene.getInput().registerListener(movableController);

        KeeperController keeperController = new KeeperController(ripley);
        scene.getInput().registerListener(keeperController);

        energy = new Energy();
        scene.addActor(energy, 100, 50);

        new When<>(
            () -> ripley.intersects(energy),
            new Invoke<>(() -> energy.useWith(ripley))
        ).scheduleFor(ripley);

        ammo = new Ammo();
        scene.addActor(ammo, -100, 50);

        new When<>(
            () -> ripley.intersects(ammo),
            new Invoke<>(() -> ammo.useWith(ripley))
        ).scheduleFor(ripley);

        scene.addActor(hammer, 230, 90);
        scene.addActor(fireExtinguisher, 220, 10);
        scene.addActor(wrench, -150, 200);

        new When<>(
            () -> ripley.intersects(fireExtinguisher),
            new Invoke<>(() -> new Take<Ripley>().scheduleFor(ripley))
        ).scheduleFor(ripley);

        new When<>(
            () -> ripley.intersects(wrench),
            new Invoke<>(() -> new Take<Ripley>().scheduleFor(ripley))
        ).scheduleFor(ripley);

        new When<>(
            () -> ripley.intersects(hammer),
            new Invoke<>(() -> new Take<Ripley>().scheduleFor(ripley))
        ).scheduleFor(ripley);


        /*
        ripley.getBackpack().add(hammer);
        ripley.getBackpack().add(fireExtinguisher);
        ripley.getBackpack().add(wrench);

         */
        scene.getGame().pushActorContainer(ripley.getBackpack());

        /*
        new When<>(
            () -> ripley.getBackpack().peek() != null,
            new Invoke<>(() -> new Drop<Ripley>().scheduleFor(ripley))
        ).scheduleFor(ripley);
        */
        ripley.getBackpack().shift();
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene){
        int windowHeight = scene.getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        scene.getGame().getOverlay().drawText("Energy: " + ripley.getEnergy(), 120, yTextPos);
        scene.getGame().getOverlay().drawText("Ammo: " + ripley.getAmmo(), 250, yTextPos);
    }
}
