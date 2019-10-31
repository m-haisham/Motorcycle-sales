package com.cerberus.input.selection;

public class SelectionSeperator implements SelectionItem {

    private String symbol;
    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    private int length;
    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }

    private SelectionSeperator() {
        this.setSymbol("-");
        this.setLength(10);
    }

    public static SelectionSeperator create() {
        return new SelectionSeperator();
    }

    public static SelectionSeperator create(String symbol) {
        SelectionSeperator separator = new SelectionSeperator();

        separator.setSymbol(symbol);

        return separator;
    }

    public static SelectionSeperator create(int length) {
        SelectionSeperator separator = new SelectionSeperator();

        separator.setLength(length);

        return separator;
    }

    public static SelectionSeperator create(String symbol, int length) {
        SelectionSeperator separator = new SelectionSeperator();

        separator.setSymbol(symbol);
        separator.setLength(length);

        return separator;

    }

    String getSeparator() {
        return (new java.lang.String(new char[this.length])).replace("\0", this.symbol);
    }

}
