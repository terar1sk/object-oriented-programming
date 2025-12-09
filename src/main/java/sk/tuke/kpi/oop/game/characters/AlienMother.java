package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.graphics.Animation;

public class AlienMother extends Alien{
    public AlienMother() {
        super(200, null);
        setAnimation(new Animation("sprites/mother.png", 112,162, 0.2f,
            Animation.PlayMode.LOOP_PINGPONG));
    }
}
