package io.nbe.game.map;

import com.google.common.base.MoreObjects;
import io.nbe.squarly.model.Color;

/**
 * Created by beaussan on 01/11/16.
 */
public class SquareContent {

    private final char charData;
    private final Color background;
    private final Color foreground;

    public SquareContent(char charData, Color background, Color foreground) {
        this.charData = charData;
        this.background = background;
        this.foreground = foreground;
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


    public SquareContent getOverlaping(SquareContent over){
        if (over == null){
            return this;
        }
        return new SquareContent(over.charData, (over.background == null) ? background : over.background, (over.foreground == null) ? foreground : over.foreground);
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
