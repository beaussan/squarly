package me.nbeaussart.impl.gui.gamegui.textbased;

import me.nbeaussart.engine.model.Color;
import me.nbeaussart.engine.model.Cord;
import me.nbeaussart.engine.model.Direction;
import me.nbeaussart.engine.view.GameScreen;
import me.nbeaussart.engine.view.MapCharDrowers;
import me.nbeaussart.engine.view.console.JConsole;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author Nicolas Beaussart
 * @since 06/11/16
 */
public class MainThree {

    private final static Color COLOR_BACK = new Color(30,30,30);
    private final static Color COLOR_PERSO = new Color(30,200,30);

    public static void main(String... args) {
        GameMap gm;
        final int size = 30;
        gm = new GameMap(size, size);

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                gm.add(new GameSquareText(gm, new Cord(x, y)));
            }
        }
        for (int x = 0; x < size; x++) {
            int finalX = x%10;
            Color currColor = new Color((255/size)*x,(255/size)*x,(255/size)*x);
            for (int y = 0; y < size; y++) {
                gm.getFromCords(new Cord(x, y)).ifPresent(gameSquareText -> {
                    gameSquareText.setChardata(Character.forDigit(finalX, 10));
                    gameSquareText.setBackground(currColor);
                });
            }
        }

        gm.getFromCords(new Cord(1, 0)).ifPresent(gameSquareText -> gameSquareText.setBackground(new Color(255, 0, 0)));
        gm.getFromCords(new Cord(1, 0)).ifPresent(gameSquareText -> gameSquareText.setForeground(new Color(0, 0, 0)));
        gm.getFromCords(new Cord(0, 1)).ifPresent(gameSquareText -> gameSquareText.setBackground(new Color(0, 255, 0)));
        gm.getFromCords(new Cord(0, 1)).ifPresent(gameSquareText -> gameSquareText.setForeground(new Color(0, 0, 0)));


        JConsole console = new JConsole(size, size);
        console.setCursorBlink(false);
        console.setFocusable(true);
        console.requestFocus();
        MapCharDrowers<GameSquareText> mapCharDrowers = new MapCharDrowers<>(gm, console);
        GameScreen.createGameScreen("Main", console);
        //Frames.display(console);
        //console.setCursorPos(0,0);
        //console.writeln("Hello world ! :D");

        final Cord[] cord = {new Cord(1, 1)};
        gm.getFromCords(cord[0]).ifPresent(gameSquareText -> {
            gameSquareText.setBackground(COLOR_PERSO);
            gameSquareText.setForeground(COLOR_PERSO);
            gameSquareText.setChardata('@');
        });

        console.addKeyListener(new KeyAdapter() {
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
                        cord[0] = move(gm, cord[0], Direction.UP);
                        break;
                    case 's':
                    case 'S':
                        cord[0] = move(gm, cord[0], Direction.DOWN);
                        break;
                    case 'q':
                    case 'Q':
                        cord[0] = move(gm, cord[0], Direction.LEFT);
                        break;
                    case 'd':
                    case 'D':
                        cord[0] = move(gm, cord[0], Direction.RIGHT);
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


    public static Cord move(GameMap map, Cord cur, Direction direction){
        map.getFromCords(cur).ifPresent(gameSquareText -> {
            gameSquareText.setBackground(COLOR_BACK);
            gameSquareText.setForeground(COLOR_BACK);
            gameSquareText.setChardata(' ');
        });
        Cord add = cur.add(direction);
        map.getFromCords(add).ifPresent(gameSquareText -> {
            gameSquareText.setBackground(COLOR_PERSO);
            gameSquareText.setForeground(COLOR_PERSO);
            gameSquareText.setChardata('@');
        });
        return add;
    }
}
