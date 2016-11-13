package me.nbeaussart.game.entity;

import me.nbeaussart.engine.model.Color;
import me.nbeaussart.game.map.GameSquare;
import me.nbeaussart.game.map.SquareContent;

import static me.nbeaussart.game.utils.Const.EASY_MONSTER_POINT;
import static me.nbeaussart.game.utils.Const.EASY_MONSTER_SQUARE;

/**
 * Created by beaussan on 01/11/16.
 */
public class EasyMonster extends Monster {
    public EasyMonster(GameSquare gameSquare) {
        super("Noob", 20, 10, gameSquare, EASY_MONSTER_SQUARE);

    }
    public EasyMonster(String name, int life, int atk, GameSquare gameSquare) {
        super(name, life, atk, gameSquare, EASY_MONSTER_SQUARE);
    }



    @Override
    public int getPointWhenDeath() {
        return EASY_MONSTER_POINT;
    }
}
