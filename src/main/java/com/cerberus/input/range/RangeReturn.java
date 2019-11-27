package com.cerberus.input.range;

class RangeReturn {
    private boolean valid;
    private int value;

    RangeReturn(boolean valid, int value) {
        this.valid = valid;
        this.value = value;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
