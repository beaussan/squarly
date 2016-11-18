package io.nbe.game.entity;

import io.nbe.game.map.GameSquare;

import static io.nbe.game.utils.Const.HARD_MONSTER_POINT;
import static io.nbe.game.utils.Const.HARD_MONSTER_SQUARE;

/**
 * Created by beaussan on 01/11/16.
 */
public class HardMonster extends Monster {
    public HardMonster(GameSquare gameSquare) {
        super("PGM", 100, 30, gameSquare, HARD_MONSTER_SQUARE);
    }

    public HardMonster(String name, int life, int atk, GameSquare gameSquare) {
        super(name, life, atk, gameSquare, HARD_MONSTER_SQUARE);
    }

    @Override
    public int getPointWhenDeath() {
        return HARD_MONSTER_POINT;
    }
}
