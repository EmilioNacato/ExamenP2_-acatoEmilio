package com.examendos.examenp2.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class BranchDTO {
    private String id;
    
    @NotBlank(message = "El correo electronico es requerido")
    @Email(message = "Formato de correo electronico invalido")
    private String emailAddress;
    
    @NotBlank(message = "El nombre es requerido")
    private String name;
    
    @NotBlank(message = "El numero de telefono es requerido")
    @Pattern(regexp = "^[0-9]{10}$", message = "El número de telefono debe tener 10 caracteres")
    private String phoneNumber;
    
    private String state;
    private LocalDateTime creationDate;
    private LocalDateTime lastModifiedDate;
    private List<BranchHolidayDTO> branchHolidays;
} 