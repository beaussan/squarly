package io.nbe.squarly.model;

import com.google.common.base.MoreObjects;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The Color class is used to represent the basic rgb color system
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

    /**
     * Generating a color from a java.awt.Color
     * @param awt the color to build from
     */
    public Color(java.awt.Color awt){
        this.red = checkNotNull(awt).getRed();
        this.green = awt.getGreen();
        this.blue = awt.getBlue();
        this.awt = awt;
    }

    /**
     * Create a Color from rgv value
     * @param red the value between 0 and 255
     * @param green the value between 0 and 255
     * @param blue the value between 0 and 255
     */
    public Color(int red, int green, int blue) {
        checkArgument(0 <= red && red <= 255, "Red should be between 0 and 255");
        checkArgument(0 <= green && green <= 255, "Green should be between 0 and 255");
        checkArgument(0 <= blue && blue <= 255, "Blue should be between 0 and 255");

        this.red = red;
        this.green = green;
        this.blue = blue;
        awt = new java.awt.Color(red, green, blue);
    }

    /**
     * Create a darker color from this color according to the FACTOR
     * @return the result
     */
    public Color darker() {
        return new Color(Math.max((int)(getRed()  *FACTOR), 0),
                Math.max((int)(getGreen()*FACTOR), 0),
                Math.max((int)(getBlue() *FACTOR), 0));
    }

    /**
     * Create a brigther color from this color according to the FACTOR
     * @return the result
     */
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
        r = brighterHelper(i, r);
        g = brighterHelper(i, g);
        b = brighterHelper(i, b);

        return new Color(Math.min((int)(r/FACTOR), 255),
                Math.min((int)(g/FACTOR), 255),
                Math.min((int)(b/FACTOR), 255));
    }

    /**
     * Check if a color is upper than the reference to keep the reference otherwise it return the input color
     * @param ref reference semi brighter from factor
     * @param real the input color
     * @return the brighter's color
     */
    private int brighterHelper(int ref, int real){
        if (real > 0 && real < ref){
            return ref;
        } else {
            return real;
        }
    }

    /**
     * @return the red component between 0 and 255
     */
    public int getRed() {
        return red;
    }

    /**
     * @return the green component between 0 and 255
     */
    public int getGreen() {
        return green;
    }

    /**
     * @return the blue component betwenn 0 and 255
     */
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Color color = (Color) o;
        return red == color.red &&
                green == color.green &&
                blue == color.blue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(red, green, blue);
    }

    /**
     * @return the awt.Color format of the color
     */
    public java.awt.Color getAwt() {
        return awt;
    }
}
