package com.wissen.demo.service;

import com.wissen.demo.domain.Holiday;
import com.wissen.demo.dto.CalendarResponseDto;
import com.wissen.demo.dto.HolidayDto;
import com.wissen.demo.dto.WeekDto;
import com.wissen.demo.repo.HolidayRepository;
import com.wissen.demo.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CalendarService {

    @Autowired
    private HolidayRepository holidayRepository;

    public CalendarResponseDto getCalendarData(String viewType, LocalDate startDate,
                                               String country, String timeZone) {
        LocalDate endDate = calculateEndDate(viewType, startDate);

        List<Holiday> holidays = holidayRepository.findByCountryAndDateBetween(
                country, startDate, endDate);

        List<HolidayDto> holidayDtos = holidays.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        List<WeekDto> weeks = calculateWeeks(startDate, endDate, holidays);

        return new CalendarResponseDto(startDate, endDate, viewType,
                country, timeZone, weeks, holidayDtos);
    }

    public List<String> getAvailableCountries() {
        return holidayRepository.findAllCountries();
    }

    private LocalDate calculateEndDate(String viewType, LocalDate startDate) {
        return switch (viewType.toLowerCase()) {
            case Constant.WEEKLY -> startDate.plusWeeks(1).minusDays(1);
            case Constant.MONTHLY -> startDate.plusMonths(1).minusDays(1);
            case Constant.QUARTERLY -> startDate.plusMonths(3).minusDays(1);
            default -> startDate.plusMonths(1).minusDays(1);
        };
    }

    private List<WeekDto> calculateWeeks(LocalDate startDate, LocalDate endDate, List<Holiday> holidays) {
        List<WeekDto> weeks = new ArrayList<>();
        Map<LocalDate, List<Holiday>> holidaysByWeek = groupHolidaysByWeek(holidays);

        LocalDate current = startDate.with(WeekFields.ISO.dayOfWeek(), 1); // Start of week (Monday)

        while (!current.isAfter(endDate)) {
            LocalDate weekEnd = current.plusDays(6);
            List<Holiday> weekHolidays = holidaysByWeek.getOrDefault(current, Collections.emptyList());

            String highlightType = determineHighlightType(weekHolidays);
            List<HolidayDto> weekHolidayDtos = weekHolidays.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());

            weeks.add(new WeekDto(current, weekEnd, highlightType, weekHolidayDtos));
            current = current.plusWeeks(1);
        }

        return weeks;
    }

    private Map<LocalDate, List<Holiday>> groupHolidaysByWeek(List<Holiday> holidays) {
        return holidays.stream()
                .collect(Collectors.groupingBy(
                        holiday -> holiday.getDate().with(WeekFields.ISO.dayOfWeek(), 1)
                ));
    }

    private String determineHighlightType(List<Holiday> weekHolidays) {
        int holidayCount = weekHolidays.size();
        if (holidayCount >= 2) {
            return Constant.DARK;
        } else if (holidayCount == 1) {
            return Constant.LITE;
        }
        return Constant.NONE;
    }

    private HolidayDto convertToDto(Holiday holiday) {
        return new HolidayDto(
                holiday.getId(),
                holiday.getName(),
                holiday.getDate(),
                holiday.getCountry(),
                holiday.getTimeZone()
        );
    }
}
