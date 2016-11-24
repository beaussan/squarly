package io.nbe.squarly.model;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static io.nbe.squarly.model.ColorAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertTrue;

/**
 * @author Nicolas Beaussart
 * @since 12/10/16
 */
public class ColorTest {

    @Test
    public void testConstructor() throws Exception {
        assertThat(new Color(10,39,57))
                .hasRed(10)
                .hasGreen(39)
                .hasBlue(57);
    }

    @Test
    public void testBrighter() throws Exception {
        assertThat(new Color(100,100,100).brighter())
                .hasRed(142)
                .hasGreen(142)
                .hasBlue(142);
    }


    @Test
    public void testBrighterOnlyOne() throws Exception {
        final int startnumber = 100;
        final int endingNumber = (int) (startnumber/Color.FACTOR);
        testBatchEdit(startnumber, endingNumber);
    }

    private void testBatchEdit(int startnumber, int endingNumber){
        assertThat(new Color(0,0,startnumber).brighter())
                .hasRed(0)
                .hasGreen(0)
                .hasBlue(endingNumber);
        assertThat(new Color(0,startnumber,0).brighter())
                .hasRed(0)
                .hasGreen(endingNumber)
                .hasBlue(0);
        assertThat(new Color(startnumber,0,0).brighter())
                .hasRed(endingNumber)
                .hasGreen(0)
                .hasBlue(0);
        assertThat(new Color(startnumber,0,startnumber).brighter())
                .hasRed(endingNumber)
                .hasGreen(0)
                .hasBlue(endingNumber);
        assertThat(new Color(startnumber,startnumber,0).brighter())
                .hasRed(endingNumber)
                .hasGreen(endingNumber)
                .hasBlue(0);
        assertThat(new Color(0,startnumber,startnumber).brighter())
                .hasRed(0)
                .hasGreen(endingNumber)
                .hasBlue(endingNumber);
        assertThat(new Color(startnumber,startnumber,startnumber).brighter())
                .hasRed(endingNumber)
                .hasGreen(endingNumber)
                .hasBlue(endingNumber);
    }


    @Test
    public void testBrighterDark() throws Exception {
        int fact = (int) (1.0/(1.0-Color.FACTOR));
        assertThat(new Color(0,0,0).brighter())
                .hasBlue(fact)
                .hasGreen(fact)
                .hasRed(fact);
    }


    @Test
    public void testDarker() throws Exception {
        int startColor = 100;
        testBatchEdit(startColor, (int) (startColor/Color.FACTOR));
    }

    @Test
    public void testToString() throws Exception {
        Assertions.assertThat(new Color(10,39,57).toString()).isEqualTo("Color{red=10, green=39, blue=57}");
    }

    @Test
    public void testGenerateFromAWT() throws Exception {
        assertThat(new Color(new java.awt.Color(20,30,40)))
                .hasRed(20)
                .hasGreen(30)
                .hasBlue(40);
    }

    @Test
    public void testGeneratingAWT() throws Exception {

        Assertions.assertThat(new Color(20,30,40).getAwt()).matches(color -> color.getRed() == 20)
                .matches(color -> color.getGreen() == 30)
                .matches(color -> color.getBlue() == 40);
    }

    @Test
    public void testToEquals() throws Exception {
        Color c1 = new Color(10,40,180);
        Color c2 = new Color(10,40,180);
        assertThat(c1).isEqualTo(c2);
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