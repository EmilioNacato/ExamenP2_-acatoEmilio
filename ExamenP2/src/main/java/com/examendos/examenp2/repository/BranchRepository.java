package com.examendos.examenp2.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.examendos.examenp2.model.Branch;

@Repository
public interface BranchRepository extends MongoRepository<Branch, String> {
    boolean existsByEmailAddress(String emailAddress);
} 