package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Color;

public class PowerSwitch extends AbstractActor {
    private Switchable device;
    public PowerSwitch(Switchable deviceToConnect) {
        device = deviceToConnect;
        setAnimation(new Animation("sprites/switch.png"));
    }

    public void toggle() {
        if (device == null)
            return;

        if (device.isOn())
            device.turnOff();
        else
            device.turnOn();

        updateTint();
    }

    public Switchable getDevice() {
        return this.device;
    }

    public void switchOn() {
        if (device == null)
            return;

        device.turnOn();
        updateTint();
    }

    public void switchOff() {
        if (device == null)
            return;

        device.turnOff();
        updateTint();
    }

    private void updateTint() {
        if (device == null)
            return;

        if (device.isOn())
            getAnimation().setTint(Color.GRAY);
        else
            getAnimation().setTint(Color.WHITE);
    }
}
