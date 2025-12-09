package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MovableController implements KeyboardListener{
    private Movable movableActor;
    private Input.Key pressedKeyOne;
    private Input.Key pressedKeyTwo;
    private Disposable disposable;
    private Set<Input.Key> keySet;
    private Move<Movable> lastAction = null;

    private Map<Input.Key, Direction> keyDirectionMap = Map.ofEntries(
        Map.entry(Input.Key.UP, Direction.NORTH),
        Map.entry(Input.Key.DOWN, Direction.SOUTH),
        Map.entry(Input.Key.LEFT, Direction.WEST),
        Map.entry(Input.Key.RIGHT, Direction.EAST)
        );

    public MovableController(Movable movableActor){
        this.movableActor = movableActor;
        keySet = new HashSet<>();
    }

    @Override
    public void keyPressed(@NotNull Input.Key key){
        if (!keyDirectionMap.containsKey(key))
            return;

        keySet.add(key);

        if(pressedKeyOne == null)
            pressedKeyOne = key;
        else if(pressedKeyTwo == null)
            pressedKeyTwo = key;
        mmm();
    }

    @Override
    public void keyReleased(@NotNull Input.Key key){
        if(!keyDirectionMap.containsKey(key))
            return;

        keySet.remove(key);
        if (key == pressedKeyOne)
            pressedKeyOne = null;
        if (key == pressedKeyTwo)
            pressedKeyTwo = null;
        mmm();
    }

    private void mmm(){

        Direction dir = null;

        int tmp = 0;
        for (Input.Key item: keySet) {
            if (tmp == 0)
                dir = keyDirectionMap.get(item);
            if (tmp == 1)
                dir = dir.combine(keyDirectionMap.get(item));
            tmp++;
        }

        if (lastAction != null){
            lastAction.stop();
            disposable.dispose();
            lastAction=null;
        }

        if (dir != null) {
            lastAction = new Move<>(dir, Float.MAX_VALUE);
            disposable = lastAction.scheduleFor(movableActor);
        }
    }

}
