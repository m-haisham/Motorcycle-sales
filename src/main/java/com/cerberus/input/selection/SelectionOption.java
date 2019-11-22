package com.cerberus.input.selection;

import com.cerberus.input.Callable;
import com.cerberus.input.selection.callers.IntegerCaller;

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
    private IntegerCaller action;
    public IntegerCaller getAction() {
        return this.action;
    }

    public void setAction(IntegerCaller action) {
        this.action = action;
    }

    protected SelectionOption(String label, IntegerCaller action) {
        this.label = label;
        this.action = action;
    }

    public static SelectionOption create(String label, IntegerCaller action) {
        return new SelectionOption(label, action);
    }

    public static SelectionOption create(String label, IntegerCaller action, String[] matchers) {
        SelectionOption option = new SelectionOption(label, action);
        option.setMatchers(matchers);
        return option;
    }
}
