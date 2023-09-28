package com.zana.parkingsimulator;

import com.zana.parkingsimulator.service.ParkService;

public class Main {

    public static void main(String[] args) {
        new ParkService(2, 100).run();
    }
}