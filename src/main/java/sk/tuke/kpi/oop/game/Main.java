package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.backends.lwjgl.LwjglBackend;
import sk.tuke.kpi.oop.game.scenarios.FirstSteps;
import sk.tuke.kpi.gamelib.GameApplication;

public class Main {
    public static void main(String[] args){
        WindowSetup windowSetup = new WindowSetup("Project Ellen", 800, 600);
        Game game = new GameApplication(windowSetup, new LwjglBackend());
        Scene scene = new World("world");

        FirstSteps firstSteps = new FirstSteps();

        game.addScene(scene);
        scene.addListener(firstSteps);
        game.getInput().onKeyPressed(Input.Key.ESCAPE, game::stop);
        game.start();
    }
}
