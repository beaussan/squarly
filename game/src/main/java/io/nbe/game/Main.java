package io.nbe.game;

import io.nbe.squarly.model.Direction;
import io.nbe.squarly.view.console.JConsole;
import io.nbe.game.action.Action;
import io.nbe.game.action.Attack;
import io.nbe.game.action.Move;
import io.nbe.game.entity.*;
import io.nbe.game.ia.DummyIA;
import io.nbe.game.map.GameMap;
import io.nbe.game.utils.KeyInputManager;
import io.nbe.squarly.model.Cord;
import io.nbe.squarly.model.GameGenerator;
import io.nbe.squarly.model.interfaces.IState;
import io.nbe.squarly.view.GameScreen;
import io.nbe.squarly.view.MapCharDrowers;
import io.nbe.game.ia.AbstractIA;
import io.nbe.game.map.GameSquare;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Created by beaussan on 31/10/16.
 */
public class Main {

    private final MapCharDrowers<GameSquare> mapPrinter;
    private final JConsole jConsole;

    private final KeyInputManager keyInputManager;
    private final GameScreen application;
    private GameMap gameMap;
    private Player player;
    private List<Monster> monsters = new ArrayList<>();
    private List<Monster> monstersEasy = new ArrayList<>();
    private List<Monster> monstersMedium = new ArrayList<>();
    private List<Monster> monstersHard = new ArrayList<>();
    private AbstractIA ia;
    private long lastFpsTime;
    private int fps;
    private final int NMB_MONSTER_EASY = 10;
    private final int NMB_MONSTER_MEDIUM = 5;
    private final int NMB_MONSTER_HARD = 2;
    private final int SIZEMAP = 80;
    private int score = 0;

    public Main(){
        gameMap = new GameMap(SIZEMAP,SIZEMAP);
        generateMap();

        keyInputManager = new KeyInputManager();

        jConsole = new JConsole(SIZEMAP+2, SIZEMAP+2);

        jConsole.setCursorBlink(false);
        jConsole.setFocusable(true);
        jConsole.requestFocus();
        this.mapPrinter = new MapCharDrowers<>(gameMap, jConsole);
        //Frames.display(console);
        //jConsole.setCursorPos(0,0);
        //jConsole.writeln("Hello world ! :D");


        //this.mapPrinter = new MapPrinter<>(gameMap);
        jConsole.addKeyListener(keyInputManager);
        application = GameScreen.createGameScreen("Main", jConsole);
        jConsole.setCursorPos(0, SIZEMAP+1);
        jConsole.write("SCORE = " + 0);

        //gameMap.addUpdatesHandlers(System.out::println);
        //application = GameScreen.createGameScreen("APPLICATION", jConsole);


    }

    private void generateMap(){

        for (int x = 0; x < SIZEMAP; x++) {
            for (int y = 0; y < SIZEMAP; y++) {
                new GameSquare(Cord.get(x, y), gameMap);
            }
        }
        new GameGenerator<GameSquare>(gameMap)
                .useDungeonGenerator()
                .addPostProsessor(gameSquares -> {
                    while(player == null){
                        Random r = new Random();
                        GameSquare gs = gameSquares.get(r.nextInt(gameSquares.size()));
                        if (gs.getEntity() == null && (gs.getState() == IState.ROOM || gs.getState() == IState.DOOR)) {
                            player = new Player("Player", 100, 20, gs);
                        }
                    }

                    while (monstersEasy.size() < NMB_MONSTER_EASY){
                        Random r = new Random();
                        GameSquare sq = gameSquares.get(r.nextInt(gameSquares.size()));
                        if (sq.getEntity() == null && (sq.getState() == IState.ROOM || sq.getState() == IState.DOOR)) {
                            monstersEasy.add(new EasyMonster(sq));
                        }
                    }
                    while (monstersMedium.size() < NMB_MONSTER_MEDIUM){
                        Random r = new Random();
                        GameSquare sq = gameSquares.get(r.nextInt(gameSquares.size()));
                        if (sq.getEntity() == null && (sq.getState() == IState.ROOM || sq.getState() == IState.DOOR)) {
                            monstersMedium.add(new NormalMonster(sq));
                        }
                    }
                    while (monstersHard.size() < NMB_MONSTER_HARD){
                        Random r = new Random();
                        GameSquare sq = gameSquares.get(r.nextInt(gameSquares.size()));
                        if (sq.getEntity() == null && (sq.getState() == IState.ROOM || sq.getState() == IState.DOOR)) {
                            monstersHard.add(new HardMonster(sq));
                        }
                    }
                    monsters.addAll(monstersEasy);
                    monsters.addAll(monstersHard);
                    monsters.addAll(monstersMedium);
                }).generate();
        ia = new DummyIA(player);
        monsters.forEach(monster -> monster.addListener(new EntityListener() {
            @Override
            public void entityDeath(Entity entity) {
                monsters.remove(monster);
                score+=monster.getPointWhenDeath();
                for (int i = 0; i < monsters.size(); i++) {
                    System.out.println(i+ " - " + monsters.get(i));
                }
                System.out.println("SCORE = " + score);
                jConsole.setCursorPos(0, SIZEMAP+1);
                jConsole.write("SCORE = " + score);
            }

            @Override
            public void entityLifeChanged(Entity entity) {
                System.out.printf("Entity %s just changed pv !\n", monster.getName());
            }
        }));
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void tryToMoveTo(Direction direction){
        //System.out.println("hey");
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
