package com.test.t2.domain.exception;

public class InsufficientAmountException extends RuntimeException {
    public InsufficientAmountException(String fundName, double minimumAmount) {
        super("El monto ingresado no es suficiente para vincularse al fondo " + fundName + ". Monto minimo: " + minimumAmount);
    }
}