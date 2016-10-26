package me.nbeaussart.engine.view;

import com.google.common.base.Preconditions;
import me.nbeaussart.engine.model.interfaces.IColoredSquare;
import me.nbeaussart.engine.model.interfaces.ICoordinateSquare;
import me.nbeaussart.engine.model.interfaces.IGameMap;

import javax.swing.*;
import java.awt.Color;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Optional;

/**
 * @author Nicolas Beaussart
 * @since 20/10/16
 */
public class MapPrinter<T extends IColoredSquare & ICoordinateSquare> extends JPanel {
    private IGameMap<T> gameMap;

    public MapPrinter(IGameMap<T> gameMap) {
        this.gameMap = Preconditions.checkNotNull(gameMap, "Game map should not be null.");
        setPreferredSize(new Dimension(getSizeWidth(), getSizeHeight()));


        setFocusable(true);
        this.requestFocusInWindow();
        System.out.println("Height\t" + getSizeHeight());
        System.out.println("sizeY\t" + gameMap.sizeY());
        System.out.println("getHeightPixel\t" + gameMap.getHeightPixel());
        System.out.println("Width\t" + getSizeWidth());
        System.out.println("sizeX\t" + gameMap.sizeX());
        System.out.println("getWidthPixel\t" + gameMap.getWidthPixel());
    }

    public IGameMap getGameMap() {
        return gameMap;
    }

    public void setGameMap(IGameMap<T> gameMap) {
        this.gameMap = gameMap;
    }

    private Optional<T> findSquareAt(int x, int y) {
        int xReal = x / gameMap.getHeightPixel();
        //int yReal = (y-gameMap.sizeY()-1)/gameMap.getHeightPixel();
        int yReal = gameMap.sizeY() - y / gameMap.getHeightPixel() - 1;
        return gameMap.getMapData().stream().filter(square -> square.getCoord().getX() == xReal)
                .filter(square -> square.getCoord().getY() == yReal)
                .findFirst();
    }

    private Optional<T> findSquareAt(Point point) {
        return findSquareAt((int) point.getX(), (int)point.getY());
    }

    public void addGameClicked(GameSquareClicked<T> gameSquareClicked) {
        this.addMouseListener(new MouseListener() {
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
        });
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                findSquareAt(e.getPoint()).ifPresent(square -> gameSquareClicked.mouseDragged(square, e));
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                findSquareAt(e.getPoint()).ifPresent(square -> gameSquareClicked.mouseMoved(square, e));
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        gameMap.getMapData().forEach(gs -> {
            Preconditions.checkNotNull(gs, "An item in the map was null");
            Preconditions.checkNotNull(gs.getColor(), "The item %s colors was null !", gs);
            Preconditions.checkNotNull(gs.getCoord(), "The item %s coordinate was null !", gs);
            g.setColor(new Color(gs.getColor().getRed(), gs.getColor().getGreen(), gs.getColor().getBlue()));
            g.fillRect(
                    gs.getCoord().getX() * gameMap.getWidthPixel(),
                    (gameMap.getHeightPixel() * (gameMap.sizeY() - gs.getCoord().getY() - 1)),
                    gameMap.getWidthPixel(),
                    gameMap.getHeightPixel()
            );
            // g.fillRect(x,y,width, height);
        });
    }

    public int getSizeHeight(){
        return gameMap.getHeightPixel() * (gameMap.sizeY());
    }
    public int getSizeWidth(){
        return gameMap.getWidthPixel() * (gameMap.sizeX());
    }
}