package io.nbe.game.entity;

import io.nbe.game.utils.Const;
import io.nbe.game.map.GameSquare;
import io.nbe.game.map.SquareContent;

/**
 * @author Nicolas Beaussart
 * @since 10/11/16
 */
public class HardMonsterTest extends MonsterTest {

    @Override
    public Monster getMonster(String name, int life, int attack, GameSquare gameSquare) {
        return new HardMonster(name, life, attack, gameSquare);
    }

    @Override
    int getPointWhenDeath() {
        return Const.HARD_MONSTER_POINT;
    }

    @Override
    public SquareContent getColorOfEntity() {
        return Const.HARD_MONSTER_SQUARE;
    }
}