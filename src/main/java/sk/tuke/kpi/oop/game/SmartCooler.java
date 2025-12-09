package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class SmartCooler extends Cooler{
    public SmartCooler(Reactor reactor) {
        super(reactor);
    }

    private void switchReactor() {
        Reactor reactor = super.getReactor();
        if (reactor == null)
            return;

        int temp = reactor.getTemperature();

        if (temp < 1500)
            super.turnOff();
        if (temp > 2500)
            super.turnOn();
    }
    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::switchReactor)).scheduleFor(this);
    }
}
