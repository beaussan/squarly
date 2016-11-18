package io.nbe.game.entity;

import io.nbe.game.map.SquareContent;
import io.nbe.game.utils.Const;
import io.nbe.game.map.GameSquare;

/**
 * @author Nicolas Beaussart
 * @since 10/11/16
 */
public class EasyMonsterTest extends MonsterTest {

    @Override
    public Monster getMonster(String name, int life, int attack, GameSquare gameSquare) {
        return new EasyMonster(name, life, attack, gameSquare);
    }

    @Override
    int getPointWhenDeath() {
        return Const.EASY_MONSTER_POINT;
    }

    @Override
    public SquareContent getColorOfEntity() {
        return Const.EASY_MONSTER_SQUARE;
    }
}