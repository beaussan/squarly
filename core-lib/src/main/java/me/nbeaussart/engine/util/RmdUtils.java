package me.nbeaussart.engine.util;

import java.util.Collection;
import java.util.Random;

/**
 * @author Nicolas Beaussart
 * @since 31/10/16
 */
public final class RmdUtils {


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
    public static <T> T getRandom(Collection<T> collection){
        int item = new Random().nextInt(collection.size());
        int i = 0;
        for(T obj : collection)
        {
            if (i == item)
                return obj;
            i = i + 1;
        }
        return null;
    }
}
