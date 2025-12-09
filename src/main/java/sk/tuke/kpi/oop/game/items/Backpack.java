package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.ActorContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Backpack implements ActorContainer<Collectible> {

    private String name;
    private List<Collectible> contents;
    private int capacity;

    public Backpack(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        contents = new ArrayList<>();
    }

    @Override
    public @NotNull List<Collectible> getContent() {
        List<Collectible> copy = new ArrayList<Collectible>();
        copy.addAll(contents);
        return copy;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public int getSize() {
        return contents.size();
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public void add(@NotNull Collectible actor) {
        if (contents != null && contents.size() >= capacity)
            throw new IllegalStateException(String.format("<%s> is full", name));

        contents.add(actor);
    }

    @Override
    public void remove(@NotNull Collectible actor) {
        if (contents != null)
            contents.remove(actor);
    }

    @Override
    public @Nullable Collectible peek() {
        if (contents != null && !contents.isEmpty())
            return contents.get(contents.size() - 1);
        else return null;
    }

    @Override
    public void shift() {
        if (contents != null)
            Collections.rotate(contents, 1);
    }

    @NotNull
    @Override
    public Iterator<Collectible> iterator() {
        return contents.iterator();
    }
}
