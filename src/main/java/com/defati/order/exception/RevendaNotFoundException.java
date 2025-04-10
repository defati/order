package com.defati.order.exception;

public class RevendaNotFoundException extends RuntimeException {

    public RevendaNotFoundException() {
        super("Revenda não encontrada");
    }

    public RevendaNotFoundException(String message) {
        super(message);
    }
}