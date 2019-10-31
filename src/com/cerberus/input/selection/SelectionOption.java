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

    /**
     * Function to be called when item is selected
     */
    private Callable<Boolean> action;
    public Callable<Boolean> getAction() {
        return this.action;
    }
    public void setAction(Callable<Boolean> action) {
        this.action = action;
    }

    protected SelectionOption(String label, Callable<Boolean> action) {
        this.label = label;
        this.action = action;
    }

    public static SelectionOption create(String label, Callable<Boolean> action) {
        return new SelectionOption(label, action);
    }

}
