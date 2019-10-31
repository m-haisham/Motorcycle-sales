package com.cerberus.input;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Menu {

    private int paddingLength = 1;
    public void setPaddingLength(int paddingLength) {
        this.paddingLength = paddingLength;
    }
    public int getPaddingLength() {
        return paddingLength;
    }

    private String failedMessage = "Unknown action, try again!";
    public void setFailedMessage(String failedMessage) {
        this.failedMessage = failedMessage;
    }
    public String getFailedMessage() {
        return failedMessage;
    }

    protected String constructString(String character, int length) {
        return (new String(new char[length])).replace("\0", character);
    }

    protected String padding() {
        return constructString(" ", paddingLength);
    }

    /**
     * Show and ask and execute appropriate function
     */
    public void prompt() {
        throw new NotImplementedException();
    }
}
