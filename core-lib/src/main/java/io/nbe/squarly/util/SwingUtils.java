package io.nbe.engine.util;

import javax.swing.*;

/**
 * The swing utilities class
 * @author Nicolas Beaussart
 * @since 21/10/16
 */
public final class SwingUtils {
    private SwingUtils(){}

    /**
     * Ask in a swing gui for something in array
     * @param question the question to ask
     * @param header the header of the box
     * @param options the options the user can chose from
     * @param errorMessage the error message when something is wrong
     * @param errorHeader the error message header box when something is wrong
     * @param <T> the type in the array
     * @return the value found
     */
    public static <T> T askForAStringInArrayReturning(String question, String header, T[] options, String errorMessage, String errorHeader) {
        return options[askForAStringInArray(question, header, options, errorMessage, errorHeader)];
    }

    /**
     * Ask in a swing gui for something in array
     * @param question the question to ask
     * @param header the header of the box
     * @param options the options the user can chose from
     * @param <T> the type in the array
     * @return the value found
     */
    @SafeVarargs
    public static <T> T askForAStringInArrayReturning(String question, String header, T ...options) {
        return options[askForAStringInArray(question, header, options, "Vous devez faire un choix", "Erreur choix")];
    }

    /**
     * Ask in a swing gui for something in array
     * @param question the question to ask
     * @param header the header of the box
     * @param options the options the user can chose from
     * @param errorMessage the error message when something is wrong
     * @param errorHeader the error message header box when something is wrong
     * @return the value found
     */
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

    /**
     * Ask in a swing gui for a string in array
     * @param question the question to ask
     * @param header the header of the box
     * @param options the options the user can chose from
     * @return the value found
     */
    public static String askForInListDisplayed(String question, String header, String ... options){
        return askForInListDisplayed(question, header, options, "Vous devez choisir une posibiltié !", "Erreur choix");
    }

    /**
     * Ask in a swing gui for something in array
     * @param question the question to ask
     * @param header the header of the box
     * @param options the options the user can chose from
     * @param errorMessage the error message when something is wrong
     * @param errorHeader the error message header box when something is wrong
     * @return the value found
     */
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


    /**
     * Ask in a swing gui for a yes no question
     * @param question the question to ask
     * @param header the header of the box
     * @param options the options the user can chose from
     * @param errorMessage the error message when something is wrong
     * @param errorHeader the error message header box when something is wrong
     * @return the value found
     */
    public static boolean askForBoolean(String question, String header, Object[] options, String errorMessage, String errorHeader) {
        return askForAStringInArray(question, header, options, errorMessage, errorHeader ) == JOptionPane.OK_OPTION;
    }

    /**
     * Ask for a swing gui for a yes no question
     * @param question the question to ask
     * @param header the header of the box
     * @param errorMessage the error message when something is wrong
     * @param errorHeader the error message header box when something is wrong
     * @return the value found
     */
    public static boolean askForBoolean(String question, String header, String errorMessage, String errorHeader) {
        return askForBoolean(question, header, new Object[]{"Oui", "Non"}, errorMessage, errorHeader);
    }


    /**
     * Ask for a swing gui for a yes no question
     * @param question the question to ask
     * @param header the header of the box
     * @return the value found
     */
    public static boolean askForBoolean(String question, String header) {
        return askForBoolean(question, header, new Object[]{"Oui", "Non"});
    }

    /**
     * Ask for a swing gui for a yes no question
     * @param question the question to ask
     * @param header the header of the box
     * @param options the options the user can chose from
     * @return the value found
     */
    public static boolean askForBoolean(String question, String header, Object[] options) {
        return askForBoolean(question, header, options, "Vous devez faire un choix !", "Erreur de choix");
    }
}
