package sk.tuke.kpi.oop.game.weapons;

public abstract class Firearm {

    private int maxBullets;
    private int curBullets;

    public Firearm(int start, int max) {
        maxBullets = max;
        curBullets = start;
        if (start > max)
            curBullets = max;
    }

    public Firearm(int max) {
        maxBullets = max;
        curBullets = max;
    }

    public int getAmmo() {
        return curBullets;
    }

    public int getMaxAmmo() {
        return maxBullets;
    }

    public void reload(int newAmmo) {
        curBullets += newAmmo;
        if (curBullets > maxBullets)
            curBullets = maxBullets;
    }

    public Fireable fire() {
        if (curBullets <= 0)
            return null;

        curBullets--;
        return createBullet();
    }

    protected abstract Fireable createBullet();
}
