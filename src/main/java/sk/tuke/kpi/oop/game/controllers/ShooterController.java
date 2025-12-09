package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.actions.Fire;
import sk.tuke.kpi.oop.game.characters.Armed;

public class ShooterController implements KeyboardListener {
    private Armed armedActor;

    public ShooterController(Armed armed) {
        this.armedActor = armed;
    }

    @Override
    public void keyPressed(@NotNull Input.Key key) {
        if (armedActor == null || armedActor.getFirearm() == null)
            return;

        if (key != Input.Key.SPACE)
            return;

        Fire<Armed> fire= new Fire<>();
        fire.setActor(armedActor);
        fire.scheduleFor(armedActor);
    }
}
