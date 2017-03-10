package io.nbe.squarly.model.interfaces;

import io.nbe.squarly.model.Color;

/**
 * Interface used to represent a character, by the character itself and its background and foreground color.
 *
 * @author Nicolas Beaussart
 * @since 01/11/16
 */
public interface ICharacterSquare {

    /**
     * @return the representing character
     */
    char getChar();

    /**
     * @return the background color
     */
    Color getBackground();

    /**
     * @return the foreground color
     */
    Color getForeground();
}
