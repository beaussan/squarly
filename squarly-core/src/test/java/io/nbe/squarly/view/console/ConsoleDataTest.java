package io.nbe.squarly.view.console;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Nicolas Beaussart
 * @since 11/11/16
 */
public class ConsoleDataTest {
    @Test
    public void testCreateData() {
        ConsoleData cd=new ConsoleData();
        cd.init(80, 25);

        // should be filled with zero chars
        assertThat(cd.getCharAt(0,0)).isEqualTo(0);
    }

    @Test
    public void testFill() {
        ConsoleData cd=new ConsoleData();
        cd.init(80, 25);

        cd.fillArea('X', null, null, null, 1, 1, 10, 10);

        // should be filled with zero chars
        assertThat(cd.getCharAt(0,0)).isEqualTo(0);
        assertThat(cd.getCharAt(1,1)).isEqualTo('X');
        assertThat(cd.getCharAt(10,10)).isEqualTo('X');
        assertThat(cd.getCharAt(11,11)).isEqualTo(0);


    }

}