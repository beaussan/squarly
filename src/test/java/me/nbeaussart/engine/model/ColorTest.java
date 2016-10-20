package me.nbeaussart.engine.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertTrue;

/**
 * @author Nicolas Beaussart
 * @since 12/10/16
 */
public class ColorTest {


    @Test
    public void testConstructor() throws Exception {
        ColorAssert.assertThat(new Color(10,39,57))
                .hasRed(10)
                .hasGreen(39)
                .hasBlue(57);
    }
    @Test
    public void testToString() throws Exception {
        assertThat(new Color(10,39,57).toString()).isEqualTo("Color{red=10, green=39, blue=57}");
    }
    @Test
    public void testToEquals() throws Exception {
        Color c1 = new Color(10,40,180);
        Color c2 = new Color(10,40,180);
        ColorAssert.assertThat(c1).isEqualTo(c2);
        assertTrue(c1.equals(c2) && c2.equals(c1));
        assertTrue(c1.hashCode() == c2.hashCode());

    }

    @Test
    public void testExceptionRedNegate() throws Exception {
        assertThatThrownBy(() -> new Color(-1, 0, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Red should be between 0 and 255");
    }
    @Test
    public void testExceptionRedUnder255() throws Exception {
        assertThatThrownBy(() ->  new Color(256, 0, 0) )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Red should be between 0 and 255");
    }

    @Test
    public void testExceptionGreenNegate() throws Exception {
        assertThatThrownBy(() -> new Color( 0, -1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Green should be between 0 and 255");
    }
    @Test
    public void testExceptionGreenUnder255() throws Exception {
        assertThatThrownBy(() ->  new Color( 0, 256, 0) )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Green should be between 0 and 255");
    }

    @Test
    public void testExceptionBlueNegate() throws Exception {
        assertThatThrownBy(() -> new Color(0, 0, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Blue should be between 0 and 255");
    }
    @Test
    public void testExceptionBlueUnder255() throws Exception {
        assertThatThrownBy(() ->  new Color(0, 0, 256) )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Blue should be between 0 and 255");
    }

}