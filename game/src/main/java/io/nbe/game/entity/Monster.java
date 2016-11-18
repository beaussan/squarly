package io.nbe.game.entity;

import io.nbe.squarly.model.interfaces.IState;
import io.nbe.game.map.GameSquare;
import io.nbe.game.map.SquareContent;

/**
 * Created by beaussan on 31/10/16.
 */
public abstract class Monster extends Entity {

    public Monster(String name, int life, int atk, GameSquare gameSquare, SquareContent squareContent) {
        super(name, life, atk, gameSquare, squareContent);
    }

    @Override
    public boolean canPassOn(GameSquare gameSquare) {
        return gameSquare.getState() != IState.WALL && gameSquare.getEntity() == null;
    }

    public abstract int getPointWhenDeath();


}
