package io.nbe.impl.gui.gamegui.textbased;

import io.nbe.squarly.model.Color;
import io.nbe.squarly.model.interfaces.ICharacterSquare;
import io.nbe.squarly.model.interfaces.ICoordinateSquare;
import io.nbe.squarly.model.interfaces.IState;
import io.nbe.squarly.model.Cord;

/**
 * @author Nicolas Beaussart
 * @since 01/11/16
 */
public class GameSquareText implements ICoordinateSquare, ICharacterSquare {

    private final GameMap map;
    private final Cord cord;
    private IState state = IState.WALL;
    private Color foreground;
    private Color background;
    private char chardata;

    public GameSquareText(GameMap map, Cord cord, Color foreground, Color background, char chardata) {
        this.map = map;
        this.cord = cord;
        this.foreground = foreground;
        this.background = background;
        this.chardata = chardata;
    }
    public GameSquareText(GameMap map, Cord cord) {
        this.map = map;
        this.cord = cord;
        this.foreground = new Color(255,255,255);
        this.background = new Color(0,0,0);
        this.chardata = ' ';
    }

    @Override
    public char getChar() {
        return chardata;
    }

    @Override
    public Color getBackground() {
        return background;
    }

    @Override
    public Color getForeground() {
        return foreground;
    }

    @Override
    public Cord getCord() {
        return cord;
    }

    @Override
    public GameMap getGameMap() {
        return map;
    }

    @Override
    public void setUpdated() {
        map.setChanged(this);
    }

    @Override
    public IState getState() {
        return state;
    }

    @Override
    public void setState(IState state) {
        this.state = state;
        setUpdated();
    }

    public void setForeground(Color foreground) {
        this.foreground = foreground;
        setUpdated();
    }

    public void setBackground(Color background) {
        this.background = background;
        setUpdated();
    }

    public void setChardata(char chardata) {
        this.chardata = chardata;
        setUpdated();
    }
}
