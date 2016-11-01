package me.nbeaussart.game;

import me.nbeaussart.engine.model.Color;
import me.nbeaussart.engine.model.Cord;
import me.nbeaussart.engine.model.Direction;
import me.nbeaussart.engine.model.GameGenerator;
import me.nbeaussart.engine.model.interfaces.IState;
import me.nbeaussart.engine.view.GameScreen;
import me.nbeaussart.engine.view.MapPrinter;
import me.nbeaussart.game.action.Action;
import me.nbeaussart.game.action.Attack;
import me.nbeaussart.game.action.Move;
import me.nbeaussart.game.entity.Entity;
import me.nbeaussart.game.entity.EntityListener;
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
import java.util.Optional;
import java.util.Random;

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
    private final int NMB_MONSTER = 10;
    private int score = 0;

    public Main(){
        gameMap = new GameMap(20,20);
        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 20; y++) {
                new GameSquare(new Cord(x,y), gameMap);
            }
        }
        new GameGenerator<GameSquare>(gameMap)
                .useMazeGeneratorClean()
                .addPostProsessor(gameSquares -> {
                    player = new Player("Player",100,20,gameMap.getFromCords(new Cord(1,1)).get());

                    while (monsters.size() < NMB_MONSTER){
                        Random r = new Random();
                        GameSquare sq = gameSquares.get(r.nextInt(gameSquares.size()));
                        if (sq.getEntity() == null && (sq.getState() == IState.ROOM || sq.getState() == IState.DOOR)) {
                            monsters.add(new Monster("MONSTER", 20, 10, sq));
                        }
                    }
                }).generate();

        monsters.forEach(monster -> monster.addListener(new EntityListener() {
            @Override
            public void entityDeath(Entity entity) {
                monsters.remove(monster);
                score+=100;
                for (int i = 0; i < monsters.size(); i++) {
                    System.out.println(i+ " - " + monsters.get(i));
                }
                System.out.println("SCORE = " + score);
            }

            @Override
            public void entityLifeChanged(Entity entity) {
                System.out.printf("Entity %s just changed pv !\n", monster.getName());
            }
        }));

        keyInputManager = new KeyInputManager();


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

    private void tryToMoveTo(Direction direction){
        Optional<GameSquare> fromCords = gameMap.getFromCords(player.getGameSquare().getCord().add(direction));
        if (fromCords.isPresent()) {
            GameSquare gameSquare = fromCords.get();
            if (gameSquare.getEntity() != null){
                new Attack(player, gameSquare.getEntity()).act();
            } else {
                new Move(player, direction).act();
            }
        }
    }

    private void handlePlayerAction(){
        if (keyInputManager.isPressed(KeyEvent.VK_Z)){
            tryToMoveTo(Direction.UP);
        }
        if (keyInputManager.isPressed(KeyEvent.VK_S)){
            tryToMoveTo(Direction.DOWN);
        }
        if(keyInputManager.isPressed(KeyEvent.VK_Q)){
            tryToMoveTo(Direction.LEFT);
        }
        if(keyInputManager.isPressed(KeyEvent.VK_D)){
            tryToMoveTo(Direction.RIGHT);
        }
    }

    public static void main(String ... args){
        new Main().gameLoop();
    }
}
