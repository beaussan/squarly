package io.nbe.squarly.view;

import io.nbe.squarly.model.interfaces.ICharacterSquare;
import io.nbe.squarly.model.interfaces.ICoordinateSquare;
import io.nbe.squarly.view.console.JConsole;
import io.nbe.squarly.model.interfaces.IGameMap;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Nicolas Beaussart
 * @since 01/11/16
 */
public class MapCharDrowers<T extends ICoordinateSquare & ICharacterSquare> {

    private final JConsole jConsole;
    private final IGameMap<T> gameMap;
    private int xoff = 0;
    private int yoff = 0;

    public MapCharDrowers(IGameMap<T> gameMap, JConsole jConsole) {
        this.jConsole = checkNotNull(jConsole, "JConsole is null");
        this.gameMap = checkNotNull(gameMap, "Game map should not be null.");
        //font = new Font("Monospaced", Font.PLAIN, 32);


        /*
        gameMap.addUpdatesHandlers(t -> {
            drawEverything();
            System.out.println(t);
        });
        //*/
        //*
        gameMap.addUpdatesHandlers(t -> {
            if (t.isPresent()){
                drawSingle(t.get());
            } else {
                drawEverything();
            }
            jConsole.repaint();
        });
        //*/
        drawEverything();
        jConsole.repaint();
    }
    public MapCharDrowers(IGameMap<T> gameMap, JConsole jConsole, int xoff, int yoff) {
        this.jConsole = checkNotNull(jConsole, "JConsole is null");
        this.gameMap = checkNotNull(gameMap, "Game map should not be null.");
        //font = new Font("Monospaced", Font.PLAIN, 32);


        checkArgument(0 <= xoff);
        checkArgument(0 <= yoff);
        this.xoff = xoff;
        this.yoff = yoff;

        /*
        gameMap.addUpdatesHandlers(t -> {
            drawEverything();
            System.out.println(t);
        });
        //*/
        //*
        gameMap.addUpdatesHandlers(t -> {
            if (t.isPresent()){
                drawSingle(t.get());
            } else {
                drawEverything();
            }
        });
        //*/
        drawEverything();
        jConsole.repaint();
    }

    public IGameMap getGameMap() {
        return gameMap;
    }

    public void drawEverything(){
        gameMap.getMapData().values().forEach(this::drawSingle);
    }

    public void drawSingle(T gs){
        /*
        jConsole.setCursorPos(gs.getCord().getX() + xoff,
                gameMap.sizeY()-gs.getCord().getY()-1 + yoff
        );
        */
        jConsole.write(""+gs.getChar(),gs.getForeground().getAwt(), gs.getBackground().getAwt(), gs.getCord().getX() + xoff
        , gameMap.sizeY()-gs.getCord().getY()-1 + yoff);
        //jConsole.write(st, fore, back);
    }


}
