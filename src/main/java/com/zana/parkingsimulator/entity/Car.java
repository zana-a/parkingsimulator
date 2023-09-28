package com.zana.parkingsimulator.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.With;

import java.time.LocalDateTime;

@Data
@Builder
@With
@AllArgsConstructor
public class Car {
    private String reg;
    private LocalDateTime enteredTime;
    private LocalDateTime exitedTime;
}
