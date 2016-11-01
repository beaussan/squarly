package me.nbeaussart.game.entity;

import me.nbeaussart.engine.model.Color;
import me.nbeaussart.engine.model.interfaces.IState;
import me.nbeaussart.game.map.GameSquare;
import me.nbeaussart.game.map.SquareContent;

/**
 * Created by beaussan on 31/10/16.
 */
public abstract class Monster extends Entity {

    public Monster(String name, int life, int atk, Color color, GameSquare gameSquare, SquareContent squareContent) {
        super(name, life, atk, color, gameSquare, squareContent);
    }

    @Override
    public boolean canPassOn(GameSquare gameSquare) {
        return gameSquare.getState() != IState.WALL && gameSquare.getEntity() == null;
    }

    public abstract int getPointWhenDeath();


}
