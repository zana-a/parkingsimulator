package com.zana.parkingsimulator.service;

import com.zana.parkingsimulator.entity.Car;
import lombok.SneakyThrows;
import net.datafaker.Faker;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class CarEnterService implements Runnable {

    private final Semaphore semaphore;
    private final List<Car> parkedCars;
    private final int capacity;
    private final Random random;

    public CarEnterService(Semaphore semaphore, List<Car> parkedCars, int capacity) {
        this.semaphore = semaphore;
        this.parkedCars = parkedCars;
        this.capacity = capacity;
        random = new Random();
    }

    @SneakyThrows
    @Override
    public void run() {
        Thread.sleep(random.nextInt(1000));

        final var car = Car.builder()
                .reg(new Faker().vehicle().licensePlate())
                .enteredTime(LocalDateTime.now())
                .build();

        if (parkedCars.size() < capacity) {
            try {
                parkCar(car);
                printEnteredMessage(car);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            printCouldNotParkMessage(car);
        }
    }

    public void parkCar(Car car) throws InterruptedException {
        semaphore.acquire();
        parkedCars.add(car);
    }

    private void printEnteredMessage(Car car) {
        System.out.println("Car " + car.getReg() + " parked at " + car.getEnteredTime());
    }

    private void printCouldNotParkMessage(Car car) {
        System.out.println("Car " + car.getReg() + " could not be parked ");
    }
}
