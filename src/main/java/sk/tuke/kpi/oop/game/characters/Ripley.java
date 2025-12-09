package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.items.Backpack;
import sk.tuke.kpi.oop.game.weapons.Firearm;
import sk.tuke.kpi.oop.game.weapons.Gun;

import java.util.Objects;

public class Ripley extends AbstractActor implements Movable, Keeper, Alive, Armed {
    private Backpack backpack;
    private final int speed = 2;
    private int ammo;
    private Firearm gun;

    private Health health;
    public static final Topic<Ripley> RIPLEY_DIED = Topic.create("ripley died", Ripley.class);
    private Disposable disposable = null;
    public Ripley() {
        super("Ellen");
        setAnimation(new Animation("sprites/player.png", 32, 32,
                        0.1f, Animation.PlayMode.LOOP_PINGPONG));
        stoppedMoving();
        backpack = new Backpack("Ripley's backpack", 10);
        health = new Health(100);
        health.onExhaustion( () -> {
            this.setAnimation(new Animation("sprites/player_die.png",32,32,0.1f,
                Animation.PlayMode.ONCE));
            Objects.requireNonNull(getScene()).getMessageBus().publish(RIPLEY_DIED,this);
        });
        setFirearm(new Gun(30,30));
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void startedMoving(Direction direction) {
        getAnimation().play();
        getAnimation().setRotation(direction.getAngle());
    }

    @Override
    public void stoppedMoving() {
        getAnimation().stop();
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int n) {
        ammo = n;
    }

    @Override
    public Backpack getBackpack() {
        return backpack;
    }

    public void showRipleyState() {
        Scene scene = getScene();
        if (scene == null)
            return;
        int windowHeight = scene.getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        scene.getGame().getOverlay().drawText("Energy: " + health.getValue(), 120, yTextPos);
        scene.getGame().getOverlay().drawText("Ammo: " + gun.getAmmo() + "/" + gun.getMaxAmmo(), 320, yTextPos);
    }

    public void decreaseEnergy() {
        if (disposable != null)
            return;

        disposable = new Loop<>(
            new ActionSequence<>(
                new Invoke<>(() -> health.drain(1)),
                new Wait<>(1)
            )
        ).scheduleFor(this);
    }

    public void removeDisposable() {
        if (disposable == null)
            return;

        disposable.dispose();
        disposable = null;
    }

    @Override
    public Health getHealth() {
        return health;
    }

    @Override
    public Firearm getFirearm() {
        return gun;
    }

    @Override
    public void setFirearm(Firearm weapon) {
        gun = weapon;
    }
}
