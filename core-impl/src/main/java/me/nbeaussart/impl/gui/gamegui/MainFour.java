package me.nbeaussart.impl.gui.gamegui;

import me.nbeaussart.engine.model.Color;
import me.nbeaussart.engine.model.Cord;
import me.nbeaussart.engine.model.GameGenerator;
import me.nbeaussart.engine.view.GameScreen;
import me.nbeaussart.engine.view.GameSquareClicked;
import me.nbeaussart.engine.view.MapPrinter;

import java.awt.event.MouseEvent;

/**
 * @author Nicolas Beaussart
 * @since 27/10/16
 */
public class MainFour {
    private final static Color COLOR_WALL=new Color(0,0,0);
    private final static Color COLOR_ROOM=new Color(255,255,255);
    private final static Color COLOR_DOOR=new Color(255,0,0);
    public static void generate(GameMap gm){
        gm.getMapData().clear();
        for (int y = 0; y < gm.sizeY(); y++) {
            for (int x = 0; x < gm.sizeX(); x++) {
                gm.getMapData().add(new GameSquare(COLOR_WALL, new Cord(x,y), gm));
            }
        }
        new GameGenerator<>(gm).useMazeGeneratorClean().generate();
        gm.setChanged(null);
        gm.setChanged(null);
    }


    public static void main(String... args){
        GameMap gm = new GameMap(40, 40, 20, 20);
        gm.addUpdatesHandlers(gameSquare3 -> {
            gm.getMapData().forEach(gameSquare -> {
                switch (gameSquare.getState()){
                    case DOOR:
                        gameSquare.setColorWithoutUpdate(COLOR_DOOR);
                        break;
                    case ROOM:
                        gameSquare.setColorWithoutUpdate(COLOR_ROOM);
                        break;
                    case WALL:
                        gameSquare.setColorWithoutUpdate(COLOR_WALL);
                        break;
                }
            });
        });
        generate(gm);
        MapPrinter<GameSquare> mapPrinter = new MapPrinter<>(gm);
        GameScreen.createGameScreen("Hello world", mapPrinter);
        mapPrinter.addGameClicked(new GameSquareClicked<GameSquare>() {
            @Override
            public void mouseClicked(GameSquare square, MouseEvent e) {
                //System.out.println(square);
                generate(gm);
            }
        });
    }
}
