package me.nbeaussart.game.map;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import me.nbeaussart.engine.model.Color;

/**
 * Created by beaussan on 01/11/16.
 */
public class SquareContent {

    private final char charData;
    private final Color background;
    private final Color foreground;

    public SquareContent(char charData, Color background, Color foreground) {
        this.charData = charData;
        this.background = Preconditions.checkNotNull(background);
        this.foreground = Preconditions.checkNotNull(foreground);
    }

    public char getCharData() {
        return charData;
    }

    public Color getBackground() {
        return background;
    }

    public Color getForeground() {
        return foreground;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("charData", charData)
                .add("background", background)
                .add("foreground", foreground)
                .toString();
    }
}
