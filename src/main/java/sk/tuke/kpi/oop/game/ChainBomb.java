package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class ChainBomb extends TimeBomb{
    public ChainBomb(float time) {
        super(time);
    }

    public void activateChain() {
        super.activate();
    }

    @Override
    public void explode() {
        super.explode();
        Ellipse2D.Float Ellipse = new Ellipse2D.Float(getPosX()-50, getPosY()-50, 102, 102);

        List<Actor> actorsOnScene = getScene().getActors();

        actorsOnScene.forEach( (Actor actor) -> {
            if (actor instanceof ChainBomb && !((ChainBomb)actor).isActivated()) {
                Rectangle2D.Float nextChainBomb = new Rectangle2D.Float(actor.getPosX()-actor.getWidth()/2,
                                                                        actor.getPosY()-actor.getHeight()/2,
                                                                           actor.getWidth(), actor.getHeight() );

                if (Ellipse.intersects(nextChainBomb)) {
                    ((ChainBomb) actor).activate();}
            }
        });
    }
}
