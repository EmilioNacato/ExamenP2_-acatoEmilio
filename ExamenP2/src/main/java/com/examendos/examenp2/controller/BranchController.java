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
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/branches")
@Tag(name = "Gestion de Sucursales", description = "APIs para gestionar sucursales bancarias y sus feriados")
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
    @Operation(summary = "Obtener todas las sucursales", 
              description = "Retorna un listado de todas las sucursales bancarias registradas")
    @ApiResponse(responseCode = "200", description = "Lista de sucursales recuperada exitosamente")
    public ResponseEntity<List<BranchDTO>> getAllBranches() {
        log.info("Iniciando proceso de busqueda de todas las sucursales");
        List<Branch> branches = service.findAll();
        List<BranchDTO> dtos = branches.stream()
                .map(mapper::toDTO)
                .toList();
        log.info("Proceso finalizado, se encontraron {} sucursales", branches.size());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener sucursal por ID",
              description = "Busca y retorna una sucursal especifica segun su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucursal encontrada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    public ResponseEntity<BranchDTO> getBranchById(@PathVariable String id) {
        log.info("Iniciando búsqueda de sucursal con ID: {}", id);
        Branch branch = service.findById(id);
        log.info("Sucursal encontrada exitosamente. Nombre: {}", branch.getName());
        return ResponseEntity.ok(mapper.toDTO(branch));
    }

    @PostMapping
    @Operation(summary = "Crear nueva sucursal",
              description = "Crea una nueva sucursal bancaria sin feriados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucursal creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos")
    })
    public ResponseEntity<BranchDTO> createBranch(@Valid @RequestBody BranchDTO branchDTO) {
        log.info("Iniciando creacion de nueva sucursal. Nombre: {}, Email: {}", 
                branchDTO.getName(), branchDTO.getEmailAddress());
        Branch branch = service.create(mapper.toModel(branchDTO));
        log.info("Sucursal creada exitosamente con ID: {}", branch.getId());
        return ResponseEntity.ok(mapper.toDTO(branch));
    }

    @PatchMapping("/{id}/phone")
    public ResponseEntity<BranchDTO> updatePhoneNumber(
            @PathVariable String id,
            @RequestParam String phoneNumber) {
        Branch branch = service.updatePhoneNumber(id, phoneNumber);
        return ResponseEntity.ok(mapper.toDTO(branch));
    }

    @PostMapping("/{id}/holidays")
    public ResponseEntity<BranchDTO> addHoliday(
            @PathVariable String id,
            @Valid @RequestBody BranchHolidayDTO holidayDTO) {
        Branch branch = service.addHoliday(id, holidayMapper.toModel(holidayDTO));
        return ResponseEntity.ok(mapper.toDTO(branch));
    }

    @DeleteMapping("/{id}/holidays")
    public ResponseEntity<BranchDTO> removeHoliday(
            @PathVariable String id,
            @RequestParam LocalDate date) {
        log.info("Solicitud para eliminar feriado de la sucursal {} en la fecha {}", id, date);
        Branch branch = service.removeHoliday(id, date);
        return ResponseEntity.ok(mapper.toDTO(branch));
    }

    @GetMapping("/{id}/holidays")
    public ResponseEntity<List<BranchHolidayDTO>> getHolidays(@PathVariable String id) {
        List<BranchHoliday> holidays = service.getHolidays(id);
        List<BranchHolidayDTO> dtos = holidays.stream()
                .map(holidayMapper::toDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}/holidays/check")
    public ResponseEntity<Boolean> isHoliday(
            @PathVariable String id,
            @RequestParam LocalDate date) {
        boolean isHoliday = service.isHoliday(id, date);
        return ResponseEntity.ok(isHoliday);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BranchDTO> updateBranch(
            @PathVariable String id,
            @RequestBody BranchDTO branchDTO) {
        Branch branch = mapper.toModel(branchDTO);
        Branch updatedBranch = service.update(id, branch);
        return ResponseEntity.ok(mapper.toDTO(updatedBranch));
    }
} 