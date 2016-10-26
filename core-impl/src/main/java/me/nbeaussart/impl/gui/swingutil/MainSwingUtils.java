package me.nbeaussart.impl.gui.swingutil;

import me.nbeaussart.engine.model.Color;

import static me.nbeaussart.engine.util.SwingUtils.askForAStringInArrayReturning;
import static me.nbeaussart.engine.util.SwingUtils.askForInListDisplayed;

/**
 * @author Nicolas Beaussart
 * @since 21/10/16
 */
public class MainSwingUtils {

    public static void main(String ... args){
        System.out.println(askForAStringInArrayReturning("Question", "En tête", new Color[]{new Color(10,38,48), new Color(29,20,28)}, "error !", "You shall not work"));
        System.out.println("---");
        System.out.println(askForInListDisplayed("Question", "En tête", new String[]{"Hello 1", "Hello 2", "Hello 3"}, "error !", "YOu shall not work"));
        System.out.println("---");
        System.out.println(askForInListDisplayed("Question", "En tête", "Hello 1", "Hello 2", "Hello 3"));
        System.out.println("---");
    }
}
