package com.wissen.demo.controller;

import com.wissen.demo.dto.CalendarResponseDto;
import com.wissen.demo.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/calendar")
public class CalendarController {

    @Autowired
    private CalendarService calendarService;

    @GetMapping("/data")
    public ResponseEntity<CalendarResponseDto> getCalendarData(
            @RequestParam String viewType,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam String country,
            @RequestParam(required = false, defaultValue = "UTC") String timeZone) {

        CalendarResponseDto response = calendarService.getCalendarData(
                viewType, startDate, country, timeZone);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/countries")
    public ResponseEntity<List<String>> getAvailableCountries() {
        List<String> countries = calendarService.getAvailableCountries();
        return ResponseEntity.ok(countries);
    }
}