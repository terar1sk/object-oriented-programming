package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.ActorContainer;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Iterator;

public class Backpack implements ActorContainer<Collectible>{
    private final String name;
    private final int capacity;
    private final List<Collectible> items;

    public Backpack(String name, int capacity){
        this.name = name;
        this.capacity = capacity;
        this.items = new ArrayList<>();
    }

    @Override
    public @NotNull List<Collectible> getContent(){
        return List.copyOf(items);
    }

    @Override
    public @NotNull String getName(){
        return name;
    }

    @Override
    public int getCapacity(){
        return capacity;
    }

    @Override
    public int getSize(){
        return items.size();
    }

    @Override
    public void add(@NotNull Collectible actor){
        if(items.size() < getCapacity()){
            items.add(actor);
        }
        else{
            throw new IllegalStateException(getName()+" is full! Cannot add more items.");
        }
    }

    @Override
    public void remove(@NotNull Collectible actor){
        items.remove(actor);
    }

    @NotNull
    @Override
    public Iterator<Collectible> iterator(){
        return items.iterator();
    }

    @Nullable
    @Override
    public Collectible peek(){
        if(items.isEmpty()){
            return null;
        }
        return items.get(items.size() - 1);
    }

    @Override
    public void shift(){
        Collections.rotate(items, 1);
    }
}
