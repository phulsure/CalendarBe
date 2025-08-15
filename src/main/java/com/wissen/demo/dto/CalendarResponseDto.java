package com.wissen.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalendarResponseDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private String viewType;
    private String country;
    private String timeZone;
    private List<WeekDto> weeks;
    private List<HolidayDto> holidays;
}