package sk.tuke.kpi.oop.game.controllers;

import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.gamelib.Input;
import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.Map;
import java.util.HashSet;
import java.util.Set;

public class MovableController implements KeyboardListener {
    private Movable actor;
    private Set<Input.Key> pressedKeys;
    private Input.Key button_a =null;
    private Input.Key button_b =null;
    private Map<Input.Key, Direction> keyDirectionMap = Map.ofEntries(Map.entry(Input.Key.UP, Direction.NORTH), Map.entry(Input.Key.RIGHT, Direction.EAST), Map.entry(Input.Key.DOWN, Direction.SOUTH), Map.entry(Input.Key.LEFT, Direction.WEST));
    private Move<Movable> currentMove;

    public MovableController(Movable actor){
        this.actor = actor;
        this.pressedKeys = new HashSet<>();
    }

    @Override
    public void keyPressed(@NotNull Input.Key key){
        if(keyDirectionMap.containsKey(key)){
            pressedKeys.add(key);
            if(button_a==null){
                button_a = key;
            }
            else if(button_b==null){
                button_b=key;
            }
            updateMove();
        }
    }

    private void updateMove(){
        Direction combinedDirection = Direction.NONE;
        for(Input.Key key : pressedKeys){
            combinedDirection = combinedDirection.combine(keyDirectionMap.get(key));
        }
        stopMoving();
        if(combinedDirection != Direction.NONE){
            currentMove = new Move<>(combinedDirection, Float.MAX_VALUE);
            currentMove.scheduleFor(actor);
        }
    }

    private void stopMoving(){
        if(currentMove != null){
            currentMove.stop();
            currentMove = null;
        }
    }

    @Override
    public void keyReleased(@NotNull Input.Key key){
        if(keyDirectionMap.containsKey(key)){
            pressedKeys.remove(key);
            updateMove();
        }
    }
}
