package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.backends.lwjgl.LwjglBackend;
import sk.tuke.kpi.oop.game.scenarios.EscapeRoom;

public class Main {
    public static void main(String[] args) {
        var windowSetup = new WindowSetup("Project Ellen", 800, 600);

        Game game = new GameApplication(windowSetup, new LwjglBackend());
        Scene scene = new World("world", "maps/escape-room.tmx", new EscapeRoom.Factory());
        game.addScene(scene);
        game.getInput().onKeyPressed(Input.Key.ESCAPE, game::stop);

        scene.addListener(new EscapeRoom());
        game.start();
    }
}
