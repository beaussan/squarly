package me.nbeaussart.engine.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Nicolas Beaussart
 * @since 10/11/16
 */
public class DirectionTest {
    @Test
    public void getOpposite() throws Exception {
        DirectionAssert.assertThat(Direction.UP).hasOpposite(Direction.DOWN);
        DirectionAssert.assertThat(Direction.DOWN).hasOpposite(Direction.UP);
        DirectionAssert.assertThat(Direction.RIGHT).hasOpposite(Direction.LEFT);
        DirectionAssert.assertThat(Direction.LEFT).hasOpposite(Direction.RIGHT);
    }

}