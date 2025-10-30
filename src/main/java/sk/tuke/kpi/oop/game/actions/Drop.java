package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.items.Collectible;
import sk.tuke.kpi.oop.game.Keeper;

public class Drop <A extends Keeper> extends AbstractAction<A>{
    @Override
    public void execute(float deltaTime){
        A keeper = getActor();
        if(keeper == null || keeper.getScene() == null || keeper.getBackpack() == null){
            setDone(true);
            return;
        }
        Collectible item = keeper.getBackpack().peek();
        if(item == null){
            setDone(true);
            return;
        }
        int centerX = keeper.getPosX() + keeper.getWidth() / 2 - item.getWidth() / 2;
        int centerY = keeper.getPosY() + keeper.getHeight() / 2 - item.getHeight() / 2;
        keeper.getScene().addActor(item, centerX, centerY);
        keeper.getBackpack().remove(item);
        setDone(true);
    }
}
