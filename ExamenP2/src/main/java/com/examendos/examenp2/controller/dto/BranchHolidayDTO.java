package com.examendos.examenp2.controller.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BranchHolidayDTO {
    @NotNull(message = "La fecha es requerida")
    private LocalDate date;
    
    @NotBlank(message = "El nombre del feriado es requerido")
    private String name;
} 