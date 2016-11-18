package io.nbe.game.utils;

import io.nbe.squarly.model.Color;
import io.nbe.game.map.SquareContent;

/**
 * @author Nicolas Beaussart
 * @since 10/11/16
 */
public final class Const {
    private Const(){}
    public static final SquareContent PLAYER_SQUARE = new SquareContent('@', null, new Color(255,255,255));
    public static final SquareContent NORMAL_MONSTER_SQUARE = new SquareContent('b', null, new Color(255,255,0));
    public static final SquareContent HARD_MONSTER_SQUARE = new SquareContent('c', null, new Color(255,0,0));
    public static final SquareContent EASY_MONSTER_SQUARE = new SquareContent('a', null, new Color(255,0,255));

    public static final int EASY_MONSTER_POINT = 10;
    public static final int NORMAL_MONSTER_POINT = 100;
    public static final int HARD_MONSTER_POINT = 1000;
}
