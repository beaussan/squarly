package io.nbe.impl.gui.gamegui.fovtest;

import io.nbe.squarly.model.*;
import io.nbe.squarly.view.GameScreen;
import io.nbe.squarly.view.MapPrinter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author Nicolas Beaussart
 * @since 13/11/16
 */
public class MainOne {
    private final static Color COLOR_PERSO = new Color(30,200,30);

    public static void main(String ... args){

        GameMap gm;
        FOV<GameSquare> fov = new FOV<>();
        gm = new GameMap(60, 60, 20, 20);

        gm.setChanged(null);
        MapPrinter<GameSquare> mp = new MapPrinter<>(gm);
        GameScreen hello = GameScreen.createGameScreen("hello", mp);
        new GameGenerator<GameSquare>(gm).useDungeonGenerator().generate();

        final Cord[] cord = {null};

        for (GameSquare gameSquare : gm.getMapData().values()) {
            if (!gameSquare.isOpaque()){
                gameSquare.setColor(COLOR_PERSO);

                gm.getMapData().values().forEach(gameSquareZ -> gameSquareZ.setViewed(false));

                fov.generateFOV(gameSquare.getCord(), gm).forEach(gameSquareZ -> gameSquareZ.setViewed(true));
                cord[0] = gameSquare.getCord();
                break;

            }
        }

        mp.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                System.out.println("typed");
                //System.out.println(e);

                //mp.repaint();
            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("keyPressed");
                switch (e.getKeyChar()) {
                    case 'z':
                    case 'Z':
                        cord[0] = move(gm, cord[0], Direction.UP, fov);
                        break;
                    case 's':
                    case 'S':
                        cord[0] = move(gm, cord[0], Direction.DOWN, fov);
                        break;
                    case 'q':
                    case 'Q':
                        cord[0] = move(gm, cord[0], Direction.LEFT, fov);
                        break;
                    case 'd':
                    case 'D':
                        cord[0] = move(gm, cord[0], Direction.RIGHT, fov);
                        break;
                }
                System.out.println(e.getKeyChar());
                System.out.println(cord[0]);
                //mp.repaint();

            }

            @Override
            public void keyReleased(KeyEvent e) {
                //System.out.println("keyReleased");

            }
        });

        System.out.println("Done");
    }


    public static Cord move(GameMap map, Cord cur, Direction direction, FOV<GameSquare> fov){
        map.getFromCords(cur).ifPresent(gameSquareText -> {
            gameSquareText.setColor(null);
        });
        Cord add = cur.add(direction);
        map.getFromCords(add).ifPresent(gameSquareText -> {
            gameSquareText.setColor(COLOR_PERSO);
        });
        map.getMapData().values().forEach(gameSquare -> gameSquare.setViewed(false));

        fov.generateFOV(add, map).forEach(gameSquare -> gameSquare.setViewed(true));

        return add;
    }
}
