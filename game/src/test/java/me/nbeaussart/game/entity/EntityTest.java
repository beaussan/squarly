package me.nbeaussart.game.entity;

import me.nbeaussart.engine.model.Color;
import me.nbeaussart.engine.model.Cord;
import me.nbeaussart.game.map.GameMap;
import me.nbeaussart.game.map.GameSquare;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import static me.nbeaussart.game.entity.EntityAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created by beaussan on 31/10/16.
 */
public abstract class EntityTest {

    private Entity entityTested;
    private final String NAME = "ENTITY_TEST";
    private final int LIFE_INIT = 20;
    private final int ATTACK_INIT = 10;
    private GameSquare gameSquare;

    public abstract Entity getEntityTested(String name, int life, int attack, GameSquare gameSquare);

    public abstract Color getColorOfEntity();

    @Before
    public void setUp() throws Exception {
        gameSquare = new GameSquare(new Cord(0,0), new GameMap(20,20));
        entityTested = getEntityTested(NAME, LIFE_INIT, ATTACK_INIT, gameSquare);
    }

    @Test
    public void testConstructor() throws Exception {
        assertThatThrownBy(() -> getEntityTested(null, 10, 10, gameSquare))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Name is null");
    }
    @Test
    public void testConstructor2() throws Exception {
        assertThatThrownBy(() -> getEntityTested(NAME, 10, -1, gameSquare))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("The attack was below 0 (-1)");

    }
    @Test
    public void testConstructor3() throws Exception {
        assertThat(getEntityTested(NAME, -1, 10, gameSquare)).hasLife(0).isNotALive();
    }

    @Test
    public void testConstructor4() throws Exception {
        assertThatThrownBy(() -> getEntityTested(NAME, 1, 10, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("gameSquare is null");

    }

    @Test
    public void testLifeShouldBeOver0() throws Exception{
        entityTested.setLife(-1);
        assertThat(entityTested).isNotALive().hasLife(0);
    }

    @Test
    public void testAtackShouldBeOver0() throws Exception{
        assertThatThrownBy(() -> entityTested.setAtk(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("The attack was below 0 (-1)");
    }
    @Test
    public void testAtackSet() throws Exception{
        entityTested.setAtk(3);
        assertThat(entityTested).hasAtk(3);

    }

    @Test
    public void testGetLife() throws Exception{
        assertThat(entityTested).hasLife(LIFE_INIT);
    }

    @Test
    public void testGetAtack() throws Exception{
        assertThat(entityTested).hasAtk(ATTACK_INIT);
    }

    @Test
    public void testGetName() throws Exception{
        assertThat(entityTested).hasName(NAME);
    }

    @Test
    public void testGetSquare() throws Exception{
        assertThat(entityTested).hasGameSquare(gameSquare);
    }

    @Test
    public void testGetColor() throws Exception {
        assertThat(entityTested).hasColor(getColorOfEntity());
    }

    @Test
    public void testEquals() throws Exception {
        assertThat(entityTested).isEqualTo(getEntityTested(NAME, LIFE_INIT, ATTACK_INIT, gameSquare));
    }
    @Test
    public void testEqualsHashCode() throws Exception {
        Assertions.assertThat(entityTested.hashCode()).isEqualTo(
                getEntityTested(NAME, LIFE_INIT, ATTACK_INIT, gameSquare).hashCode()
        );
    }

    @Test
    public void testToString() throws Exception {
        Assertions.assertThat(entityTested.toString()).matches(entityTested.getClass().getSimpleName()+"\\{.*\\}")
                .contains(
                        "name="+ NAME,
                        "life=" + LIFE_INIT,
                        "color=" + getColorOfEntity().toString(),
                        "atk=" + ATTACK_INIT,
                        "gameSquare=" + gameSquare.toString(),
                        "isAlive=" + true);

    }


}