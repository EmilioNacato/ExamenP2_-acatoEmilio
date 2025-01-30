package com.examendos.examenp2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleDateFormatException(MethodArgumentTypeMismatchException ex) {
        Map<String, String> response = new HashMap<>();
        
        Throwable rootCause = getRootCause(ex);
        if (rootCause instanceof DateTimeParseException) {
            response.put("error", "Formato de fecha inválido");
            response.put("mensaje", "Por favor, ingrese la fecha en formato YYYY-MM-DD. Ejemplo: 2024-02-14");
        } else {
            response.put("error", "Error en el formato del parámetro");
            response.put("mensaje", "El valor ingresado no tiene el formato correcto");
        }
        
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Throwable getRootCause(Throwable throwable) {
        if (throwable == null) return null;
        Throwable rootCause = throwable;
        while (rootCause.getCause() != null) {
            rootCause = rootCause.getCause();
        }
        return rootCause;
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFoundException(NotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Recurso no encontrado");
        response.put("mensaje", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UpdateNotAllowedException.class)
    public ResponseEntity<Map<String, String>> handleUpdateNotAllowedException(UpdateNotAllowedException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Actualización no permitida");
        response.put("mensaje", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errores = new HashMap<>();
        
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String mensaje = switch (error.getField()) {
                case "phoneNumber" -> "El número de teléfono debe tener exactamente 10 dígitos numéricos. Ejemplo: 0987654321";
                case "emailAddress" -> "Por favor, ingrese un correo electrónico válido. Ejemplo: sucursal@banquito.com";
                case "name" -> "El nombre de la sucursal no puede estar vacío";
                default -> error.getDefaultMessage();
            };
            errores.put(error.getField(), mensaje);
        });
        
        response.put("error", "Error de validación");
        response.put("mensaje", "Por favor, revise los siguientes campos:");
        response.put("detalles", errores);
        
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
} 