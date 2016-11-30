package io.nbe.squarly.view.console;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.nbe.squarly.util.Quadruple;

import java.awt.*;

/**
 * @author Nicolas Beaussart
 * @since 27/11/16
 */
public class DataChar {

    private final char c;
    private final Color background;
    private final Color foreground;
    private final Font font;


    private static final LoadingCache<Quadruple<Character, Color, Color, Font>, DataChar> cacheGraph = CacheBuilder.newBuilder()
            .build(new CacheLoader<Quadruple<Character, Color, Color, Font>, DataChar>() {
                @Override
                public DataChar load(Quadruple<Character, Color, Color, Font> key) throws Exception {
                    return new DataChar(key);
                }
            });

    private DataChar(Quadruple<Character, Color, Color, Font> key) {
        this.c = key.getVal1();
        this.background = key.getVal2();
        this.foreground = key.getVal3();
        this.font = key.getVal4();
    }

    /**
     * Get a DataChar
     * @param c the char
     * @param background the background
     * @param foreground the foreground
     * @param font the font
     * @return the datachar cached
     */
    public static DataChar get(char c, Color background, Color foreground, Font font){
        return cacheGraph.getUnchecked(new Quadruple<>(c, background, foreground, font));
    }

    /**
     * Get a DataChar
     * @param c the char
     * @param background the background
     * @param foreground the foreground
     * @return the datachar cached
     */
    public static DataChar get(char c, Color background, Color foreground){
        return cacheGraph.getUnchecked(new Quadruple<>(c, background, foreground, null));
    }


    public char getC() {
        return c;
    }

    public Font getFont() {
        return font;
    }

    public Color getBackground() {
        return background;
    }

    public Color getForeground() {
        return foreground;
    }

    /**
     * alter the font of this data and return a new one
     * @param f the new font
     * @return the new Data Char
     */
    public DataChar alterFont(Font f){
        return get(c, background, foreground, f);
    }

    /**
     * alter the char of this data and return a new one
     * @param c the new char
     * @return the new Data Char
     */
    public DataChar alterC(char c){
        return get(c, background, foreground, font);
    }

    /**
     * alter the foreground of this char and return a new one
     * @param color the new foreground
     * @return the new Data Char
     */
    public DataChar alterForeground(Color color){
        return get(c, background, color, font);
    }

    /**
     * alter the backgournd of this char and return a new one
     * @param color the new background
     * @return the new Data Char
     */
    public DataChar alterBackground(Color color){
        return get(c, color, foreground, font);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DataChar dataChar = (DataChar) o;
        return c == dataChar.c &&
                Objects.equal(background, dataChar.background) &&
                Objects.equal(foreground, dataChar.foreground) &&
                Objects.equal(font, dataChar.font);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(c, background, foreground, font);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("c", c)
                .add("font", font)
                .add("background", background)
                .add("foreground", foreground)
                .toString();
    }
}
