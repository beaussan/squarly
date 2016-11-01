package me.nbeaussart.impl.gui.swing;

import me.nbeaussart.engine.util.Frames;
import me.nbeaussart.engine.view.console.JConsole;

import java.awt.*;
import java.util.Random;

/**
 * @author Nicolas Beaussart
 * @since 01/11/16
 */

public class ConsoleApp {

    public static void main(String[] args) {
        JConsole jc=new JConsole(100,40);
        jc.setCursorVisible(true);
        jc.setCursorBlink(true);
        jc.write("Hello World\n");
        jc.write("Hello World\n", Color.BLACK,Color.MAGENTA);
        jc.write("Hello World\n",Color.GREEN,Color.BLACK);

        System.out.println("Normal output");
        jc.captureStdOut();
        System.out.println("Captured output");


        // brown box
        jc.fillArea(' ', Color.WHITE, new Color(100,70,30), 20, 20, 3, 3);

        jc.setCursorPos(0, 0);

        Frames.display(jc,"JConsole test application");

        int SECS=3;
        long start=System.currentTimeMillis();
        int iterations=0;
        Random r = new Random();

        while (start>(System.currentTimeMillis()-1000*SECS)) {
            for (int y=10; y<20; y++) {
                for (int x=10; x<80; x++) {
                    jc.fillArea((char)r.nextInt(256),
                            new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256)),
                            new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256)),
                            x, y, 1, 1);
                }
            }
            jc.repaint();
            iterations++;
        }

        jc.setCursorPos(0, 6);
        System.out.println("FPS="+iterations/SECS);
    }

}
