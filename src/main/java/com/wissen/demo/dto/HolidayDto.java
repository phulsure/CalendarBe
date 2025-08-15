package com.wissen.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HolidayDto {
    private Long id;
    private String name;
    private LocalDate date;
    private String country;
    private String timeZone;
}