package me.nbeaussart.game.entity;

import me.nbeaussart.engine.model.Color;
import me.nbeaussart.game.map.GameSquare;

/**
 * Created by beaussan on 31/10/16.
 */
public class MonsterTest extends EntityTest {

    @Override
    public Entity getEntityTested(String name, int life, int attack, GameSquare gameSquare) {
        return new Monster(name, life, attack, gameSquare);
    }

    @Override
    public Color getColorOfEntity() {
        return new Color(255,0,0);
    }
}
