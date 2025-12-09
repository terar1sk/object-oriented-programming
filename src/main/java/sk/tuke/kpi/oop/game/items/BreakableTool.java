package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.AbstractActor;

public abstract class BreakableTool<A extends Actor> extends AbstractActor implements Usable<A> {
    private int remainingUses;

    public BreakableTool(int uses) {
        remainingUses = uses;
    }

    protected int getRemainingUses() {
        return remainingUses;
    }

    @Override
    public void useWith(A actor) {
        remainingUses--;
        if (remainingUses <= 0)
            getScene().removeActor(this);
    }
}
