package io.nbe.squarly.view.console;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Nicolas Beaussart
 * @since 27/11/16
 */
public class DataCharTest {
    DataChar data;
    @Before
    public void setUp() throws Exception {
        data = DataChar.get('c', Color.BLACK, Color.BLUE, null);

    }

    @Test
    public void getC() throws Exception {
        assertThat(data.getC()).isEqualTo('c');
    }

    @Test
    public void getFont() throws Exception {
        assertThat(data.getFont()).isEqualTo(null);
    }

    @Test
    public void getBackground() throws Exception {
        assertThat(data.getBackground()).isEqualTo(Color.BLACK);
    }

    @Test
    public void getForeground() throws Exception {
        assertThat(data.getForeground()).isEqualTo(Color.BLUE);
    }

    @Test
    public void testEquals() throws Exception {
        assertThat(data == DataChar.get('c', Color.BLACK, Color.BLUE, null)).isTrue();
    }


    @Test
    public void testEqualsEq() throws Exception {
        assertThat(data.equals(DataChar.get('c', Color.BLACK, Color.BLUE, null))).isTrue();
        assertThat(data.equals(DataChar.get('c', Color.BLACK, Color.BLUE))).isTrue();
        assertThat(data.equals(null)).isFalse();
        assertThat(data.equals("ba")).isFalse();
    }


    @Test
    public void testEqualsEqFalse() throws Exception {
        assertThat(data.equals(DataChar.get(' ', Color.BLACK, Color.BLUE, null))).isFalse();
        assertThat(data.equals(data.alterBackground(Color.DARK_GRAY))).isFalse();
        assertThat(data.equals(data.alterForeground(Color.DARK_GRAY))).isFalse();
    }

    @Test
    public void testHash() throws Exception {
        assertThat(data.hashCode()).isEqualTo(DataChar.get('c', Color.BLACK, Color.BLUE, null).hashCode());
    }

    @Test
    public void testString() throws Exception {
        assertThat(data.toString()).isEqualTo("DataChar{c=c, font=null, background=java.awt.Color[r=0,g=0,b=0], foreground=java.awt.Color[r=0,g=0,b=255]}");
    }

    @Test
    public void testChangeBackground() throws Exception {
        assertThat(data.alterBackground(Color.CYAN).getBackground()).isEqualTo(Color.CYAN);
    }
    @Test
    public void testChangeForeground() throws Exception {
        assertThat(data.alterForeground(Color.CYAN).getForeground()).isEqualTo(Color.CYAN);
    }

    @Test
    public void testChangeChar() throws Exception {
        assertThat(data.alterC('d').getC()).isEqualTo('d');
    }

    @Test
    public void testChangeFont() throws Exception {
        Font f = new Font("arial", 0,30);
        assertThat(data.alterFont(f).getFont()).isEqualTo(f);
    }

}