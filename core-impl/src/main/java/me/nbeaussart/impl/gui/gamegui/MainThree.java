package me.nbeaussart.impl.gui.gamegui;

import me.nbeaussart.engine.model.Cord;
import me.nbeaussart.engine.model.GameGenerator;

/**
 * @author Nicolas Beaussart
 * @since 27/10/16
 */
public class MainThree {
    public static void main(String... args){
        GameMap gm = new GameMap(40, 40, 20, 20);
        for (int y = 0; y < gm.sizeY(); y++) {
            for (int x = 0; x < gm.sizeX(); x++) {
                gm.getMapData().add(new GameSquare(null, new Cord(x,y), gm));
            }
        }
        new GameGenerator<GameSquare>(gm, true).useMazeGenerator().generate();
        gm = new GameMap(40, 40, 20, 20);
        for (int y = 0; y < gm.sizeY(); y++) {
            for (int x = 0; x < gm.sizeX(); x++) {
                gm.getMapData().add(new GameSquare(null, new Cord(x,y), gm));
            }
        }
        new GameGenerator<GameSquare>(gm, true).useMazeGeneratorClean().generate();
        System.out.println("Done");
    }
}
