package me.nbeaussart.engine.view;

import com.google.common.base.Preconditions;
import me.nbeaussart.engine.model.Cord;
import me.nbeaussart.engine.model.interfaces.ICaracterSquare;
import me.nbeaussart.engine.model.interfaces.ICoordinateSquare;
import me.nbeaussart.engine.model.interfaces.IGameMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Nicolas Beaussart
 * @since 01/11/16
 */
public class MapCharDrowers<T extends ICoordinateSquare & ICaracterSquare> extends JPanel{

    private IGameMap<T> gameMap;
    private Font font;


    public MapCharDrowers(IGameMap<T> gameMap) {
        this.gameMap = checkNotNull(gameMap, "Game map should not be null.");

        setPreferredSize(new Dimension(getSizeWidth(), getSizeHeight()));
        font = new Font("Monospaced", Font.PLAIN, 32);


        setFocusable(true);
        this.requestFocusInWindow();
        gameMap.addUpdatesHandlers(t -> repaint());
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
        return gameMap.getFromCords(new Cord(xReal, yReal));
        /*
        return gameMap.getMapData().stream().filter(square -> square.getCord().getX() == xReal)
                .filter(square -> square.getCord().getY() == yReal)
                .findFirst();*/
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

    private void paintSquare(Graphics g, T gs) {
        checkNotNull(gs, "An item in the map was null");

        //g.setColor(new Color(gs.getColor().getRed(), gs.getColor().getGreen(), gs.getColor().getBlue()));
        g.fillRect(
                gs.getCord().getX() * gameMap.getWidthPixel(),
                (gameMap.getHeightPixel() * (gameMap.sizeY() - gs.getCord().getY() - 1)),
                gameMap.getWidthPixel(),
                gameMap.getHeightPixel()
        );
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        gameMap.getMapData().values().forEach(gs -> {
            paintSquare(g, gs);
        });
    }

    public int getSizeHeight(){
        return gameMap.getHeightPixel() * (gameMap.sizeY());
    }
    public int getSizeWidth(){
        return gameMap.getWidthPixel() * (gameMap.sizeX());
    }
}
