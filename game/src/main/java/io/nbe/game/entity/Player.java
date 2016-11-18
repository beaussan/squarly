package io.nbe.game.entity;

import io.nbe.squarly.model.interfaces.IState;
import io.nbe.game.map.GameSquare;
import io.nbe.game.utils.Const;

/**
 * Created by  on 31/10/16.
 */
public class Player extends Entity{

    public Player(String name, int life, int atk, GameSquare gameSquare) {
        super(name, life, atk, gameSquare, Const.PLAYER_SQUARE);
    }

    @Override
    public boolean canPassOn(GameSquare gameSquare) {
        return gameSquare.getState() != IState.WALL && gameSquare.getEntity() == null;
    }
}
