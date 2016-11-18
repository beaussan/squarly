package io.nbe.game.entity;

import io.nbe.game.utils.Const;
import io.nbe.game.map.GameSquare;
import io.nbe.game.map.SquareContent;

/**
 * @author Nicolas Beaussart
 * @since 10/11/16
 */
public class NormalMonsterTest extends MonsterTest {

    @Override
    public Monster getMonster(String name, int life, int attack, GameSquare gameSquare) {
        return new NormalMonster(name, life, attack, gameSquare);
    }

    @Override
    int getPointWhenDeath() {
        return Const.NORMAL_MONSTER_POINT;
    }

    @Override
    public SquareContent getColorOfEntity() {
        return Const.NORMAL_MONSTER_SQUARE;
    }
}