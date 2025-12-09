package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.Scenario;

public class Gameplay extends Scenario {

    @Override
    public void setupPlay(@NotNull Scene scene) {
        Reactor reactor = new Reactor();
        scene.addActor(reactor, 64, 64);
        reactor.turnOn();

        DefectiveLight light = new DefectiveLight();
        scene.addActor(light, 256, 256);
        light.turnOn();
        reactor.addDevice(light);

        ChainBomb bomb = new ChainBomb(5);
        scene.addActor(bomb, 50, 50);

        ChainBomb bomb2 = new ChainBomb(5);
        scene.addActor(bomb2, 70, 70);

    }
}
