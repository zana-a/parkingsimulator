package com.zana.parkingsimulator;

import com.zana.parkingsimulator.entity.Car;
import com.zana.parkingsimulator.util.FeeCalculator;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FeeCalculatorTest {

    private final FeeCalculator feeCalculator = new FeeCalculator();
    private final Faker faker = new Faker();

    private Car createCarWithMinuteDurationOf(long duration) {
        return Car.builder()
                .reg(faker.vehicle().licensePlate())
                .enteredTime(LocalDateTime.now())
                .exitedTime(LocalDateTime.now().plusMinutes(duration))
                .build();
    }

    @Test
    public void calculateFeeForLessThanOneHour() {
        final var duration = 29; // 29 minutes = 0.4833 hours
        final var car = createCarWithMinuteDurationOf(duration);
        final var result = feeCalculator.apply(car);
        final var expected = 0.0;
        assertEquals(expected, result);
    }

    @Test
    public void calculateFeeForCeiling() {
        final var duration = 150; // 150 minutes = 2.5 hours
        final var car = createCarWithMinuteDurationOf(duration);
        final var result = feeCalculator.apply(car);
        final var expected = 6.0;
        assertEquals(expected, result);
    }

    @Test
    public void calculateFeeForFloor() {
        final var duration = 125; // 125 minutes = 2.0833 hours
        final var car = createCarWithMinuteDurationOf(duration);
        final var result = feeCalculator.apply(car);
        final var expected = 4.0;
        assertEquals(expected, result);
    }
}
