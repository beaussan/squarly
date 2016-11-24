package io.nbe.squarly.view.console;

import javax.swing.JComponent;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.io.PrintStream;
import java.util.Arrays;

/**
 * Class implementing a Swing-based text console
 *
 * Principles:
 * - provides a fixed number of rows and columns, but can be resized
 * - each cell can have its own foreground and background colour
 * - The main font determines the grid size
 *
 * @author Nicolas Beaussart
 * @since 01/11/16
 *
 */
public class JConsole extends JComponent implements HierarchyListener {
    private static final long serialVersionUID = 3571518591759968333L;

    private static final Color DEFAULT_FOREGROUND = Color.LIGHT_GRAY;
    private static final Color DEFAULT_BACKGROUND = Color.BLACK;
    private static final Font DEFAULT_FONT = new Font("Courier New", Font.PLAIN, 18);
    private static final int DEFAULT_BLINKRATE = 200;
    private static final boolean DEFAULT_BLINK_ON = false;

    private volatile ConsoleData data = new ConsoleData();

    private int fontWidth;
    private int fontHeight;
    private int fontYOffset;

    private boolean cursorVisible = false;
    private boolean cursorBlinkOn = true;
    private boolean cursorInverted = true;

    private int cursorX = 0;
    private int cursorY = 0;
    private Font mainFont = null;
    private Font currentFont = null;
    private Color currentForeground = DEFAULT_FOREGROUND;
    private Color currentBackground = DEFAULT_BACKGROUND;

    private Timer blinkTimer;

    /**
     * Create a JConsole
     * @param columns the number of columns to draw
     * @param rows the number of rows to draw
     */
    public JConsole(int columns, int rows) {
        setMainFont(DEFAULT_FONT);
        setCurrentFont(mainFont);
        init(columns, rows);
        if (DEFAULT_BLINK_ON) {
            setCursorBlink(true);
        }
    }

    /**
     * Sets the main font of the console, which is used to determine the size of
     * characters
     *
     * @param font
     */
    public void setMainFont(Font font) {
        mainFont = font;

        FontRenderContext fontRenderContext = new FontRenderContext(
                mainFont.getTransform(), false, false);
        Rectangle2D charBounds = mainFont.getStringBounds("X",
                fontRenderContext);
        fontWidth = (int) charBounds.getWidth();
        fontHeight = (int) charBounds.getHeight();
        fontYOffset = -(int) charBounds.getMinY();

        setPreferredSize(new Dimension(data.getColumns() * fontWidth, data.getRows() * fontHeight));

        repaint();
    }

    private void stopBlinking() {
        if (blinkTimer != null) {
            blinkTimer.stop();
            cursorInverted = true;
        }
    }

    private void startBlinking() {
        getTimer().start();
    }

    public void setCursorBlink(boolean blink) {
        if (blink) {
            cursorBlinkOn = true;
            startBlinking();
        } else {
            cursorBlinkOn = false;
            stopBlinking();
        }
    }

    public void setBlinkDelay(int millis) {
        getTimer().setDelay(millis);
    }

    private Timer getTimer() {
        if (blinkTimer == null) {
            blinkTimer = new Timer(DEFAULT_BLINKRATE, e -> {
                if (cursorBlinkOn && isShowing()) {
                    cursorInverted = !cursorInverted;
                    repaintArea(getCursorX(), getCursorY(), 1, 1);
                } else {
                    stopBlinking();
                }
            });
            blinkTimer.setRepeats(true);
            if (cursorBlinkOn) {
                startBlinking();
            }
        }
        return blinkTimer;
    }

    @Override
    public void addNotify() {
        super.addNotify();
        addHierarchyListener(this);
    }

    @Override
    public void removeNotify() {
        removeHierarchyListener(this);
        super.removeNotify();
    }

    public void setRows(int rows) {
        throw new UnsupportedOperationException();
    }

    public void setCurrentFont(Font f) {
        currentFont = f;
    }

    public void setCursorVisible(boolean visible) {
        cursorVisible = visible;
    }

    public int getRows() {
        return data.getRows();
    }

    public void setColumns(int columns) {
        throw new UnsupportedOperationException();
    }

    public int getColumns() {
        return data.getColumns();
    }

    public int getFontWidth() {
        return fontWidth;
    }

    public int getFontHeight() {
        return fontHeight;
    }

    /**
     *
     * Fires a repaint event on a specified rectangle of characters in the
     * console
     *
     * @param column the column index to start with
     * @param row the row index to start with
     * @param width the width to repaint
     * @param height the height to repaint
     */
    public void repaintArea(int column, int row, int width, int height) {
        int fw = getFontWidth();
        int fh = getFontHeight();
        repaint(column * fw, row * fh, width * fw, height * fh);
    }

    /**
     * Initialises the console to a specified size
     */
    protected void init(int columns, int rows) {
        data.init(columns, rows);
        Arrays.fill(data.getBackground(), DEFAULT_BACKGROUND);
        Arrays.fill(data.getForeground(), DEFAULT_FOREGROUND);
        Arrays.fill(data.getFont(), DEFAULT_FONT);
        Arrays.fill(data.getText(), ' ');

        setPreferredSize(new Dimension(columns * fontWidth, rows * fontHeight));
    }

    /**
     * Clears the JConsole
     */
    public void clear() {
        clearArea(0, 0, data.getColumns(), data.getRows());
    }

    /**
     * Reset the cursor position
     */
    public void resetCursor() {
        repaintArea(cursorX, cursorY, 0, 0);
        cursorX = 0;
        cursorY = 0;
        repaintArea(cursorX, cursorY, 0, 0);
    }

    /**
     * Clears the screen and reset the cursor
     */
    public void clearScreen() {
        clear();
        resetCursor();
    }

    private void clearArea(int column, int row, int width, int height) {
        data.fillArea(' ', currentForeground, currentBackground, currentFont,
                column, row, width, height);
        repaintArea(0, 0, width, height);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        Rectangle r = g.getClipBounds();

        // AffineTransform textTransform=new AffineTransform();
        // textTransform.scale(fontWidth, fontHeight);
        // g.setTransform(textTransform);

        // calculate x and y range to redraw
        int x1 = (int) (r.getMinX() / fontWidth);
        int x2 = (int) (r.getMaxX() / fontWidth) + 1;
        int y1 = (int) (r.getMinY() / fontWidth);
        int y2 = (int) (r.getMaxY() / fontWidth) + 1;

        int curX = getCursorX();
        int curY = getCursorY();

        for (int j = Math.max(0, y1); j < Math.min(y2, data.getRows()); j++) {
            int offset = j * data.getColumns();
            int start = Math.max(x1, 0);
            int end = Math.min(x2, data.getColumns());

            while (start < end) {
                Color nfg = data.getForeground()[offset + start];
                Color nbg = data.getBackground()[offset + start];
                Font nf = data.getFont()[offset + start];

                // index of ending position
                int i = start + 1;

                if ((j == curY) && (start == curX)) {
                    if (cursorVisible && cursorBlinkOn && cursorInverted) {
                        // swap foreground and background colours
                        Color t = nfg;
                        nfg = nbg;
                        nbg = t;
                    }
                } else {
                    // detect run
                    while ((i < end) && (!((j == curY) && (i == curX)))
                            && (nfg == data.getForeground()[offset + i])
                            && (nbg == data.getBackground()[offset + i])
                            && (nf == data.getFont()[offset + i])) {
                        i++;
                    }
                }

                // set font
                g.setFont(nf);

                // draw background
                g.setBackground(nbg);
                g.clearRect(fontWidth * start, j * fontHeight, fontWidth
                        * (i - start), fontHeight);

                // draw chars up to this point
                g.setColor(nfg);
                for (int k=start; k<i; k++) {
                    g.drawChars(data.getText(), offset + k, 1, k
                            * fontWidth, j * fontHeight + fontYOffset);
                }
                start = i;
            }
        }
    }

    /**
     * Set the cursor position of the console
     * @param column the column to set
     * @param row the raw to set
     */
    public void setCursorPos(int column, int row) {
        if ((column < 0) || (column >= data.getColumns()))
            throw new IllegalArgumentException("Invalid X cursor position: " + column);
        if ((row < 0) || (row >= data.getRows()))
            throw new IllegalArgumentException("Invalid Y cursor position: " + row);
        cursorX = column;
        cursorY = row;
    }

    public int getCursorX() {
        return cursorX;
    }

    public int getCursorY() {
        return cursorY;
    }

    @Override
    public void setForeground(Color c) {
        currentForeground = c;
    }

    @Override
    public void setBackground(Color c) {
        currentBackground = c;
    }

    @Override
    public Color getForeground() {
        return currentForeground;
    }

    @Override
    public Color getBackground() {
        return currentBackground;
    }

    /**
     * Get the char at
     * @param column the colum
     * @param row the raw
     * @return the char found
     */
    public char getCharAt(int column, int row) {
        return data.getCharAt(column, row);
    }

    /**
     * Get the foreground at
     * @param column the colum
     * @param row the raw
     * @return the color of the foreground
     */
    public Color getForegroundAt(int column, int row) {
        return data.getForegroundAt(column, row);
    }

    public Color getBackgroundAt(int column, int row) {
        return data.getBackgroundAt(column, row);
    }

    public Font getFontAt(int column, int row) {
        return data.getFontAt(column, row);
    }

    /**
     * Redirects System.out to this console by calling System.setOut
     */
    public void captureStdOut() {
        PrintStream ps = new PrintStream(System.out) {
            public void println(String x) {
                writeln(x);
            }
        };

        System.setOut(ps);
    }

    public void write(char c) {
        data.setDataAt(cursorX, cursorY, c, currentForeground,
                currentBackground, currentFont);
        moveCursor(c);
    }

    private void moveCursor(char c) {
        switch (c) {
            case '\n':
                cursorY++;
                cursorX = 0;
                break;
            default:
                cursorX++;
                if (cursorX >= data.getColumns()) {
                    cursorX = 0;
                    cursorY++;
                }
                break;
        }
    }

    public void writeln(String line) {
        write(line);
        write('\n');
    }

    public void write(String string, Color foreGround, Color backGround) {
        Color foreTemp = currentForeground;
        Color backTemp = currentBackground;
        setForeground(foreGround);
        setBackground(backGround);
        write(string);
        setForeground(foreTemp);
        setBackground(backTemp);
    }

    synchronized public void write(String string, Color foreGround, Color backGround, int colums, int rows) {
        setCursorPos(colums, rows);
        Color foreTemp = currentForeground;
        Color backTemp = currentBackground;
        setForeground(foreGround);
        setBackground(backGround);
        write(string);
        setForeground(foreTemp);
        setBackground(backTemp);
    }

    public void write(String string) {
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            write(c);
        }
    }

    public void fillArea(char c, Color fg, Color bg, int column, int row,
                         int width, int height) {
        data.fillArea(c, fg, bg, currentFont, column, row, width, height);
        repaintArea(column, row, width, height);
    }

    @Override
    public void hierarchyChanged(HierarchyEvent e) {
        if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0) {
            if (isShowing()) {
                startBlinking();
            } else {
                stopBlinking();
            }
        }
    }

}
