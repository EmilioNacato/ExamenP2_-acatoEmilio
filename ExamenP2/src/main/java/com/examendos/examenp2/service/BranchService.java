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
        log.info("Actualizando número de teléfono para la sucursal: {}", id);
        Branch branch = findById(id);
        
        if (!branch.getPhoneNumber().matches("^[0-9]{10}$")) {
            throw new IllegalArgumentException("El número de teléfono debe tener 10 dígitos");
        }
        
        branch.setPhoneNumber(phoneNumber);
        branch.setLastModifiedDate(LocalDateTime.now());
        log.info("Número de teléfono actualizado. Nueva fecha de modificación: {}", branch.getLastModifiedDate());
        return repository.save(branch);
    }
    
} 