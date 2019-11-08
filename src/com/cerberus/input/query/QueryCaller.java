package com.cerberus.input.query;

import java.util.Scanner;

public interface QueryCaller<T> {
    public T call(Scanner scanner);
}
