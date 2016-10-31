package me.nbeaussart.game.ia;

import me.nbeaussart.engine.model.Direction;
import me.nbeaussart.game.action.Action;
import me.nbeaussart.game.action.Move;
import me.nbeaussart.game.entity.Entity;
import me.nbeaussart.game.entity.Monster;

import java.util.Random;

/**
 * Created by beaussan on 31/10/16.
 */
public class DummyIA extends AbstractIA {

    private final Random r = new Random();

    @Override
    protected Action doStuff(Monster monster) {
        switch (r.nextInt(5)){
            case 0 :
                return new Move(monster, Direction.UP);
            case 1 :
                return new Move(monster, Direction.DOWN);
            case 2 :
                return new Move(monster, Direction.LEFT);
            case 3 :
                return new Move(monster, Direction.RIGHT);
            default :
                return null;
        }
    }
}
