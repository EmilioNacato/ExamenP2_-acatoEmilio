package com.examendos.examenp2.exception;

public class NotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    private final String data;
    private final String entity;
    
    public NotFoundException(String data, String entity) {
        super();
        this.data = data;
        this.entity = entity;
    }
    
    @Override
    public String getMessage() {
        return "No se encontró ninguna coincidencia para: " + entity + 
               ", con el dato: " + data;
    }
} 