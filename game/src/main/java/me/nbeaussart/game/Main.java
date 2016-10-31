package me.nbeaussart.game;

import me.nbeaussart.engine.model.Color;
import me.nbeaussart.engine.model.Cord;
import me.nbeaussart.engine.model.Direction;
import me.nbeaussart.engine.model.GameGenerator;
import me.nbeaussart.engine.model.interfaces.IState;
import me.nbeaussart.engine.view.GameScreen;
import me.nbeaussart.engine.view.MapPrinter;
import me.nbeaussart.game.action.Action;
import me.nbeaussart.game.action.Move;
import me.nbeaussart.game.entity.Entity;
import me.nbeaussart.game.entity.Monster;
import me.nbeaussart.game.entity.Player;
import me.nbeaussart.game.ia.AbstractIA;
import me.nbeaussart.game.ia.DummyIA;
import me.nbeaussart.game.map.GameMap;
import me.nbeaussart.game.map.GameSquare;
import me.nbeaussart.game.utils.KeyInputManager;

import javax.print.attribute.standard.PrinterLocation;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by beaussan on 31/10/16.
 */
public class Main {

    private final MapPrinter<GameSquare> mapPrinter;
    private final GameScreen application;
    private final KeyInputManager keyInputManager;
    private GameMap gameMap;
    private Entity player;
    private List<Monster> monsters = new ArrayList<>();
    private AbstractIA ia = new DummyIA();
    private long lastFpsTime;
    private int fps;

    public Main(){
        gameMap = new GameMap(20,20);
        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 20; y++) {
                new GameSquare(new Cord(x,y), gameMap);
            }
        }
        new GameGenerator<GameSquare>(gameMap).useMazeGeneratorClean().generate();

        player = new Player("Player",100,20,gameMap.getFromCords(new Cord(1,1)).get());
        keyInputManager = new KeyInputManager();

        for (GameSquare gameSquare : gameMap.getMapData()) {
            if (gameSquare.getState() == IState.ROOM){
                monsters.add(new Monster("MONSTER", 10,20, gameSquare));
                if (monsters.size() >= 3){
                    break;
                }

            }
        }


        this.mapPrinter = new MapPrinter<>(gameMap);
        mapPrinter.addKeyListener(keyInputManager);
        application = GameScreen.createGameScreen("APPLICATION", mapPrinter);


    }

    public void gameLoop(){
        final int TARGET_FPS = 10;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
        long lastLoopTime = System.nanoTime();
        while(player.isALive()){
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            lastFpsTime += updateLength;
            fps++;
            if(lastFpsTime >= 1000000000){
                System.out.println("(FPS: "+fps+")");
                lastFpsTime = 0;
                fps = 0;
            }
            handlePlayerAction();
            monsters.forEach(monster -> ia.getAction(monster).ifPresent(Action::act));
            try {
                Thread.sleep( (lastLoopTime - System.nanoTime() + OPTIMAL_TIME)/1000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void handlePlayerAction(){
        if (keyInputManager.isPressed(KeyEvent.VK_Z)){
            new Move(player, Direction.UP).act();
        }
        if (keyInputManager.isPressed(KeyEvent.VK_S)){
            new Move(player, Direction.DOWN).act();
        }
        if(keyInputManager.isPressed(KeyEvent.VK_Q)){
            new Move(player, Direction.LEFT).act();
        }
        if(keyInputManager.isPressed(KeyEvent.VK_D)){
            new Move(player, Direction.RIGHT).act();
        }
    }

    public static void main(String ... args){
        new Main().gameLoop();
    }
}
