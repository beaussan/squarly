package io.nbe.game.entity;

import io.nbe.game.utils.Const;
import io.nbe.game.map.GameSquare;
import io.nbe.game.map.SquareContent;

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
