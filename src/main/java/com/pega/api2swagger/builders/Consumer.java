package com.pega.api2swagger.builders;

public interface Consumer<T> {

    /**
     * Performs this operation on the given argument.
     * @param t the input argument
     */
    void accept(T t);

}
