package com.cerberus.models.helpers.string;

import com.cerberus.models.helpers.StringHelper;

public class SidedLine {

    private int width;
    private String indentStart;
    private String indentEnd;
    private String leading;
    private String trailing;

    private boolean endLine = true;

    public SidedLine(int width, String leading, String trailing, String indent) {
        this.width = width;
        this.indentStart = indent;
        this.indentEnd = indent;

        this.leading = leading;
        this.trailing = trailing;
    }

    public SidedLine(int width, String leading, String trailing) {
        this.width = width;
        this.indentStart = "  ";
        this.indentEnd = "  ";

        this.leading = leading;
        this.trailing = trailing;
    }

    public SidedLine(int width, String leading, String trailing, String indentStart, String indentEnd) {
        this.width = width;
        this.indentStart = indentStart;
        this.indentEnd = indentEnd;

        this.leading = leading;
        this.trailing = trailing;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        String lead = indentStart + leading;
        String trail = trailing + indentEnd;
        String spacer = StringHelper.create(" ", this.width - lead.length() - trail.length());

        builder.append(lead).append(spacer).append(trail);

        if (endLine) {
            builder.append("\n");
        }

        return builder.toString();

    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getLeading() {
        return leading;
    }

    public void setLeading(String leading) {
        this.leading = leading;
    }

    public String getTrailing() {
        return trailing;
    }

    public void setTrailing(String trailing) {
        this.trailing = trailing;
    }

    public void setEndLine(boolean endLine) {
        this.endLine = endLine;
    }
}
