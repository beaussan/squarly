package me.nbeaussart.engine.view.console;

import java.awt.*;

/**
 * Class used for storing console data
 * @author Nicolas Beaussart
 * @since 01/11/16
 */

public final class ConsoleData {
    private int capacity = 0;
    public int rows;
    public int columns;
    public Color[] background;
    public Color[] foreground;
    public Font[] font;
    public char[] text;

    ConsoleData() {
        // create empty console data
    }

    private void ensureCapacity(int minCapacity) {
        if (capacity >= minCapacity)
            return;

        char[] newText = new char[minCapacity];
        Color[] newBackground = new Color[minCapacity];
        Color[] newForeground = new Color[minCapacity];
        Font[] newFont = new Font[minCapacity];

        int size = rows * columns;
        if (size > 0) {
            System.arraycopy(text, 0, newText, 0, size);
            System.arraycopy(foreground, 0, newForeground, 0, size);
            System.arraycopy(background, 0, newBackground, 0, size);
            System.arraycopy(font, 0, newFont, 0, size);
        }

        text = newText;
        foreground = newForeground;
        background = newBackground;
        font = newFont;
        capacity = minCapacity;
    }


    void init(int columns, int rows) {
        ensureCapacity(rows * columns);
        this.rows = rows;
        this.columns = columns;
    }

    /**
     * Sets a single character position
     * @param column column to set from
     * @param row raw to set from
     * @param c the char to be set
     * @param fg the foreground to be set
     * @param bg the background to be set
     * @param f the font to be set
     */
    public void setDataAt(int column, int row, char c, Color fg, Color bg,
                          Font f) {
        int pos = column + row * columns;
        text[pos] = c;
        foreground[pos] = fg;
        background[pos] = bg;
        font[pos] = f;
    }

    /**
     * Get the char data at the console
     * @param column column to get from
     * @param row raw to get from
     * @return the char from
     */
    public char getCharAt(int column, int row) {
        int offset = column + row * columns;
        return text[offset];
    }

    /**
     * Get the foreground at the console
     * @param column column to get from
     * @param row raw to get from
     * @return the foreground from
     */
    public Color getForegroundAt(int column, int row) {
        int offset = column + row * columns;
        return foreground[offset];
    }

    /**
     * Get the background at the console
     * @param column column to get from
     * @param row raw to get from
     * @return the background from
     */
    public Color getBackgroundAt(int column, int row) {
        int offset = column + row * columns;
        return background[offset];
    }

    /**
     * Get the font at the console
     * @param column column to get from
     * @param row raw to get from
     * @return the font from
     */
    public Font getFontAt(int column, int row) {
        int offset = column + row * columns;
        return font[offset];
    }

    /**
     * fill a area in the data
     * @param c the char to be set
     * @param fg the foreground to be set
     * @param bg the background to be set
     * @param f the font to be set
     * @param column the column to start from
     * @param row the row to start from
     * @param width the width to set
     * @param height the height to set
     */
    public void fillArea(char c, Color fg, Color bg, Font f, int column,
                         int row, int width, int height) {
        for (int q = Math.max(0, row); q < Math.min(row + height, rows); q++) {
            for (int p = Math.max(0, column); p < Math.min(column + width,
                    columns); p++) {
                int offset = p + q * columns;
                text[offset] = c;
                foreground[offset] = fg;
                background[offset] = bg;
                font[offset] = f;
            }
        }
    }
}