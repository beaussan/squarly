package me.nbeaussart.engine.model;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @author Nicolas Beaussart
 * @since 20/10/16
 */
public class GameScreen extends JFrame {

    public GameScreen(String name, MapPrinter mapPrinter) {

        initUIGame(name, mapPrinter);
    }

    private void initUIGame(String name, MapPrinter mapPrinter) {

        add(mapPrinter);

        setSize(mapPrinter.getSizeWidth(), mapPrinter.getSizeHeight());
        setTitle(name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

    }

    public static GameScreen createGameScreen(String name, MapPrinter mapPrinter){
        final GameScreen[] gameScreen = new GameScreen[1];
        EventQueue.invokeLater(() -> {
            gameScreen[0] = new GameScreen(name, mapPrinter);
            gameScreen[0].setVisible(true);
        });

        return gameScreen[0];
    }





}
