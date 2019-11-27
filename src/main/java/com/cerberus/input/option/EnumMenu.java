package com.cerberus.input.option;

import com.cerberus.input.Menu;
import com.cerberus.input.selection.SelectionItem;
import com.cerberus.input.selection.SelectionMenu;
import com.cerberus.input.selection.SelectionOption;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnumMenu<T extends Enum<T>> extends Menu {

    private SelectionMenu menu;
    private final Class<T> optionClass;
    private final List<T> enumValues;

    private EnumMenu(String message, Class<T> e) {
        optionClass = e;

        this.menu = SelectionMenu.create(message, new SelectionItem[0]);
        this.enumValues = Arrays.asList(optionClass.getEnumConstants());

        List<SelectionOption> options =this.enumValues
                .stream()
                .map(value -> SelectionOption.create(value.name(), (ignored) -> {}))
                .collect(Collectors.toList());

        this.menu.setItems(options);
    }

    public static <T extends Enum<T>> EnumMenu<T> create(Class<T> e) {
        return new EnumMenu(e.getSimpleName(), e);
    }

    public T promptNoAction() {
        int index = menu.promptNoAction();

        return enumValues.get(index);
    }
}
