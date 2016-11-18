package io.nbe.squarly.util;

import com.google.common.base.Objects;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

/**
 * @author Nicolas Beaussart
 * @since 11/11/16
 */
public class StreamUtilsTest {

    public static final int NMB_RUN = 10;
    public static final int NMB_RUNS = 20;

    @Test
    public void distinctByKey() throws Exception {
        List<ItemTest> ls = new ArrayList<>();
        for (int z = 0; z < NMB_RUNS; z++) {

            for (int i = 0; i < NMB_RUN*10; i++) {
                ls.add(new ItemTest(i% NMB_RUN, i+z));
            }
        }
        assertThat(
                ls.parallelStream()
                        .filter(StreamUtils.distinctByKey(itemTest -> itemTest.getId() == NMB_RUN))
                        .collect(Collectors.toList()))
                .hasSize(1);
    }


    private class ItemTest {
        public int id;
        public int rmd;

        public ItemTest(int id, int rmd){
            this.id = id;
            this.rmd = rmd;
        }

        public int getId() {
            return id;
        }

        public int getRmd() {
            return rmd;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ItemTest itemTest = (ItemTest) o;
            return id == itemTest.id &&
                    rmd == itemTest.rmd;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id, rmd);
        }
    }

}