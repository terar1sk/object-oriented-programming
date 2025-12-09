package sk.tuke.kpi.oop.game.openables;

import sk.tuke.kpi.gamelib.Actor;

public class LockedDoor extends Door{

    private boolean lock;

    public LockedDoor() {
        super();
        lock = true;
    }

    public void lock() {
        lock = true;
        close();
    }

    public void unlock() {
        lock = true;
        open();
    }

    @Override
    public void useWith(Actor actor) {
        if (!lock)
            super.useWith(actor);
    }
}
