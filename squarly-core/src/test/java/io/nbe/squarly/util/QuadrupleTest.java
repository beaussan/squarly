package io.nbe.squarly.util;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Nicolas Beaussart
 * @since 27/11/16
 */
public class QuadrupleTest {
    public static final int VAL4 = 30;
    public static final boolean VAL3 = true;
    public static final String VAL2 = "aba";
    public static final int VAL1 = 10;
    Quadruple<Integer, String , Boolean, Integer> data;
    @Before
    public void setUp() throws Exception {
        data = new Quadruple<>(VAL1, VAL2, VAL3, VAL4);
    }

    @Test
    public void testEquals() throws Exception {
        assertThat(data.equals(new Quadruple<>(VAL1, VAL2, VAL3, VAL4))).isTrue();
        assertThat(data.equals(data)).isTrue();
        assertThat(data.equals("aba")).isFalse();
        assertThat(data.equals(null)).isFalse();
    }

    @Test
    public void testVal1() throws Exception {
        assertThat(data.getVal1()).isEqualTo(VAL1);
    }

    @Test
    public void testVal2() throws Exception {
        assertThat(data.getVal2()).isEqualTo(VAL2);
    }

    @Test
    public void testVal3() throws Exception {
        assertThat(data.getVal3()).isEqualTo(VAL3);
    }

    @Test
    public void testVal4() throws Exception {
        assertThat(data.getVal4()).isEqualTo(VAL4);
    }

    @Test
    public void testHash() throws Exception {
        assertThat(data.hashCode()).isEqualTo(new Quadruple<>(VAL1, VAL2, VAL3, VAL4).hashCode());
    }

    @Test
    public void testString() throws Exception {
        assertThat(data.toString())
                .isEqualToIgnoringCase("Quadruple{val1="+VAL1+", val2="+VAL2+", val3="+VAL3+", val4="+VAL4+"}");
    }

}