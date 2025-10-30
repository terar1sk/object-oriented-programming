package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.oop.game.items.Backpack;
import sk.tuke.kpi.gamelib.Actor;

public interface Keeper extends Actor{
    Backpack getBackpack();
}
