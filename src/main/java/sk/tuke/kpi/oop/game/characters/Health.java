package sk.tuke.kpi.oop.game.characters;

import java.util.ArrayList;
import java.util.List;

public class Health {

    private int curHealth;
    private final int maxHealth;

    private List<ExhaustionEffect> efects;

    public Health(int startingHealth, int maxHealth) {
        curHealth = startingHealth;
        this.maxHealth = maxHealth;
        efects = new ArrayList<>();
    }

    public Health(int health) {
        curHealth = health;
        this.maxHealth = health;
        efects = new ArrayList<>();
    }

    public int getValue() {
        return curHealth;
    }

    public void refill(int amount) {
        curHealth += amount;
        if (curHealth > maxHealth)
            curHealth = maxHealth;
    }

    public void restore() {
        curHealth = maxHealth;
    }

    public void drain(int amount) {
        curHealth -= amount;
        if (curHealth > 0)
            return;

        curHealth = 0;
        if (efects == null)
            return;

        efects.forEach(ExhaustionEffect::apply);
        efects.clear();
    }

    public void exhaust() {
        while (curHealth > 0)
            drain(10000);
    }

    public void onExhaustion(ExhaustionEffect effect) {
        if (efects != null)
            efects.add(effect);
    }

    @FunctionalInterface
    public interface ExhaustionEffect {
        void apply();
    }
}
