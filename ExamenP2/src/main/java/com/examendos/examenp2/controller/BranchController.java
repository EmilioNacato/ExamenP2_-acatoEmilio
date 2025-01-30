package com.examendos.examenp2.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.examendos.examenp2.controller.dto.BranchDTO;
import com.examendos.examenp2.controller.dto.BranchHolidayDTO;
import com.examendos.examenp2.controller.mapper.BranchMapper;
import com.examendos.examenp2.controller.mapper.BranchHolidayMapper;
import com.examendos.examenp2.model.Branch;
import com.examendos.examenp2.model.BranchHoliday;
import com.examendos.examenp2.service.BranchService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/branches")
@Tag(name = "Gestión de Sucursales", description = "APIs para gestionar sucursales y sus feriados")
@Slf4j
public class BranchController {

    private final BranchService service;
    private final BranchMapper mapper;
    private final BranchHolidayMapper holidayMapper;

    public BranchController(BranchService service, BranchMapper mapper, BranchHolidayMapper holidayMapper) {
        this.service = service;
        this.mapper = mapper;
        this.holidayMapper = holidayMapper;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las sucursales")
    public ResponseEntity<List<BranchDTO>> getAllBranches() {
        log.info("Iniciando búsqueda de todas las sucursales");
        List<Branch> branches = service.findAll();
        List<BranchDTO> dtos = branches.stream()
                .map(mapper::toDTO)
                .toList();
        log.info("Se encontraron {} sucursales", branches.size());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener sucursal por ID")
    public ResponseEntity<BranchDTO> getBranchById(@PathVariable String id) {
        log.info("Buscando sucursal con id: {}", id);
        Branch branch = service.findById(id);
        log.info("Sucursal encontrada: {}", branch.getName());
        return ResponseEntity.ok(mapper.toDTO(branch));
    }

    @PostMapping
    @Operation(summary = "Crear nueva sucursal")
    public ResponseEntity<BranchDTO> createBranch(@Valid @RequestBody BranchDTO branchDTO) {
        log.info("Creando nueva sucursal con nombre: {}", branchDTO.getName());
        Branch branch = service.create(mapper.toModel(branchDTO));
        log.info("Sucursal creada con id: {}", branch.getId());
        return ResponseEntity.ok(mapper.toDTO(branch));
    }

    @PatchMapping("/{id}/phone")
    @Operation(summary = "Actualizar número de teléfono de la sucursal")
    public ResponseEntity<BranchDTO> updatePhoneNumber(
            @PathVariable String id,
            @RequestParam String phoneNumber) {
        Branch branch = service.updatePhoneNumber(id, phoneNumber);
        return ResponseEntity.ok(mapper.toDTO(branch));
    }

} 