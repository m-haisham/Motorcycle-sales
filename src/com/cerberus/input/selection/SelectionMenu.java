package com.cerberus.input.selection;

import com.cerberus.input.Menu;
import com.cerberus.input.selection.callers.HeaderCaller;
import com.cerberus.input.selection.callers.InputCaller;
import com.cerberus.input.selection.callers.ListCaller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Simple selection menu
 */
public class SelectionMenu extends Menu {

    private Scanner scan;

    /**
     * Menu name
     */
    private String label;
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Selection menu items
     */
    private ArrayList<SelectionItem> items;
    public ArrayList<SelectionItem> getItems() {
        return items;
    }
    public void setItems(ArrayList<SelectionItem> items) {
        this.items = items;
    }

    /**
     * Header function
     */
    private HeaderCaller header;
    public void setHeader(HeaderCaller header) {
        this.header = header;
    }

    /**
     * List function
     */
    private ListCaller list;
    public void setList(ListCaller list) {
        this.list = list;
    }

    /**
     * Input function
     * return response
     */
    private InputCaller input;
    public void setInput(InputCaller scan) {
        this.input = scan;
    }

    private SelectionMenu(String label, ArrayList<SelectionItem> items) {
        super();

        this.label = label;
        this.items = items;

        // default header print
        this.setHeader((String l) -> {
            String separator = constructString("-", l.length() + this.getPaddingLength() * 2);
            System.out.println(/* get some space */);
            System.out.println(this.padding()+l);
            System.out.println(separator);
        });

        // default list print
        this.setList((ArrayList<SelectionItem> menuItems) -> {
            int offset = 1;
            for (int i = 0; i < menuItems.size(); i++) {
                SelectionItem item = menuItems.get(i);

                if(item.getClass() == SelectionOption.class) {
                    System.out.println(this.padding() + (i+offset) + ". "+ ((SelectionOption) item).getLabel());
                } else if (item.getClass() == SelectionSeperator.class) {
                    System.out.println(this.padding() + ((SelectionSeperator) item).getSeparator());
                    offset--;
                }

            }
        });

        // default scan
        this.setInput((Scanner scan) -> {
            System.out.print("\n-> ");
            return scan.nextLine();
        });
    }

    public static SelectionMenu create(String label, ArrayList<SelectionItem> items) {
        SelectionMenu menu = new SelectionMenu(label, items);
        menu.scan = new Scanner(System.in);
        return menu;
    }

    public static SelectionMenu create(String label, SelectionItem[] items) {
        SelectionMenu menu = new SelectionMenu(label, new ArrayList<>(Arrays.asList(items)));
        menu.scan = new Scanner(System.in);
        return menu;
    }

    public static SelectionMenu create(String label, ArrayList<SelectionItem> items, int paddingLength) {
        SelectionMenu menu = new SelectionMenu(label, items);
        menu.setPaddingLength(paddingLength);
        menu.scan = new Scanner(System.in);
        return menu;
    }

    public static SelectionMenu create(String label, SelectionItem[] items, int paddingLength) {
        SelectionMenu menu = new SelectionMenu(label, new ArrayList<>(Arrays.asList(items)));
        menu.setPaddingLength(paddingLength);
        menu.scan = new Scanner(System.in);
        return menu;
    }

    public static SelectionMenu custom(String label, ArrayList<SelectionItem> items, int paddingLength, HeaderCaller header, ListCaller list, InputCaller input) {
        SelectionMenu menu = new SelectionMenu(label, items);
        menu.setPaddingLength(paddingLength);
        menu.scan = new Scanner(System.in);

        menu.setHeader(header);
        menu.setList(list);
        menu.setInput(input);

        return menu;
    }

    /**
     * Print menu and parse selection and execute proper function
     */
    public void prompt() {

        header.call(this.getLabel());

        list.call(this.getItems());

        String response = input.call(scan);

        // parse and do appropriate option
        int responseIndex = this.parseResponse(response);
        if (responseIndex == -1) {
            System.out.println(this.getFailedMessage());
            this.prompt();
        }
        else if (this.items.get(responseIndex).getClass() == SelectionOption.class)
            ((SelectionOption) this.items.get(responseIndex)).getAction().call(); // execute function
        else {
            System.out.println(this.getFailedMessage());
            this.prompt();
        }
    }

    /**
     * parses response against this.items, string or number
     * @param response value to be parsed
     * @return if valid, index pointing to item else -1
     */
    private int parseResponse(String response) {
        try { // check whether input is integer parse able

            int parsed = Integer.parseInt(response);

            parsed--; // change start to 0

            int offset = 0; // declare offset

            for (int i = 0; i < this.getItems().size(); i++) {
                SelectionItem item = this.getItems().get(i);

                // if index is separator, increment offset
                if (item.getClass() == SelectionSeperator.class) {
                    offset++;
                }

                // if input is same as current i, break loop
                if (i == parsed) {
                    break;
                }

            }

            if (parsed >= 0 && parsed <= this.getItems().size() - offset) // if out of bounds
                return -1;

            return parsed + offset;
        }
        catch (NumberFormatException e) {

            // check for matching label
            for (int i = 0; i < this.getItems().size(); i++) {
                SelectionItem item = this.getItems().get(i);

                if (response.equalsIgnoreCase(((SelectionOption) item).getLabel())) {
                    return i;
                }
            }

            // no matching label
            return -1;
        }
    }

}
