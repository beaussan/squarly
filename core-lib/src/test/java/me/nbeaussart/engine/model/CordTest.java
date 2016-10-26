package me.nbeaussart.engine.model;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Nicolas Beaussart
 * @since 12/10/16
 */
public class CordTest {

    private Cord cord;

    @Before
    public void setupTest() {
        cord = new Cord(10, 4);
    }

    @Test
    public void constructor() throws Exception {
        CordAssert.assertThat(cord).hasX(10).hasY(4);
    }

    @Test
    public void testAdd() throws Exception {
        CordAssert.assertThat(cord.add(5, 6))
                .hasX(15)
                .hasY(10);
    }

    @Test
    public void testAddObj() throws Exception {
        CordAssert.assertThat(cord.add(new Cord(5, 6)))
                .hasX(15)
                .hasY(10);
    }

    @Test
    public void testMinus() throws Exception {
        CordAssert.assertThat(cord.minus(5, 6))
                .hasX(5)
                .hasY(-2);

    }

    @Test
    public void testMminusObj() throws Exception {
        CordAssert.assertThat(cord.minus(new Cord(5, 6)))
                .hasX(5)
                .hasY(-2);

    }

    @Test
    public void testToString() throws Exception {
        Assertions.assertThat(cord.toString()).isEqualTo("Cord{x=10, y=4}");
    }

    @Test
    public void testEquals() throws Exception {
        Cord c1 = new Cord(20, 40);
        Cord c2 = new Cord(20, 40);
        CordAssert.assertThat(c1).isEqualTo(c2);
        Assert.assertTrue(c1.equals(c2) && c2.equals(c1));
        Assert.assertTrue(c1.hashCode() == c2.hashCode());
    }

}