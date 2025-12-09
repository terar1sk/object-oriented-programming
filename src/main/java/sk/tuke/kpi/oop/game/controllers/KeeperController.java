package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.actions.Drop;
import sk.tuke.kpi.oop.game.actions.Shift;
import sk.tuke.kpi.oop.game.actions.Take;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.items.Usable;

import java.util.Objects;

public class KeeperController implements KeyboardListener{

    private Keeper keeper;

    public KeeperController(Keeper keeper) {
        this.keeper = keeper;
    }

    @Override
    public void keyPressed(@NotNull Input.Key key){
        switch(key){
            case ENTER: new Take<>().scheduleFor(keeper); break;
            case BACKSPACE: new Drop<>().scheduleFor(keeper); break;
            case S: new Shift<>().scheduleFor(keeper); break;
            case U:
                Usable<?> usable = Objects.requireNonNull(keeper.getScene())
                                    .getActors()
                                    .stream()
                                    .filter(Usable.class::isInstance)
                                    .filter(keeper::intersects)
                                    .map(Usable.class::cast)
                                    .findFirst()
                                    .orElse(null);
                if(usable != null)
                    new Use<>(usable).scheduleForIntersectingWith(keeper);

                break;
            case B:
                if(keeper.getBackpack().peek() instanceof Usable){
                    Use<?> use = new Use<>((Usable<?>)keeper.getBackpack().peek());
                    use.scheduleForIntersectingWith(keeper);
                }
                break;
            default: break;
        }
    }
}
