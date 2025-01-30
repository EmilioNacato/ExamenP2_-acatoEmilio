package com.examendos.examenp2.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.examendos.examenp2.controller.dto.BranchHolidayDTO;
import com.examendos.examenp2.model.BranchHoliday;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BranchHolidayMapper {
    BranchHolidayDTO toDTO(BranchHoliday model);
    BranchHoliday toModel(BranchHolidayDTO dto);
} 