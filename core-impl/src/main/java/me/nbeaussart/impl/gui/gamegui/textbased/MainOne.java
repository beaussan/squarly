package me.nbeaussart.impl.gui.gamegui.textbased;

import me.nbeaussart.engine.model.Color;
import me.nbeaussart.engine.model.Cord;
import me.nbeaussart.engine.util.Frames;
import me.nbeaussart.engine.view.MapCharDrowers;
import me.nbeaussart.engine.view.console.JConsole;
import me.nbeaussart.impl.gui.gamegui.colorbased.*;

/**
 * @author Nicolas Beaussart
 * @since 01/11/16
 */
public class MainOne {

    public static void main(String... args){
        GameMap gm;
        gm = new GameMap(4,4);

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                gm.add(new GameSquareText(gm, new Cord(x,y)));
            }
        }

        for (int x = 0; x < 4; x++) {
            int finalX = x;
            Color currColor = new Color((255/4)*x,(255/4)*x,(255/4)*x);
            for (int y = 0; y < 4; y++) {
                gm.getFromCords(new Cord(x, y)).ifPresent(gameSquareText -> {
                    gameSquareText.setChardata(Character.forDigit(finalX, 10));
                    gameSquareText.setBackground(currColor);
                });
            }
        }
        /*
        gm.getFromCords(new Cord(0,0)).ifPresent(gameSquareText -> gameSquareText.setChardata('0'));
        gm.getFromCords(new Cord(1,0)).ifPresent(gameSquareText -> gameSquareText.setChardata('1'));
        gm.getFromCords(new Cord(0,1)).ifPresent(gameSquareText -> gameSquareText.setChardata('1'));
        gm.getFromCords(new Cord(1,1)).ifPresent(gameSquareText -> gameSquareText.setChardata('2'));
        gm.getFromCords(new Cord(2,0)).ifPresent(gameSquareText -> gameSquareText.setChardata('2'));
        gm.getFromCords(new Cord(0,2)).ifPresent(gameSquareText -> gameSquareText.setChardata('2'));
        gm.getFromCords(new Cord(3,0)).ifPresent(gameSquareText -> gameSquareText.setChardata('3'));
        gm.getFromCords(new Cord(0,3)).ifPresent(gameSquareText -> gameSquareText.setChardata('3'));
        gm.getFromCords(new Cord(4,0)).ifPresent(gameSquareText -> gameSquareText.setChardata('4'));
        gm.getFromCords(new Cord(0,4)).ifPresent(gameSquareText -> gameSquareText.setChardata('4'));

        */
        JConsole console = new JConsole(4,4);
        MapCharDrowers<GameSquareText> mapCharDrowers = new MapCharDrowers<>(gm, console);
        Frames.display(console);

        gm.getFromCords(new Cord(1,0)).ifPresent(gameSquareText -> gameSquareText.setBackground(new Color(255,0,0)));
        gm.getFromCords(new Cord(1,0)).ifPresent(gameSquareText -> gameSquareText.setForeground(new Color(0,0,0)));
        gm.getFromCords(new Cord(0,1)).ifPresent(gameSquareText -> gameSquareText.setBackground(new Color(0,255,0)));
        gm.getFromCords(new Cord(0,1)).ifPresent(gameSquareText -> gameSquareText.setForeground(new Color(0,0,0)));

        System.out.println("Done");
    }
}
