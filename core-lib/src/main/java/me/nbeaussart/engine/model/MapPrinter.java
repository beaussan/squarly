package me.nbeaussart.engine.model;

import com.google.common.base.Preconditions;

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
public class MapPrinter extends JPanel {
    private GameMap gameMap;

    public MapPrinter(GameMap gameMap) {
        this.gameMap = Preconditions.checkNotNull(gameMap, "Game map should not be null.");
        setSize(getSizeWidth(), getSizeHeight());

        setFocusable(true);
        this.requestFocusInWindow();
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    private Optional<GameSquare> findSquareAt(int x, int y){
        int xReal = x/gameMap.getHeight();
        int yReal = y/gameMap.getWidth();
        return gameMap.getMapData().stream().filter(square -> square.getCoord().getX() == xReal)
                .filter(square -> square.getCoord().getY() == yReal)
                .findFirst();
    }
    private Optional<GameSquare> findSquareAt(Point point){
        return findSquareAt((int) point.getX(), (int)point.getY());
    }

    public void addGameClicked (GameSquareClicked gameSquareClicked){
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
            g.setColor(new Color(gs.getColor().getRed(), gs.getColor().getGreen(), gs.getColor().getBlue()));
            g.fillRect(
                    gs.getCoord().getX()*gameMap.getWidth(),
                    gs.getCoord().getY()*gameMap.getHeight(),
                    gameMap.getWidth(),
                    gameMap.getHeight()
            );
        });
    }

    public int getSizeHeight(){
        return gameMap.getHeight() * (gameMap.sizeY() + 2);
    }
    public int getSizeWidth(){
        return gameMap.getWidth() * (gameMap.sizeX() + 1 );
    }
}
