package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.Scenario;
import sk.tuke.kpi.oop.game.tools.Hammer;

public class Gameplay extends Scenario{

    @Override
    public void setupPlay(Scene scene){
        Reactor reactor = new Reactor();
        scene.addActor(reactor, 64, 64);
        reactor.turnOn();

        Cooler cooler = new Cooler(reactor);
        scene.addActor(cooler, 128, 64);
        new ActionSequence<>(
            new Wait<>(5),
            new Invoke<>(cooler::turnOn)
        ).scheduleFor(cooler);

        Hammer hammer = new Hammer("Hammer", 10);
        new When<>(
            () -> reactor.getTemperature() >= 3000,
            new Invoke<>(() -> reactor.repair(hammer))
        ).scheduleFor(reactor);
    }

    public void someOtherScenario(Scene scene){
        Reactor secondReactor = new Reactor();
        scene.addActor(secondReactor, 300, 64);
        secondReactor.turnOn();

        Cooler secondCooler = new Cooler(secondReactor);
        scene.addActor(secondCooler, 364, 64);
        new ActionSequence<>(
            new Wait<>(5),
            new Invoke<>(secondCooler::turnOn)
        ).scheduleFor(secondCooler);
    }
}
