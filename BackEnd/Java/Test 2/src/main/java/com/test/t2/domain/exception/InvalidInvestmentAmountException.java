package com.test.t2.domain.exception;

public class InvalidInvestmentAmountException extends RuntimeException {
    public InvalidInvestmentAmountException() {
        super("El monto a invertir no puede ser igual o inferior a cero.");
    }
}