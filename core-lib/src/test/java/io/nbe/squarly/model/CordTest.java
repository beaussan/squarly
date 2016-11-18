package io.nbe.engine.model;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Nicolas Beaussart
 * @since 12/10/16
 */
public class CordTest {

    public static final int Y = 19;
    public static final int X = 10;
    private Cord cord;

    @Before
    public void setupTest() {
        cord = Cord.get(X, Y);
    }

    @Test
    public void constructor() throws Exception {
        CordAssert.assertThat(cord).hasX(X).hasY(Y);
    }



    @Test
    public void testSingleInstance() throws Exception {
        Assert.assertTrue(Cord.get(X,Y) == Cord.get(X,Y));
        Assert.assertTrue(Cord.get(X,Y) != Cord.get(X+1,Y));
    }

    @Test
    public void testHash() throws Exception {
        Assertions.assertThat(Cord.get(10,5).hashCode()).isNotEqualTo(Cord.get(5,10).hashCode());
        Assertions.assertThat(Cord.get(10,5).hashCode()).isEqualTo(Cord.get(10,5).hashCode());
    }

    @Test
    public void testHashExtaned() throws Exception {
        for (int x = -100; x < 100; x++) {
            for (int y = -100; y < 100; y++) {
                CordAssert.assertThat(Cord.get(x,y)).hasX(x).hasY(y);
            }
        }
    }

    @Test
    public void testAdd() throws Exception {
        CordAssert.assertThat(cord.add(5, 6))
                .hasX(X+5)
                .hasY(Y+6);
    }

    @Test
    public void testAddDirection() throws Exception {
        CordAssert.assertThat(cord.add(Direction.UP))
                .isEqualTo(cord.add(Direction.UP.getCords()));
    }

    @Test
    public void testAddDirectionDiag() throws Exception {
        CordAssert.assertThat(cord.add(Direction.UP.add(Direction.LEFT)))
                .isEqualTo(cord.add(Direction.UP.getCords().add(Direction.LEFT.getCords())));
    }

    @Test
    public void testAddObj() throws Exception {
        int xAdded = 5;
        int yAdded = 6;
        CordAssert.assertThat(cord.add(Cord.get(xAdded, yAdded)))
                .hasX(X+ xAdded)
                .hasY(Y+ yAdded);
    }

    @Test
    public void testMinus() throws Exception {
        int xMinus = 5;
        int yMinus = 6;
        CordAssert.assertThat(cord.minus(xMinus, yMinus))
                .hasX(X - xMinus)
                .hasY(Y - yMinus);

    }

    @Test
    public void testMminusObj() throws Exception {
        int xMinus = 5;
        int yMinus = 6;
        CordAssert.assertThat(cord.minus(Cord.get(xMinus, yMinus)))
                .hasX(X - xMinus)
                .hasY(Y - yMinus);

    }

    @Test
    public void testToString() throws Exception {
        Assertions.assertThat(cord.toString())
        .matches("Cord\\{.*\\}")
        .contains("x="+X, "y="+Y);
    }

    @Test
    public void testEquals() throws Exception {
        Cord c1 = Cord.get(20, 40);
        Cord c2 = Cord.get(20, 40);
        CordAssert.assertThat(c1).isEqualTo(c2);
        Assert.assertTrue(c1.equals(c2) && c2.equals(c1));
        Assert.assertTrue(c1.hashCode() == c2.hashCode());
    }

}
