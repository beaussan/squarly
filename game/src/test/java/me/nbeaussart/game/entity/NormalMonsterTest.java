package me.nbeaussart.game.entity;

import me.nbeaussart.game.map.GameSquare;
import me.nbeaussart.game.map.SquareContent;
import me.nbeaussart.game.utils.Const;

import static org.junit.Assert.*;

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