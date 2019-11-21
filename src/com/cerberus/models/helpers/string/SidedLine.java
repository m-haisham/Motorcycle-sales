package com.cerberus.models.helpers.string;

import com.cerberus.models.helpers.StringHelper;

public class SidedLine {

    private int width;
    private String indent;
    private String leading;
    private String trailing;

    private boolean endLine = true;

    public SidedLine(int width, String indent, String leading, String trailing) {
        this.width = width;
        this.indent = indent;

        this.leading = leading;
        this.trailing = trailing;
    }

    public SidedLine(int width, String leading, String trailing) {
        this.width = width;
        this.indent = "  ";

        this.leading = leading;
        this.trailing = trailing;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        String lead = indent + leading;
        String trail = trailing + indent;
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
}
