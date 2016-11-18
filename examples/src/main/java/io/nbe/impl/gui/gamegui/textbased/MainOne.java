package io.nbe.impl.gui.gamegui.textbased;

import io.nbe.squarly.model.Color;
import io.nbe.squarly.view.console.JConsole;
import io.nbe.squarly.model.Cord;
import io.nbe.squarly.util.Frames;
import io.nbe.squarly.view.MapCharDrowers;

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
                gm.add(new GameSquareText(gm, Cord.get(x,y)));
            }
        }

        for (int x = 0; x < 4; x++) {
            int finalX = x;
            Color currColor = new Color((255/4)*x,(255/4)*x,(255/4)*x);
            for (int y = 0; y < 4; y++) {
                gm.getFromCords(Cord.get(x, y)).ifPresent(gameSquareText -> {
                    gameSquareText.setChardata(Character.forDigit(finalX, 10));
                    gameSquareText.setBackground(currColor);
                });
            }
        }
        /*
        gm.getFromCords(Cord.get(0,0)).ifPresent(gameSquareText -> gameSquareText.setChardata('0'));
        gm.getFromCords(Cord.get(1,0)).ifPresent(gameSquareText -> gameSquareText.setChardata('1'));
        gm.getFromCords(Cord.get(0,1)).ifPresent(gameSquareText -> gameSquareText.setChardata('1'));
        gm.getFromCords(Cord.get(1,1)).ifPresent(gameSquareText -> gameSquareText.setChardata('2'));
        gm.getFromCords(Cord.get(2,0)).ifPresent(gameSquareText -> gameSquareText.setChardata('2'));
        gm.getFromCords(Cord.get(0,2)).ifPresent(gameSquareText -> gameSquareText.setChardata('2'));
        gm.getFromCords(Cord.get(3,0)).ifPresent(gameSquareText -> gameSquareText.setChardata('3'));
        gm.getFromCords(Cord.get(0,3)).ifPresent(gameSquareText -> gameSquareText.setChardata('3'));
        gm.getFromCords(Cord.get(4,0)).ifPresent(gameSquareText -> gameSquareText.setChardata('4'));
        gm.getFromCords(Cord.get(0,4)).ifPresent(gameSquareText -> gameSquareText.setChardata('4'));

        */
        JConsole console = new JConsole(4,4);
        MapCharDrowers<GameSquareText> mapCharDrowers = new MapCharDrowers<>(gm, console);
        Frames.display(console);

        gm.getFromCords(Cord.get(1,0)).ifPresent(gameSquareText -> gameSquareText.setBackground(new Color(255,0,0)));
        gm.getFromCords(Cord.get(1,0)).ifPresent(gameSquareText -> gameSquareText.setForeground(new Color(0,0,0)));
        gm.getFromCords(Cord.get(0,1)).ifPresent(gameSquareText -> gameSquareText.setBackground(new Color(0,255,0)));
        gm.getFromCords(Cord.get(0,1)).ifPresent(gameSquareText -> gameSquareText.setForeground(new Color(0,0,0)));

        System.out.println("Done");
    }
}
