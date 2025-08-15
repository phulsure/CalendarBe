package com.wissen.demo.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "holidays")
@Data
public class Holiday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, name = "holiday_date")
    private LocalDate date;
    
    @Column(nullable = false)
    private String country;
    
    @Column(name = "time_zone")
    private String timeZone;
}