package com.examendos.examenp2.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class BranchHoliday {
    private LocalDate date;
    private String name;
} 