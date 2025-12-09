package sk.tuke.kpi.oop.game.weapons;

public class Gun extends Firearm{

    public Gun(int start, int max) {
        super(start, max);
    }
    @Override
    protected Fireable createBullet() {
        return new Bullet();
    }
}
