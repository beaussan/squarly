package me.nbeaussart.engine.model;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Nicolas Beaussart
 * @since 12/10/16
 */
public class CoordTest {

    private Coord coord;

    @Before
    public void setupTest() {
        coord = new Coord(10,4);
    }

    @Test
    public void constructor() throws Exception {
        CoordAssert.assertThat(coord).hasX(10).hasY(4);
    }

    @Test
    public void testAdd() throws Exception {
        CoordAssert.assertThat(coord.add(5,6))
                .hasX(15)
                .hasY(10);
    }

    @Test
    public void testAddObj() throws Exception {
        CoordAssert.assertThat(coord.add(new Coord(5,6)))
                .hasX(15)
                .hasY(10);
    }

    @Test
    public void testMinus() throws Exception {
        CoordAssert.assertThat(coord.minus(5,6))
                .hasX(5)
                .hasY(-2);

    }

    @Test
    public void testMminusObj() throws Exception {
        CoordAssert.assertThat(coord.minus(new Coord(5,6)))
                .hasX(5)
                .hasY(-2);

    }

    @Test
    public void testToString() throws Exception {
        Assertions.assertThat(coord.toString()).isEqualTo("Coord{x=10, y=4}");
    }

    @Test
    public void testEquals() throws Exception {
        Coord c1 = new Coord(20,40);
        Coord c2 = new Coord(20,40);
        CoordAssert.assertThat(c1).isEqualTo(c2);
        Assert.assertTrue(c1.equals(c2) && c2.equals(c1));
        Assert.assertTrue(c1.hashCode() == c2.hashCode());
    }

}