package io.nbe.engine.model;

import com.google.common.base.MoreObjects;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

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

    public static Color from(java.awt.Color awt){
        return new Color(awt.getRed(), awt.getGreen(), awt.getBlue());
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
        return from(awt.darker());
    }

    public Color brighter() {
        return from(awt.brighter());
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
