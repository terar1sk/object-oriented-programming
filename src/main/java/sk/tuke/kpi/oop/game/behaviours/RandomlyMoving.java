package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

public class RandomlyMoving implements Behaviour<Movable> {

    @Override
    public void setUp(Movable movable) {

        if (movable==null)
            return;

        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(this::randomlyMove),
                new Wait<>(2)
            )
        ).scheduleFor(movable);
    }

    public void randomlyMove(Movable actor) {
        int x = (int) (Math.random() * 3) - 1;
        int y = (int) (Math.random() * 3) - 1;

        Direction dir = null;

        for (Direction item : Direction.values())
            if (x == item.getDx() && y == item.getDy())
                dir = item;

        if (dir == null)
            return;

        actor.getAnimation().setRotation(dir.getAngle());
        new Move<>(dir, 3).scheduleFor(actor);
    }
}
