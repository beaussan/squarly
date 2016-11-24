package io.nbe.squarly.util;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;


import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author Nicolas Beaussart
 * @since 24/11/16
 */
public class RmdUtilsTest {

    private List<String> values;

    @Before
    public void setUp() throws Exception {
        values = new ArrayList<String>(){{
            add("0");
            add("1");
            add("2");
            add("3");
        }};

    }

    @Test
    public void getRandom() throws Exception {
        int seed = 39;
        Random r = new Random(seed);
        Random r2 = new Random(seed);

        int i = r2.nextInt(values.size());
        assertThat(RmdUtils.getRandom(values, r)).isEqualTo(""+i);

    }

    @Test
    public void getRandom1() throws Exception {
        Map<String , Boolean> data =  values.stream().collect(Collectors.toMap(o -> o, o -> false));
        for (int i = 0; i < values.size()*300; i++) {
            String fond = RmdUtils.getRandom(values);
            data.put(fond, true);
            if (data.values().contains(true) && !data.values().contains(false)){
                System.out.println(data);
                break;
            }
        }
        assertThat(data.values().contains(false)).isFalse();
    }

}