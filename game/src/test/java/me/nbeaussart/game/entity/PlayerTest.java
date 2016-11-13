package me.nbeaussart.game.entity;

import me.nbeaussart.engine.model.Color;
import me.nbeaussart.game.map.GameSquare;
import me.nbeaussart.game.map.SquareContent;
import me.nbeaussart.game.utils.Const;

/**
 * Created by beaussan on 31/10/16.
 */
public class PlayerTest extends EntityTest {
    @Override
    public Entity getEntityTested(String name, int life, int attack, GameSquare gameSquare) {
        return new Player(name, life, attack, gameSquare);
    }

    @Override
    public SquareContent getColorOfEntity() {
        return Const.PLAYER_SQUARE;
    }


}
