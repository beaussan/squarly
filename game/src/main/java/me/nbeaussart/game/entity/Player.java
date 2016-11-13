package me.nbeaussart.game.entity;

import me.nbeaussart.engine.model.Color;
import me.nbeaussart.engine.model.interfaces.IState;
import me.nbeaussart.game.map.GameSquare;
import me.nbeaussart.game.map.SquareContent;
import me.nbeaussart.game.utils.Const;

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
