package io.nbe.squarly.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Stream utility classes
 *
 * @author Nicolas Beaussart
 * @since 31/10/16
 */
public class StreamUtils {

    /**
     * filter stream for distinct by key
     * @param keyExtractor the lambda key condition
     * @param <T> the type of stream
     * @return the predicate for the filter stream
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
