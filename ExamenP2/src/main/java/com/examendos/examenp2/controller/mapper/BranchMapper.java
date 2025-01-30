package com.examendos.examenp2.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.examendos.examenp2.controller.dto.BranchDTO;
import com.examendos.examenp2.model.Branch;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BranchMapper {
    BranchDTO toDTO(Branch model);
    Branch toModel(BranchDTO dto);
} 