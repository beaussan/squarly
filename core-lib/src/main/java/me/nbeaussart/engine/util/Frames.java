package me.nbeaussart.engine.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Nicolas Beaussart
 * @since 01/11/16
 */
public class Frames {

    private static final HashMap<String,JFrame> frames= new HashMap<>();

    /**
     * Create a frame or get an existing frame with the specified title
     * @param title
     * @return
     */
    public static JFrame createFrame(String title) {
        JFrame f=frames.get(title);

        if (f==null) {
            f=new JFrame(title);
            frames.put(title,f);

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
