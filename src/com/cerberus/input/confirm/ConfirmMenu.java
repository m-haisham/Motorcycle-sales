package com.cerberus.input.confirm;

import com.cerberus.input.Callable;
import com.cerberus.input.Menu;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Scanner;

/**
 * Simple Confirmation menu
 */
public class ConfirmMenu extends Menu {

    private String question;
    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }

    private String hint;
    public String getHint() {
        return hint;
    }
    public void setHint(String hint) {
        this.hint = hint;
    }

    private Callable<Boolean> actionYes = () -> { throw new NotImplementedException(); };
    public void setActionYes(Callable<Boolean> actionYes) {
        this.actionYes = actionYes;
    }

    private Callable<Boolean> actionNo = null;
    public void setActionNo(Callable<Boolean> actionNo) {
        this.actionNo = actionNo;
    }

    private ConfirmMenu() {
        // set defaults
        this.setHint("(Y/n)");
        this.setQuestion("Are you sure?");
    }

    public static ConfirmMenu create(String question, Callable<Boolean> actionYes) {
        ConfirmMenu menu = new ConfirmMenu();

        menu.setQuestion(question);
        menu.setActionYes(actionYes);

        return menu;
    }

    public static ConfirmMenu create(String question, Callable<Boolean> actionYes, Callable<Boolean> actionNo) {
        ConfirmMenu menu = new ConfirmMenu();

        menu.setQuestion(question);
        menu.setActionYes(actionYes);
        menu.setActionNo(actionNo);

        return menu;
    }

    public static ConfirmMenu create(String question) {
        ConfirmMenu menu = new ConfirmMenu();

        menu.setQuestion(question);

        return menu;
    }

    public void prompt() {
        Scanner scan = new Scanner(System.in);

        System.out.print(this.getQuestion() + " " + this.getHint() + ": ");

        String response = scan.nextLine();

        int parsedResponse = this.parseResponse(response);

        switch (parsedResponse) {

            case 1: // yes
                this.actionYes.call();
                break;

            case 0: // no
                try {
                    this.actionNo.call();
                } catch (NullPointerException e) {}
                break;

            default: // don't know
                System.out.println(this.getFailedMessage());
                this.prompt();
                break;
        }

    }

    private int parseResponse(String response) {
        if (response.equalsIgnoreCase("y")) return 1;
        else if(response.equalsIgnoreCase("n")) return 0;
        else return -1;
    }

}
