package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class ChainBomb extends TimeBomb{

    public ChainBomb(float time){
        super(time);
    }

    @Override
    public void explode(){
        super.explode();
        Ellipse2D.Float explosionArea = new Ellipse2D.Float(
            this.getPosX() - 50,
            this.getPosY() - 50,
            100,
            100
        );

        List<Actor> actorsList = getScene().getActors();
        for(Actor actor : actorsList){
            if(actor instanceof ChainBomb && !((ChainBomb) actor).isActivated()){
                Rectangle2D.Float actorBounds = new Rectangle2D.Float(
                    actor.getPosX() - actor.getWidth() / 2,
                    actor.getPosY() - actor.getHeight() / 2,
                    actor.getWidth(),
                    actor.getHeight()
                );
                if(explosionArea.intersects(actorBounds)){
                    ((ChainBomb) actor).activate();
                }
            }
        }
    }
}
