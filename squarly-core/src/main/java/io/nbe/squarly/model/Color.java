package io.nbe.squarly.model;

import com.google.common.base.MoreObjects;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Color class, basic rgb color system
 *
 * @author Nicolas Beaussart
 * @since 12/10/16
 */
public class Color {
    private final int red;
    private final int green;
    private final int blue;
    private final java.awt.Color awt;

    public static final double FACTOR = 0.7;

    @Deprecated
    public static Color from(java.awt.Color awt){
        return new Color(awt);
    }



    public Color(java.awt.Color awt){
        this.red = checkNotNull(awt).getRed();
        this.green = awt.getGreen();
        this.blue = awt.getBlue();
        this.awt = awt;
    }

    public Color(int red, int green, int blue) {
        checkArgument(0 <= red && red <= 255, "Red should be between 0 and 255");
        checkArgument(0 <= green && green <= 255, "Green should be between 0 and 255");
        checkArgument(0 <= blue && blue <= 255, "Blue should be between 0 and 255");

        this.red = red;
        this.green = green;
        this.blue = blue;
        awt = new java.awt.Color(red, green, blue);
    }

    public Color darker() {
        return new Color(Math.max((int)(getRed()  *FACTOR), 0),
                Math.max((int)(getGreen()*FACTOR), 0),
                Math.max((int)(getBlue() *FACTOR), 0));
    }

    public Color brighter() {
        int r = getRed();
        int g = getGreen();
        int b = getBlue();

        /*
         * 1. black.brighter() should return grey
         * 2. applying brighter to blue will always return blue, brighter
         * 3. non pure color (non zero rgb) will eventually return white
         */
        int i = (int)(1.0/(1.0-FACTOR));
        if ( r == 0 && g == 0 && b == 0) {
            return new Color(i, i, i);
        }
        if ( r > 0 && r < i ) r = i;
        if ( g > 0 && g < i ) g = i;
        if ( b > 0 && b < i ) b = i;

        return new Color(Math.min((int)(r/FACTOR), 255),
                Math.min((int)(g/FACTOR), 255),
                Math.min((int)(b/FACTOR), 255));
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("red", red)
                .add("green", green)
                .add("blue", blue)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Color color = (Color) o;
        return red == color.red &&
                green == color.green &&
                blue == color.blue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(red, green, blue);
    }

    public java.awt.Color getAwt() {
        return awt;
    }
}
