package io.nbe.squarly.util;

import javax.swing.*;
import java.util.HashMap;

/**
 * Frame for swing utilities class
 *
 * @author Nicolas Beaussart
 * @since 01/11/16
 */
public class Frames {

    private static final HashMap<String,JFrame> currentFrames = new HashMap<>();

    /**
     * Create a frame or get an existing frame with the specified title
     * @param title the title of the frame
     * @return the frame created
     */
    public static JFrame createFrame(String title) {
        JFrame f= currentFrames.get(title);

        if (f==null) {
            f=new JFrame(title);
            currentFrames.put(title,f);

            f.setVisible(true);
            f.pack();

            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        } else {
            f.getContentPane().removeAll();
            if (!f.isVisible()) {
                f.setVisible(true);
            } else {
                f.validate();
            }
            f.repaint();
        }

        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        return f;
    }

    /**
     * Shows a frame, making it visible as required
     */
    public static void showFrame(JFrame f) {
        if (!f.isVisible()) f.setVisible(true);
        f.pack();
    }

    /**
     * Display a component in a new standard frame
     */
    public static JFrame display(final JComponent component) {
        JFrame f=createFrame("View Popup");

        f.getContentPane().add(component);
        showFrame(f);

        return f;
    }

    /**
     * Display a component in a new standard frame with the given title
     */
    public static JFrame display(final JComponent component, String title) {
        JFrame f=createFrame(title);
        f.getContentPane().add(component);
        showFrame(f);
        return f;
    }


}
