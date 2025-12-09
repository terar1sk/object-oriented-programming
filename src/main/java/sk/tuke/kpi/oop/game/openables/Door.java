package sk.tuke.kpi.oop.game.openables;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.items.Usable;

import java.util.Objects;

public class Door extends AbstractActor implements Openable, Usable<Actor> {

    private boolean isOpened;

    public static final Topic<Door> DOOR_OPENED = Topic.create("door opened", Door.class);
    public static final Topic<Door> DOOR_CLOSED = Topic.create("door closed", Door.class);
    public enum  Orientation { VERTICAL, HORIZONTAL }
    private Animation openedDoorAnimation;
    private Animation closedDoorAnimation;

    private Orientation orientation;

    public Door() {
        String vDoorImageName = "sprites/vdoor.png";
        isOpened = false;
        closedDoorAnimation = new Animation(vDoorImageName, 16, 32, 0.1f, Animation.PlayMode.ONCE);
        openedDoorAnimation = new Animation(vDoorImageName, 16, 32, 0.1f, Animation.PlayMode.ONCE_REVERSED);
        orientation = Orientation.VERTICAL;
        setAnimation(new Animation(vDoorImageName, 16, 32));
        getAnimation().stop();
    }


    public Door (String name, Orientation orientation) {
        super(name);
        String vDoorImageName = "sprites/vdoor.png";
        String hDoorImageName = "sprites/hdoor.png";

        isOpened = false;
        this.orientation = orientation;

        if (orientation == Orientation.HORIZONTAL) {
            closedDoorAnimation = new Animation(hDoorImageName, 32, 16, 0.1f, Animation.PlayMode.ONCE);
            openedDoorAnimation = new Animation(hDoorImageName, 32, 16, 0.1f, Animation.PlayMode.ONCE_REVERSED);
            setAnimation(new Animation(hDoorImageName, 32, 16));
            getAnimation().stop();
        }
        else if (orientation == Orientation.VERTICAL) {
            closedDoorAnimation = new Animation(vDoorImageName, 16, 32, 0.1f, Animation.PlayMode.ONCE);
            openedDoorAnimation = new Animation(vDoorImageName, 16, 32, 0.1f, Animation.PlayMode.ONCE_REVERSED);
            setAnimation(new Animation(vDoorImageName, 16, 32));
            getAnimation().stop();
        }

    }


    @Override
    public void open() {
        if (isOpened)
            return;

        isOpened = true;

        if (orientation == Orientation.VERTICAL) {
            Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.CLEAR);
            getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16 + 1).setType(MapTile.Type.CLEAR);
        } else {
            Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.CLEAR);
            getScene().getMap().getTile(this.getPosX() / 16 + 1, this.getPosY() / 16).setType(MapTile.Type.CLEAR);
        }
        setAnimation(openedDoorAnimation);
        openedDoorAnimation.play();
        openedDoorAnimation.stop();
        getScene().getMessageBus().publish(DOOR_OPENED, this);
    }

    @Override
    public void close() {
        if (!isOpened)
            return;
        isOpened = false;

        if (orientation == Orientation.VERTICAL) {
            Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.WALL);
            getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16 + 1).setType(MapTile.Type.WALL);
        } else {
            Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.WALL);
            getScene().getMap().getTile(this.getPosX() / 16 + 1, this.getPosY() / 16).setType(MapTile.Type.WALL);
        }
        setAnimation(closedDoorAnimation);
        getAnimation().play();
        getAnimation().stop();
        getScene().getMessageBus().publish(DOOR_CLOSED, this);
    }

    @Override
    public boolean isOpen() {
        return isOpened;
    }

    @Override
    public void useWith(Actor actor) {
        if (isOpened)
            close();
        else
            open();
    }

    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);
        if (orientation == Orientation.VERTICAL) {
            Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.WALL);
            getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16 + 1).setType(MapTile.Type.WALL);
        } else {
            Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.WALL);
            getScene().getMap().getTile(this.getPosX() / 16 + 1, this.getPosY() / 16).setType(MapTile.Type.WALL);
        }
    }
}
