package com.examendos.examenp2.exception;

public class UpdateNotAllowedException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public UpdateNotAllowedException(String field) {
        super("No está permitido actualizar el campo: " + field);
    }
} 