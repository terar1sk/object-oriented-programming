package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class SmartCooler extends Cooler{
    private static final int MIN_TEMP = 1500;
    private static final int MAX_TEMP = 2500;

    public SmartCooler(Reactor reactor){
        super(reactor);
    }

    @Override
    public void addedToScene(@NotNull Scene scene){
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::adjustCooling)).scheduleFor(this);
    }

    private void adjustCooling(){
        Reactor reactor = getReactor();
        if(reactor == null){
            return;
        }

        int temperature = getReactor().getTemperature();
        if(temperature > MAX_TEMP && !isOn()){
            turnOn();
        }
        else if(temperature < MIN_TEMP && isOn()){
            turnOff();
        }
    }
}
