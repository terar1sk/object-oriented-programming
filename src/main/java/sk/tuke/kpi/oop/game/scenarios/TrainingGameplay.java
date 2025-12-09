package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.Scenario;
import sk.tuke.kpi.oop.game.Cooler;
import sk.tuke.kpi.oop.game.Reactor;
import sk.tuke.kpi.oop.game.Teleport;
import sk.tuke.kpi.oop.game.items.Hammer;

public class TrainingGameplay extends Scenario {
    public void setupPlay(@NotNull Scene scene) {
        Reactor reactor = new Reactor();
        scene.addActor(reactor, 64, 64);
        reactor.turnOn();

        Reactor reactorBroken = new Reactor();
        scene.addActor(reactorBroken, 164, 164);
        reactorBroken.turnOn();
        reactorBroken.increaseTemperature(5000);

        Hammer hammer = new Hammer();
        scene.addActor(hammer, 150, 150);

        Cooler cooler = new Cooler(reactor);
        scene.addActor(cooler, 250, 250);

        Teleport t1 = new Teleport(null);
        scene.addActor(t1, 75, 250);

        Teleport t2 = new Teleport(t1);
        scene.addActor(t2, 250, 300);
        t1.setDestination(t2);

        new ActionSequence<>(
            new Wait<>(5),
            new Invoke<>(cooler::turnOn)
        ).scheduleFor(cooler);



        /*new When<>(
            () -> reactor.getTemperature() >= 3000,
            new Invoke<>(() -> reactor.repair(hammer))
        ).scheduleFor(reactor);*/
    }
}
