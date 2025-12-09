package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.*;

public class FirstSteps implements SceneListener {

    private Ripley ripley;
    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        ripley = new Ripley();
        ripley.setAmmo(50);
        scene.addActor(ripley, 0, 0);
        scene.getInput().registerListener(new MovableController(ripley));
        scene.getInput().registerListener(new KeeperController(ripley));

        var hammer = new Hammer();
        var hammer1 = new Hammer();
        var fireExtinguisher = new FireExtinguisher();
        var fireExtinguisher1 = new FireExtinguisher();
        var hammer2 = new Hammer();
        var hammer3 = new Hammer();
        var fireExtinguisher2 = new FireExtinguisher();
        var fireExtinguisher3 = new FireExtinguisher();
        var wrench = new Wrench();

        scene.addActor(hammer, 100, -50);
        scene.addActor(hammer1, 200, -50);
        scene.addActor(fireExtinguisher, 120, 40);
        scene.addActor(fireExtinguisher1, 130, 40);
        scene.addActor(hammer2, 100, -50);
        scene.addActor(hammer3, 200, -50);
        scene.addActor(fireExtinguisher2, 120, 40);
        scene.addActor(fireExtinguisher3, 130, 40);
        scene.addActor(wrench, -150, 200);

        ripley.getBackpack().add(hammer);
        ripley.getBackpack().add(wrench);
        ripley.getBackpack().add(fireExtinguisher);

        scene.getGame().pushActorContainer(ripley.getBackpack());
        ripley.getBackpack().shift();
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        ripley.showRipleyState();
    }

}
