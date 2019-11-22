package com.cerberus.input.range;

import com.cerberus.input.Menu;

import java.util.Scanner;

public class RangeMenu extends Menu {

    private Scanner scan;

    private String message;

    private int min;
    public int getMin() {
        return min;
    }
    public void setMin(int min) {
        this.min = min;
    }

    private int max;
    public int getMax() {
        return max;
    }
    public void setMax(int max) {
        this.max = max;
    }

    private RangeMenu(int min, int max) {
        this.min = min;
        this.max = max;

        scan = new Scanner(System.in);
    }

    public static RangeMenu create(String message, int from, int to) {
        RangeMenu rangeMenu = new RangeMenu(from, to);
        rangeMenu.setMessage(message);
        return rangeMenu;
    }

    public int promptNoAction() {

        System.out.print(this.getMessage()+" ("+this.getMin()+"..."+this.getMax()+"): ");

        int response = scan.nextInt();

        int parsedResponse = this.parseResponse(response);
        if (parsedResponse == -1) {
            System.out.println(this.getFailedMessage());
            parsedResponse = this.promptNoAction();
        }

        return parsedResponse;

    }

    private int parseResponse(int response) {

        // validity check
        if (this.min < response && response <= this.max) {
            return response;
        } else {
            // invalid
            return -1;
        }

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
