package io.nbe.squarly.util;

import java.util.Objects;

import static com.google.common.base.Objects.*;

/**
 * @author Nicolas Beaussart
 * @param <K> the value type
 * @param <V> the key type
 */
public class Pair<K, V> {
    private K key;
    private V value;

    /**
     * Create a pair data with they key and value
     * @param key the key to store
     * @param value the value to store
     */
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.key + "=" + this.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return equal(key, pair.key) &&
                equal(value, pair.value);
    }
}
