package com.examendos.examenp2.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.examendos.examenp2.exception.NotFoundException;
import com.examendos.examenp2.exception.UpdateNotAllowedException;
import com.examendos.examenp2.model.Branch;
import com.examendos.examenp2.model.BranchHoliday;
import com.examendos.examenp2.repository.BranchRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BranchService {
    
    private static final String ENTITY = "Branch";
    private final BranchRepository repository;
    
    public BranchService(BranchRepository repository) {
        this.repository = repository;
    }
    
    public List<Branch> findAll() {
        return repository.findAll();
    }
    
    public Branch findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, ENTITY));
    }
    
    public Branch create(Branch branch) {
        branch.setCreationDate(LocalDateTime.now());
        branch.setLastModifiedDate(LocalDateTime.now());
        branch.setState("ACTIVE");
        return repository.save(branch);
    }
    
    public Branch updatePhoneNumber(String id, String phoneNumber) {
        log.info("Actualizando numero de telefono para la sucursal: {}", id);
        Branch branch = findById(id);
        
        if (!branch.getPhoneNumber().matches("^[0-9]{10}$")) {
            throw new IllegalArgumentException("El numero de telefono debe tener 10 caracteres");
        }
        
        branch.setPhoneNumber(phoneNumber);
        branch.setLastModifiedDate(LocalDateTime.now());
        log.info("Numero de telefono actualizado, nueva fecha de modificacion: {}", branch.getLastModifiedDate());
        return repository.save(branch);
    }
    
    public Branch addHoliday(String id, BranchHoliday holiday) {
        Branch branch = findById(id);
        branch.getBranchHolidays().add(holiday);
        branch.setLastModifiedDate(LocalDateTime.now());
        return repository.save(branch);
    }
    
    public Branch removeHoliday(String id, LocalDate date) {
        log.info("Intentando eliminar feriado para la fecha {} de la sucursal {}", date, id);
        Branch branch = findById(id);
        
        boolean existeFeriado = branch.getBranchHolidays().stream()
                .anyMatch(h -> h.getDate().equals(date));
                
        if (!existeFeriado) {
            throw new NotFoundException(date.toString(), "Feriado");
        }
        
        branch.getBranchHolidays().removeIf(h -> h.getDate().equals(date));
        branch.setLastModifiedDate(LocalDateTime.now());
        log.info("Feriado eliminado exitosamente");
        return repository.save(branch);
    }
    
    public List<BranchHoliday> getHolidays(String id) {
        Branch branch = findById(id);
        return branch.getBranchHolidays();
    }
    
    public boolean isHoliday(String id, LocalDate date) {
        Branch branch = findById(id);
        return branch.getBranchHolidays().stream()
                .anyMatch(h -> h.getDate().equals(date));
    }

    public Branch update(String id, Branch updatedBranch) {
        throw new UpdateNotAllowedException("Solo se permite actualizar el numero de telefono");
    }
} 