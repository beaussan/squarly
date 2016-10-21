package me.nbeaussart.impl;

import me.nbeaussart.engine.model.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static me.nbeaussart.engine.model.GameScreen.createGameScreen;

/**
 * @author Nicolas Beaussart
 * @since 20/10/16
 */
public class MainOne {

    public static void drawShape(GameMapImpl gm, int sizeXY){
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
                gm.getMapData().add(new GameSquareImpl(c, new Coord(i, j), gm));
                gm.getMapData().add(new GameSquareImpl(c, new Coord(j, i), gm));
                gm.getMapData().add(new GameSquareImpl(c, new Coord(sizeXY-i, sizeXY-j), gm));
                gm.getMapData().add(new GameSquareImpl(c, new Coord(sizeXY-j, sizeXY-i), gm));
            }
        }
        gm.deleteDuplicate();
    }

    public static void main(String... args){
        int sizeXY = 40;
        GameMapImpl gm = new GameMapImpl(sizeXY, sizeXY, 20, 20);
        MapPrinter mp = new MapPrinter(gm);
        drawShape(gm, sizeXY);

        mp.addGameClicked(new GameSquareClicked() {
            @Override
            public void mouseClicked(GameSquare square, MouseEvent e) {
                System.out.println("Clicked");
                System.out.println(square);
                ((GameSquareImpl) square).setColor(new Color(0,0,0));
                mp.repaint();

            }

            @Override
            public void mousePressed(GameSquare square, MouseEvent e) {
                System.out.println("Pressed");
                System.out.println(square);
                ((GameSquareImpl) square).setColor(new Color(0,0,0));
                mp.repaint();
            }

            @Override
            public void mouseDragged(GameSquare square, MouseEvent e) {
                System.out.println("Dragged");
                System.out.println(square);
                ((GameSquareImpl) square).setColor(new Color(0,0,0));
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
