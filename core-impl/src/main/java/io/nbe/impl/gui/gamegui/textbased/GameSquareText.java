package me.nbeaussart.impl.gui.gamegui.textbased;

import me.nbeaussart.engine.model.Color;
import me.nbeaussart.engine.model.Cord;
import me.nbeaussart.engine.model.interfaces.ICaracterSquare;
import me.nbeaussart.engine.model.interfaces.ICoordinateSquare;
import me.nbeaussart.engine.model.interfaces.IState;

/**
 * @author Nicolas Beaussart
 * @since 01/11/16
 */
public class GameSquareText implements ICoordinateSquare, ICaracterSquare {

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
