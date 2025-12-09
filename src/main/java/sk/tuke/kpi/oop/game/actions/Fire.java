package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Armed;
import sk.tuke.kpi.oop.game.weapons.Fireable;

import java.util.Objects;


public class Fire <A extends Armed> extends AbstractAction<A> {

    @Override
    public void execute(float deltaTime) {
        Armed ac = getActor();
        if ( ac == null) {
            setDone(true);
            return;
        }

        if (isDone()) {
            return;
        }

        Fireable fireable = ac.getFirearm().fire();
        int pomX = Direction.fromAngle(ac.getAnimation().getRotation()).getDx();
        int pomY = Direction.fromAngle(ac.getAnimation().getRotation()).getDy();

        if (fireable!=null) {
            Objects.requireNonNull(ac.getScene()).addActor(fireable, ac
                .getPosX() + 8 + pomX * 24, ac.getPosY() + 8 + pomY * 24);

            fireable.
                startedMoving(Direction.fromAngle(ac.getAnimation().getRotation()));

            new Move<Fireable>(Direction.fromAngle(ac.getAnimation().getRotation()),Float.MAX_VALUE)
                .scheduleFor(fireable);
        }
        setDone(true);
    }
}
