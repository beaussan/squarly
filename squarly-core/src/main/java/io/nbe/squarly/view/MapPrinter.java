package io.nbe.squarly.view;

import io.nbe.squarly.model.interfaces.IColoredSquare;
import io.nbe.squarly.model.interfaces.ICoordinateSquare;
import io.nbe.squarly.model.Cord;
import io.nbe.squarly.model.interfaces.IGameMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Optional;

import static com.google.common.base.Preconditions.*;

/**
 * A map writer for drawing a map with simple colored squares
 * @author Nicolas Beaussart
 * @since 20/10/16
 */
public class MapPrinter<T extends IColoredSquare & ICoordinateSquare> extends JPanel {
    private transient IGameMap<T> gameMap;

    public MapPrinter(IGameMap<T> gameMap) {
        this.gameMap = checkNotNull(gameMap, "Game map should not be null.");
        gameMap.addUpdatesHandlers(t -> repaint());
        setPreferredSize(new Dimension(getSizeWidth(), getSizeHeight()));


        setFocusable(true);
        this.requestFocusInWindow();
    }

    public IGameMap getGameMap() {
        return gameMap;
    }

    public void setGameMap(IGameMap<T> gameMap) {
        this.gameMap = gameMap;
    }

    /**
     * Adding a game clicked event to the map printer
     * @param gameSquareClicked the event listener to be added
     */
    public void addGameClicked(GameSquareClicked<T> gameSquareClicked) {
        Listeners listener = new Listeners(gameSquareClicked);
        addMouseListener(listener);
        addMouseMotionListener(listener);
    }

    private void paintSquare(Graphics g, T gs) {
        checkNotNull(gs, "An item in the map was null");
        checkNotNull(gs.getColor(), "The item %s colors was null !", gs);
        checkNotNull(gs.getCord(), "The item %s coordinate was null !", gs);
        g.setColor(new Color(gs.getColor().getRed(), gs.getColor().getGreen(), gs.getColor().getBlue()));
        g.fillRect(
                gs.getCord().getX() * gameMap.getWidthPixel(),
                gameMap.getHeightPixel() * (gameMap.sizeY() - gs.getCord().getY() - 1),
                gameMap.getWidthPixel(),
                gameMap.getHeightPixel()
        );
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        gameMap.getMapData().values().forEach(gs -> {
            if (gs != null){
                paintSquare(g, gs);
            }
        });
    }

    public int getSizeHeight(){
        return gameMap.getHeightPixel() * (gameMap.sizeY());
    }
    public int getSizeWidth(){
        return gameMap.getWidthPixel() * (gameMap.sizeX());
    }


    private class Listeners implements MouseListener, MouseMotionListener{
        final GameSquareClicked<T> gameSquareClicked;

        public Listeners(GameSquareClicked<T> gameSquareClicked) {
            this.gameSquareClicked = gameSquareClicked;
        }

        private Optional<T> findSquareAt(int x, int y) {
            int xReal = x / gameMap.getHeightPixel();
            int yReal = gameMap.sizeY() - y / gameMap.getHeightPixel() - 1;
            return gameMap.getFromCords(Cord.get(xReal, yReal));
        }

        private Optional<T> findSquareAt(Point point) {
            return findSquareAt((int) point.getX(), (int)point.getY());
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            findSquareAt(e.getPoint()).ifPresent(square -> gameSquareClicked.mouseDragged(square, e));
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            findSquareAt(e.getPoint()).ifPresent(square -> gameSquareClicked.mouseMoved(square, e));
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            findSquareAt(e.getPoint()).ifPresent(square -> gameSquareClicked.mouseClicked(square, e));
        }
        @Override
        public void mousePressed(MouseEvent e) {
            findSquareAt(e.getPoint()).ifPresent(square -> gameSquareClicked.mousePressed(square, e));
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            findSquareAt(e.getPoint()).ifPresent(square -> gameSquareClicked.mouseReleased(square, e));
        }
        @Override
        public void mouseEntered(MouseEvent e) {
            findSquareAt(e.getPoint()).ifPresent(square -> gameSquareClicked.mouseEntered(square, e));
        }
        @Override
        public void mouseExited(MouseEvent e) {
            findSquareAt(e.getPoint()).ifPresent(square -> gameSquareClicked.mouseExited(square, e));
        }
    }
}
