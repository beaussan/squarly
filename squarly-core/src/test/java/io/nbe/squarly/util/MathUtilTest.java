package io.nbe.squarly.util;

import io.nbe.squarly.model.Cord;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author Nicolas Beaussart
 * @since 24/11/16
 */
public class MathUtilTest {
    @Test
    public void abs() throws Exception {
        assertThat(MathUtil.abs(10)).isEqualTo(10).isEqualTo(MathUtil.abs(-10));
    }

    @Test
    public void bresenhamAlgorithm() throws Exception {
        Cord start = Cord.get(10,10);
        Cord end = Cord.get(13,13);

        assertThat(MathUtil.bresenhamAlgorithm(start, end))
                .containsExactly(
                        Cord.get(10,10),
                        Cord.get(11,11),
                        Cord.get(12,12),
                        Cord.get(13,13));
    }
    @Test
    public void bresenhamAlgorithm2() throws Exception {
        Cord start = Cord.get(10,10);
        Cord end = Cord.get(13,10);

        assertThat(MathUtil.bresenhamAlgorithm(start, end))
                .containsExactly(
                        Cord.get(10,10),
                        Cord.get(11,10),
                        Cord.get(12,10),
                        Cord.get(13,10));
    }

    @Test
    public void bresenhamAlgorithm3() throws Exception {
        Cord start = Cord.get(10,10);
        Cord end = Cord.get(10,13);

        assertThat(MathUtil.bresenhamAlgorithm(start, end))
                .containsExactly(
                        Cord.get(10,10),
                        Cord.get(10,11),
                        Cord.get(10,12),
                        Cord.get(10,13));
    }

    @Test
    public void bresenhamAlgorithm4() throws Exception {
        Cord start = Cord.get(10,10);
        Cord end = Cord.get(10,7);

        assertThat(MathUtil.bresenhamAlgorithm(start, end))
                .containsExactly(
                        Cord.get(10,10),
                        Cord.get(10,9),
                        Cord.get(10,8),
                        Cord.get(10,7));
    }
    @Test
    public void bresenhamAlgorithm5() throws Exception {
        Cord start = Cord.get(10,10);
        Cord end = Cord.get(7,10);

        assertThat(MathUtil.bresenhamAlgorithm(start, end))
                .containsExactly(
                        Cord.get(10,10),
                        Cord.get(9,10),
                        Cord.get(8,10),
                        Cord.get(7,10));
    }
    @Test
    public void bresenhamAlgorithm6() throws Exception {
        Cord start = Cord.get(10,10);
        Cord end = Cord.get(7,7);

        assertThat(MathUtil.bresenhamAlgorithm(start, end))
                .containsExactly(
                        Cord.get(10,10),
                        Cord.get(9,9),
                        Cord.get(8,8),
                        Cord.get(7,7));
    }
    @Test
    public void bresenhamAlgorithm7() throws Exception {
        final int stCord = 10;
        final int toAdd = 3;

        Cord start = null;
        Cord end = null;

        start = Cord.get(stCord, stCord);
        end = Cord.get(stCord, stCord + toAdd);
        assertThat(MathUtil.bresenhamAlgorithm(start, end))
                .containsExactly(
                        Cord.get(stCord,stCord + toAdd -3),
                        Cord.get(stCord,stCord + toAdd - 2),
                        Cord.get(stCord,stCord + toAdd - 1),
                        Cord.get(stCord,stCord + toAdd));

        start = Cord.get(stCord, stCord);
        end = Cord.get(stCord + toAdd, stCord);
        assertThat(MathUtil.bresenhamAlgorithm(start, end))
                .containsExactly(
                        Cord.get(stCord + toAdd - 3, stCord),
                        Cord.get(stCord + toAdd - 2, stCord),
                        Cord.get(stCord + toAdd - 1, stCord),
                        Cord.get(stCord + toAdd, stCord));
        start = Cord.get(stCord, stCord);
        end = Cord.get(stCord + toAdd, stCord + toAdd);
        assertThat(MathUtil.bresenhamAlgorithm(start, end))
                .containsExactly(
                        Cord.get(stCord + toAdd - 3, stCord + toAdd - 3),
                        Cord.get(stCord + toAdd - 2, stCord + toAdd - 2),
                        Cord.get(stCord + toAdd - 1, stCord + toAdd - 1),
                        Cord.get(stCord + toAdd, stCord + toAdd));
    }
    @Test
    public void bresenhamAlgorithm8() throws Exception {
        final int stCord = 10;
        final int toAdd = -3;

        Cord start = null;
        Cord end = null;

        start = Cord.get(stCord, stCord);
        end = Cord.get(stCord, stCord + toAdd);
        assertThat(MathUtil.bresenhamAlgorithm(start, end))
                .containsExactly(
                        Cord.get(stCord,stCord + toAdd + 3),
                        Cord.get(stCord,stCord + toAdd + 2),
                        Cord.get(stCord,stCord + toAdd + 1),
                        Cord.get(stCord,stCord + toAdd));

        start = Cord.get(stCord, stCord);
        end = Cord.get(stCord + toAdd, stCord);
        assertThat(MathUtil.bresenhamAlgorithm(start, end))
                .containsExactly(
                        Cord.get(stCord + toAdd + 3, stCord),
                        Cord.get(stCord + toAdd + 2, stCord),
                        Cord.get(stCord + toAdd + 1, stCord),
                        Cord.get(stCord + toAdd, stCord));
        start = Cord.get(stCord, stCord);
        end = Cord.get(stCord + toAdd, stCord + toAdd);
        assertThat(MathUtil.bresenhamAlgorithm(start, end))
                .containsExactly(
                        Cord.get(stCord + toAdd + 3, stCord + toAdd + 3),
                        Cord.get(stCord + toAdd + 2, stCord + toAdd + 2),
                        Cord.get(stCord + toAdd + 1, stCord + toAdd + 1),
                        Cord.get(stCord + toAdd, stCord + toAdd));
    }



    @Test
    public void bresenhamAlgorithm9() throws Exception {
        Cord start = Cord.get(10,10);
        Cord end = Cord.get(8,9);
        assertThat(MathUtil.bresenhamAlgorithm(start, end))
                .containsExactly(
                        Cord.get(10,10),
                        Cord.get(9,9),
                        Cord.get(8,9));
    }

}