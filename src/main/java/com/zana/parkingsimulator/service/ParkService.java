package com.zana.parkingsimulator.service;

import com.zana.parkingsimulator.entity.Car;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class ParkService implements Runnable {

    private final ExecutorService threadPool;
    private final CarEnterService carEnterService;
    private final CarExitService carExitService;
    private final int capacity;

    public ParkService(int gates, int capacity) {
        this.capacity = capacity;
        final var semaphore = new Semaphore(capacity);
        final var parkedCars = Collections.synchronizedList(new ArrayList<Car>());
        threadPool = Executors.newFixedThreadPool(gates);
        carEnterService = new CarEnterService(semaphore, parkedCars, capacity);
        carExitService = new CarExitService(semaphore, parkedCars);
    }

    @Override
    public void run() {
        for (var index = 0; index <= getTotalAttemptingCars(); index++) {
            threadPool.submit(carEnterService);
            threadPool.submit(carExitService);
        }
        threadPool.shutdown();
    }

    private int getTotalAttemptingCars() {
        // The number of cars attempting to enter the car park.
        // In the real world this would be a continuous stream of cars.
        return capacity * 4;
    }
}
