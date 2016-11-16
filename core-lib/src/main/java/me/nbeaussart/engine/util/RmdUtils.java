package me.nbeaussart.engine.util;

import java.util.Collection;
import java.util.Random;

/**
 * Some random utilities functions
 *
 * @author Nicolas Beaussart
 * @since 31/10/16
 */
public final class RmdUtils {

    /**
     * Get a random item from a collection
     * @param collection the collection to get from
     * @param r the random generator
     * @param <T> the type from the collection
     * @return item found
     */
    public static <T> T getRandom(Collection<T> collection, Random r){
        int item = r.nextInt(collection.size()); // In real life, the Random object should be rather more shared than this
        int i = 0;
        for(T obj : collection)
        {
            if (i == item)
                return obj;
            i = i + 1;
        }
        return null;
    }

    /**
     * Get a random item from a collection using default generator
     * @param collection the collection to get from
     * @param <T> the type from the collection
     * @return item found
     */
    public static <T> T getRandom(Collection<T> collection){
        return getRandom(collection, new Random());
    }
}
