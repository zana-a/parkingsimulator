package com.zana.parkingsimulator.util;

import com.zana.parkingsimulator.entity.Car;

import java.time.temporal.ChronoUnit;
import java.util.function.Function;

public class FeeCalculator implements Function<Car, Double> {

    private static final int STANDARD_FEE_IN_PENNIES = 200;
    private static final int PENNIES_IN_ONE_POUND = 100;

    @Override
    public Double apply(Car car) {
        final double durationInMinutes = car.getEnteredTime().until(car.getExitedTime(), ChronoUnit.MINUTES);
        final double durationInDecimal = Math.round(durationInMinutes / ChronoUnit.HOURS.getDuration().toMinutes());
        final double totalFeeInPennies = durationInDecimal * STANDARD_FEE_IN_PENNIES;
        return totalFeeInPennies / PENNIES_IN_ONE_POUND;
    }
}
