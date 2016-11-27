package io.nbe.squarly.util;

import java.util.Collection;
import java.util.Random;

/**
 * Some random utilities functions
 *
 * @author Nicolas Beaussart
 * @since 31/10/16
 */
public final class RmdUtils {
    private RmdUtils() {

    }

    /**
     * Get a random item from a collection
     * @param collection the collection to get from
     * @param r the random generator
     * @param <T> the type from the collection
     * @return item found
     */
    public static <T> T getRandom(Collection<T> collection, Random r){
        if (collection == null || collection.isEmpty()){
            return null;
        }
        int item;
        item = r.nextInt(collection.size());
        int i = 0;
        T found = null;
        for(T obj : collection)
        {
            if (i == item) {
                found = obj;
                break;
            }
            i = i + 1;
        }
        return found;
    }

    /**
     * Generate a random instance from a string
     * @param string the seed
     * @return the random instance
     */
    public static Random getRandomFromString(String string){
        return new Random(String.valueOf(string).hashCode());
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
