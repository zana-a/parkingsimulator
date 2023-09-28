package com.zana.parkingsimulator.service;

import com.zana.parkingsimulator.entity.Car;
import com.zana.parkingsimulator.util.FeeCalculator;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class CarExitService implements Runnable {

    private final Semaphore semaphore;
    private final List<Car> parkedCars;
    private final Random random;
    private final FeeCalculator feeCalculator;

    public CarExitService(Semaphore semaphore, List<Car> parkedCars) {
        this.semaphore = semaphore;
        this.parkedCars = parkedCars;
        random = new Random();
        feeCalculator = new FeeCalculator();
    }

    @SneakyThrows
    @Override
    public void run() {
        Thread.sleep(random.nextInt(2000));
        final var randomCarIndex = random.nextInt(parkedCars.size()) % parkedCars.size();
        final var randomSelectedCar = parkedCars.get(randomCarIndex);
        final var randomUpperBoundForExitTime = 1200;
        randomSelectedCar.setExitedTime(LocalDateTime.now().plusMinutes(random.nextInt(randomUpperBoundForExitTime)));
        printExitedMessage(randomSelectedCar);
        releaseSlot(randomCarIndex);
    }

    private void releaseSlot(int index) {
        parkedCars.remove(index);
        semaphore.release();
    }

    private void printExitedMessage(Car car) {
        System.out.println("Car "
                + car.getReg()
                + " exited at "
                + car.getExitedTime()
                + "\tTotal Fee -> £"
                + feeCalculator.apply(car));
    }
}
