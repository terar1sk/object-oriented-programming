package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.graphics.Animation;

public class Mjolnir extends Hammer{
    public Mjolnir(){
        super("Mjolnir", 4);
        setAnimation(new Animation("sprites/mjolnir.png"));
    }
}
