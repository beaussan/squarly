package me.nbeaussart.engine.view;

import me.nbeaussart.engine.view.console.JConsole;

import javax.swing.*;
import java.awt.*;

/**
 * @author Nicolas Beaussart
 * @since 20/10/16
 */
public class GameScreen extends JFrame {

    private GameScreen(String name, JComponent mapPrinter) {

        initUIGame(name, mapPrinter);
    }

    public static GameScreen createGameScreen(String name, MapPrinter mapPrinter){
        final GameScreen[] gameScreen = new GameScreen[1];
        EventQueue.invokeLater(() -> {
            gameScreen[0] = new GameScreen(name, mapPrinter);
            gameScreen[0].setVisible(true);
        });

        return gameScreen[0];
    }
    public static GameScreen createGameScreen(String name, JConsole console){
        final GameScreen[] gameScreen = new GameScreen[1];
        EventQueue.invokeLater(() -> {
            gameScreen[0] = new GameScreen(name, console);
            gameScreen[0].setVisible(true);
        });

        return gameScreen[0];
    }

    private void initUIGame(String name, JComponent mapPrinter) {

        add(mapPrinter);
        setTitle(name);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
    }





}
