package me.nbeaussart.impl.gui.gamegui;

import me.nbeaussart.engine.model.*;
import me.nbeaussart.engine.view.GameScreen;
import me.nbeaussart.engine.view.GameSquareClicked;
import me.nbeaussart.engine.view.MapPrinter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static me.nbeaussart.engine.view.GameScreen.createGameScreen;

/**
 * @author Nicolas Beaussart
 * @since 20/10/16
 */
public class MainOne {

    public static void drawShape(GameMap gm, int sizeXY) {
        gm.getMapData().clear();

        for (int i = 0; i <= sizeXY / 2; i++) {

            for (int j = i; j <= sizeXY-i; j++) {
                Color c;
                switch (i%3){
                    case 0:
                        c = new Color((255/ (sizeXY-i))*j, 0, 0);
                        break;
                    case 1:
                        c = new Color(0, (255/ (sizeXY-i))*j, 0);
                        break;
                    default:
                        c = new Color(0, 0, (255/ (sizeXY-i))*j);
                        break;
                }
                gm.getMapData().add(new GameSquare(c, new Coord(i, j), gm));
                gm.getMapData().add(new GameSquare(c, new Coord(j, i), gm));
                gm.getMapData().add(new GameSquare(c, new Coord(sizeXY - i, sizeXY - j), gm));
                gm.getMapData().add(new GameSquare(c, new Coord(sizeXY - j, sizeXY - i), gm));
            }
        }

        gm.deleteDuplicate();

    }

    public static void main(String... args){
        int sizeXY = 20;
        GameMap gm = new GameMap(sizeXY, sizeXY, 25, 25);
        MapPrinter<GameSquare> mp = new MapPrinter<>(gm);
        drawShape(gm, sizeXY);

        System.out.println(gm.getFromCoords(new Coord(sizeXY, sizeXY)));
        System.out.println(gm.getFromCoords(new Coord(sizeXY - 1, sizeXY - 1)));
        System.out.println(gm.getFromCoords(new Coord(sizeXY - 2, sizeXY - 2)));

        mp.addGameClicked(new GameSquareClicked<GameSquare>() {
            @Override
            public void mouseClicked(GameSquare square, MouseEvent e) {
                System.out.println("Clicked");
                System.out.println(square);
                square.setColor(new Color(0, 0, 0));
                mp.repaint();

            }

            @Override
            public void mousePressed(GameSquare square, MouseEvent e) {
                System.out.println("Pressed");
                System.out.println(square);
                square.setColor(new Color(0, 0, 0));
                mp.repaint();
            }

            @Override
            public void mouseDragged(GameSquare square, MouseEvent e) {
                System.out.println("Dragged");
                System.out.println(square);
                square.setColor(new Color(0, 0, 0));
                mp.repaint();
            }
        });
        mp.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                System.out.println("typed");
                System.out.println(e);
                drawShape(gm, sizeXY);
                mp.repaint();
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        GameScreen hello = createGameScreen("hello", mp);
    }
}
