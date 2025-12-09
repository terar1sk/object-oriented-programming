package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

import java.util.concurrent.ThreadLocalRandom;

public class DefectiveLight extends Light implements Repairable{

    private boolean repaired;
    private Disposable dispose;
    public DefectiveLight() {
        super();
        repaired = false;
    }
    private void defectLight() {
        int n = ThreadLocalRandom.current().nextInt(0, 20 + 1);
        if (n == 1 && !repaired) {
            super.toggle();
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        dispose = new Loop<>(new Invoke<>(this::defectLight)).scheduleFor(this);
    }

    @Override
    public boolean repair() {
        if (repaired)
            return false;

        repaired = true;
        dispose.dispose();

        new ActionSequence<>(
            new Wait<>(10),
            new Invoke<>(this::breakLight)
        ).scheduleFor(this);

        return true;
    }

    public void breakLight() {
        this.dispose = new Loop<>(new Invoke<>(this::defectLight)).scheduleFor(this);
        repaired = false;
    }

}
