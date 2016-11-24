package io.nbe.squarly.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author Nicolas Beaussart
 * @since 24/11/16
 */
public class PairTest {


    public static final int KEY = 10;
    public static final int VALUE = 20;
    private Pair<Integer, Integer> pair;
    @Before
    public void setUp() throws Exception {
        pair = new Pair<>(KEY, VALUE);
    }

    @Test
    public void getKey() throws Exception {
        assertThat(pair.getKey()).isEqualTo(KEY);
    }

    @Test
    public void getValue() throws Exception {
        assertThat(pair.getValue()).isEqualTo(VALUE);
    }

    @Test
    public void toStringTest() throws Exception {
        assertThat(pair.toString()).isEqualToIgnoringCase(KEY + "=" + VALUE);
    }

    @Test
    public void equalsTest() throws Exception {
        assertThat(pair).isEqualToComparingFieldByField(new Pair<>(KEY, VALUE));
        Assert.assertTrue(pair.equals(new Pair<>(KEY, VALUE)));
        Assert.assertFalse(pair.equals(new Pair<>(KEY+1, VALUE)));
        Assert.assertFalse(pair.equals(new Pair<>(KEY, VALUE+1)));
        Assert.assertFalse(pair.equals(null));
        Assert.assertTrue(pair.equals(pair));
        Assert.assertFalse(pair.equals(""));
        Assert.assertEquals(new Pair<>(KEY, VALUE).hashCode(), pair.hashCode());
    }

}