package io.nbe.game.entity;

import io.nbe.game.map.GameSquare;
import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * @author Nicolas Beaussart
 * @since 10/11/16
 */
public abstract class MonsterTest extends EntityTest {

    Monster monsterTested;

    @Override
    public Entity getEntityTested(String name, int life, int attack, GameSquare gameSquare) {
        monsterTested = getMonster(name, life, attack, gameSquare);
        return monsterTested;
    }

    public abstract Monster getMonster(String name, int life, int attack, GameSquare gameSquare);


    @Test
    public void testScore() throws Exception {
        Assertions.assertThat(monsterTested.getPointWhenDeath()).isEqualTo(getPointWhenDeath());
    }

    abstract int getPointWhenDeath();
}
