package com.cerberus.input.selection;

import com.cerberus.input.Callable;

public class SelectionOption implements SelectionItem {

    /**
     * Text to display in Menu
     */
    private String label;
    public String getLabel() {
        return this.label;
    }
    public void setLabel(String label) {
        this.label = label;
    }

    private String[] matchers = new String[] {};
    public String[] getMatchers() {
        return matchers;
    }
    public void setMatchers(String[] matchers) {
        this.matchers = new String[matchers.length];
        for (int i = 0; i < matchers.length; i++) {
            this.matchers[i] = matchers[i].toLowerCase();
        }
    }
    /**
     * Function to be called when item is selected
     */
    private Callable<Void> action;
    public Callable<Void> getAction() {
        return this.action;
    }

    public void setAction(Callable<Void> action) {
        this.action = action;
    }

    protected SelectionOption(String label, Callable<Void> action) {
        this.label = label;
        this.action = action;
    }

    public static SelectionOption create(String label, Callable<Void> action) {
        return new SelectionOption(label, action);
    }

    public static SelectionOption create(String label, Callable<Void> action, String[] matchers) {
        SelectionOption option = new SelectionOption(label, action);
        option.setMatchers(matchers);
        return option;
    }
}
