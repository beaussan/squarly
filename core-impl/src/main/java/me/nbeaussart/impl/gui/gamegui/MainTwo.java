package me.nbeaussart.impl.gui.gamegui;

import me.nbeaussart.engine.model.Color;
import me.nbeaussart.engine.model.Cord;
import me.nbeaussart.engine.model.Direction;
import me.nbeaussart.engine.view.GameScreen;
import me.nbeaussart.engine.view.GameSquareClicked;
import me.nbeaussart.engine.view.MapPrinter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static me.nbeaussart.engine.view.GameScreen.createGameScreen;

/**
 * @author Nicolas Beaussart
 * @since 26/10/16
 */
public class MainTwo {

    public static final Color COLOR_BACK = new Color(255, 0, 0);
    public static final Color COLOR_BACK1 = new Color(255, 0, 255);
    public static final Color COLOR_BACK2 = new Color(255, 255, 0);
    public static final Color COLOR_PLAYER = new Color(0, 0, 0);

    public static void drawFull(GameMap gm) {
        int cpt = 0;
        for (int i = 0; i < gm.sizeX(); i++) {
            for (int i1 = 0; i1 < gm.sizeY(); i1++) {
                gm.getMapData().add(new GameSquare((cpt % 2 == 0) ? COLOR_BACK1 : COLOR_BACK2, new Cord(i, i1), gm));
                cpt++;
            }
        }
        gm.removeDuplicate();
        gm.removeOutOfBounds();
    }

    public static Cord move(GameMap gm, Cord old, Cord newCord) {
        gm.getFromCords(old).ifPresent(coloredSquare -> coloredSquare.setColor(COLOR_BACK));
        gm.getFromCords(newCord).ifPresent(coloredSquare -> coloredSquare.setColor(COLOR_PLAYER));
        return newCord;
    }


    public static void main(String... args) {
        GameMap gm = new GameMap(20, 40, 10, 30);
        MapPrinter<GameSquare> mp = new MapPrinter<>(gm);
        drawFull(gm);
        final Cord[] current = {new Cord(4, 4)};

        gm.getFromCords(current[0]).ifPresent(coloredSquare -> coloredSquare.setColor(new Color(0, 0, 0)));


        mp.addGameClicked(new GameSquareClicked<GameSquare>() {
            @Override
            public void mouseClicked(GameSquare square, MouseEvent e) {
                System.out.println("Clicked");
                System.out.println(square);
                mp.repaint();

            }

            @Override
            public void mousePressed(GameSquare square, MouseEvent e) {
                System.out.println("Pressed");
                System.out.println(square);
                //mp.repaint();
            }

            @Override
            public void mouseDragged(GameSquare square, MouseEvent e) {
                System.out.println("Dragged");
                System.out.println(square);
                //mp.repaint();
            }
        });
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
                        current[0] = move(gm, current[0], current[0].add(Direction.UP.getCords()));
                        break;
                    case 's':
                    case 'S':
                        current[0] = move(gm, current[0], current[0].add(Direction.DOWN.getCords()));
                        break;
                    case 'q':
                    case 'Q':
                        current[0] = move(gm, current[0], current[0].add(Direction.LEFT.getCords()));
                        break;
                    case 'd':
                    case 'D':
                        current[0] = move(gm, current[0], current[0].add(Direction.RIGHT.getCords()));
                        break;
                }
                System.out.println(e.getKeyChar());
                System.out.println(current[0]);
                //mp.repaint();

            }

            @Override
            public void keyReleased(KeyEvent e) {
                //System.out.println("keyReleased");

            }
        });

        GameScreen hello = createGameScreen("hello", mp);
    }
}
