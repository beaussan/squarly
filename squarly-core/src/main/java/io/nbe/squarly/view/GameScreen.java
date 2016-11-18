package io.nbe.squarly.view;

import io.nbe.squarly.view.console.JConsole;

import javax.swing.*;
import java.awt.*;

/**
 * Class for making a gameScreen for the game gui
 * @author Nicolas Beaussart
 * @since 20/10/16
 */
public class GameScreen extends JFrame {

    private GameScreen(String name, JComponent mapPrinter) {

        initUIGame(name, mapPrinter);
    }

    /**
     * Create a game screen with a mapPrinter
     * @param name the name of the application
     * @param mapPrinter the mapPrinter
     * @return the gameScreen generated
     */
    public static GameScreen createGameScreen(String name, MapPrinter mapPrinter){
        final GameScreen[] gameScreen = new GameScreen[1];
        EventQueue.invokeLater(() -> {
            gameScreen[0] = new GameScreen(name, mapPrinter);
            gameScreen[0].setVisible(true);
        });

        return gameScreen[0];
    }

    /**
     * Create a game screen with a console and char map
     * @param name the name of the application
     * @param console the console to show
     * @return the gameScreen generated
     */
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
