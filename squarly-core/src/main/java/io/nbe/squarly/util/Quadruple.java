package io.nbe.squarly.util;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * A simple quadruple value store
 * @param <A> the value 1 type
 * @param <B> the value 2 type
 * @param <C> the value 3 type
 * @param <D> the value 4 type
 * @author Nicolas Beaussart
 */
public class Quadruple<A,B,C,D> {
    private final A val1;
    private final B val2;
    private final C val3;
    private final D val4;

    /**
     * Create a quadruple from four value
     * @param val1 value 1
     * @param val2 value 2
     * @param val3 value 3
     * @param val4 value 4
     */
    public Quadruple(A val1, B val2, C val3, D val4) {
        this.val1 = val1;
        this.val2 = val2;
        this.val3 = val3;
        this.val4 = val4;
    }

    public A getVal1() {
        return val1;
    }

    public B getVal2() {
        return val2;
    }

    public C getVal3() {
        return val3;
    }

    public D getVal4() {
        return val4;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Quadruple<?, ?, ?, ?> quadruple = (Quadruple<?, ?, ?, ?>) o;
        return Objects.equal(val1, quadruple.val1) &&
                Objects.equal(val2, quadruple.val2) &&
                Objects.equal(val3, quadruple.val3) &&
                Objects.equal(val4, quadruple.val4);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(val1, val2, val3, val4);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("val1", val1)
                .add("val2", val2)
                .add("val3", val3)
                .add("val4", val4)
                .toString();
    }


}
