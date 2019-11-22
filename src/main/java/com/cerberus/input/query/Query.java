package com.cerberus.input.query;

import com.cerberus.input.Callable;

import java.util.Scanner;

public class Query {
    private Scanner input;

    private Query() {
        setInput(new Scanner(System.in));
    }

    public static Query create() {
        return new Query();
    }

    public static Query create(Scanner scanner) {
        final Query query = new Query();
        query.setInput(scanner);
        return query;
    }

    public Scanner getInput() {
        return input;
    }

    public void setInput(Scanner input) {
        this.input = input;
    }

    public <T> T ask(String question, QueryCaller<T> caller) {
        System.out.print(question);
        return caller.call(input);
    }
}
