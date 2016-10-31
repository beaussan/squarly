package me.nbeaussart.game;

import me.nbeaussart.engine.model.Color;
import me.nbeaussart.engine.model.Cord;
import me.nbeaussart.engine.model.Direction;
import me.nbeaussart.engine.model.GameGenerator;
import me.nbeaussart.engine.view.GameScreen;
import me.nbeaussart.engine.view.MapPrinter;
import me.nbeaussart.game.action.Move;
import me.nbeaussart.game.entity.Entity;
import me.nbeaussart.game.entity.Player;
import me.nbeaussart.game.map.GameMap;
import me.nbeaussart.game.map.GameSquare;

import javax.print.attribute.standard.PrinterLocation;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by beaussan on 31/10/16.
 */
public class Main {

    private final MapPrinter<GameSquare> mapPrinter;
    private final GameScreen application;
    private GameMap gameMap;
    private Entity player;

    public Main(){
        gameMap = new GameMap(20,20);
        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 20; y++) {
                new GameSquare(new Cord(x,y), gameMap);
            }
        }
        new GameGenerator<GameSquare>(gameMap).useMazeGeneratorClean().generate();

        player = new Player("Player",100,20,gameMap.getFromCords(new Cord(1,1)).get());
        this.mapPrinter = new MapPrinter<>(gameMap);
        application = GameScreen.createGameScreen("APPLICATION", mapPrinter);
        setupLister();
    }

    private void setupLister(){
        mapPrinter.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

                switch (e.getKeyChar()) {
                    case 'z':
                    case 'Z':
                        new Move(player, Direction.UP).act();
                        break;
                    case 's':
                    case 'S':
                        new Move(player, Direction.DOWN).act();
                        break;
                    case 'q':
                    case 'Q':
                        new Move(player, Direction.LEFT).act();
                        break;
                    case 'd':
                    case 'D':
                        new Move(player, Direction.RIGHT).act();
                        break;
                }
                System.out.println(player);
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }


    public static void main(String ... args){
        new Main();
    }
}
