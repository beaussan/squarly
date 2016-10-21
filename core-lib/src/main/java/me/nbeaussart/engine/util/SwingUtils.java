package me.nbeaussart.engine.util;

import javax.swing.*;

/**
 * @author Nicolas Beaussart
 * @since 21/10/16
 */
public final class SwingUtils {
    private SwingUtils(){}


    public static <T> T askForAStringInArrayReturning(String question, String header, T[] options, String errorMessage, String errorHeader) {
        return options[askForAStringInArray(question, header, options, errorMessage, errorHeader)];
    }
    public static <T> T askForAStringInArrayReturning(String question, String header, T     ...options) {
        return options[askForAStringInArray(question, header, options, "Vous devez faire un choix", "Erreur choix")];
    }

    public static String askForInListDisplayed(String question, String header, String[] options, String errorMessage, String errorHeader){
        String res = "";

        while (res == null || res.isEmpty()){
            res = (String)JOptionPane.showInputDialog(null,
                    question,
                    header,
                    JOptionPane.PLAIN_MESSAGE,
                    null,     //do not use a custom Icon
                    options,  //the titles of buttons
                    options[0]); //default button title
            if (res == null || res.isEmpty()){
                JOptionPane.showMessageDialog(null,
                        errorMessage,
                        errorHeader,
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        return res;
    }

    public static String askForInListDisplayed(String question, String header, String ... options){
        return askForInListDisplayed(question, header, options, "Vous devez choisir une posibiltié !", "Erreur choix");
    }

    public static int askForAStringInArray(String question, String header, Object[] options, String errorMessage, String errorHeader) {
        int res = JOptionPane.CLOSED_OPTION;

        while (res == JOptionPane.CLOSED_OPTION){
            res = JOptionPane.showOptionDialog(null,
                    question,
                    header,
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,     //do not use a custom Icon
                    options,  //the titles of buttons
                    options[0]); //default button title
            if (res == JOptionPane.CLOSED_OPTION){
                JOptionPane.showMessageDialog(null,
                        errorMessage,
                        errorHeader,
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        return res;
    }

    public static boolean askForBoolean(String question, String header, Object[] options, String errorMessage, String errorHeader) {

        return askForAStringInArray(question, header, options, errorMessage, errorHeader ) == JOptionPane.OK_OPTION;
    }
    public static boolean askForBoolean(String question, String header, String errorMessage, String errorHeader) {
        return askForBoolean(question, header, new Object[]{"Oui", "Non"}, errorMessage, errorHeader);
    }
    public static boolean askForBoolean(String question, String header) {
        return askForBoolean(question, header, new Object[]{"Oui", "Non"});
    }
    public static boolean askForBoolean(String question, String header, Object[] options) {
        return askForBoolean(question, header, options, "Vous devez faire un choix !", "Erreur de choix");
    }
}
