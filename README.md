# Parking Simulator

## Overview

This application is a simple non-blocking parking system.

It uses semaphores to determine capacity. If capacity is reached, the current car is blocked from parking with an error message printed specifying the reason.
However, once space has been freed (ie, the semaphore is less than the max capacity) more cars are allowed to park in.

The amount of threads and the maxiumum capacity is controlled by the user in the `ParkService` class.

The application is layed out in a similar fashion to an MVC application that is commonly associated with frameworks such as SpringBoot (although not limited to Java nor SringBoot).

Finally, we randomly pick out cars to remove from the car park. 
This is just to demonstrate non-blocking io. 
We then use the duration the car was parked for (calculated by the entered and exited times recorded on the Car object) to calculate a fee.
The fee rounds to the nearest hour (so 29 minutes will round to the floor of the hour ie. 1.29 hours will be 1 hour and 1.49 hours will round to the ceiling of the hour to 2 hours).
This is then multiplied by the hourly fee of £2.

Since concurrency and parallelism produces a non-deterministic output, I have not tested it using unit tests. 
I am currently looking at ways this could be improved.
However, I have fully tested the `FeeCalculator` using JUnit. 

I have also tried to follow the clean code standards and paid extra attention to any variable that could potentially be mutated.
With this in mind, most variables are stated to be final to ensure the variable is not overriden.
To test this, I have tried to seperate methods into functions. You may also see functional interfaces being used. 
The `FeeCalculator` class could have easily been a class with a static method.

Another aspect that the viewer should note is the `@SneakyThrows` annotations. 
This will sneakily throw any errors as the name suggests and may not be ideal in the real world as users would prefer to get a reason as to why the application failed.
This would be very difficult for developers to find as well.

There are a lot of different changes that can be made to this application especially in a real world scenario.
For example, the current application as it stands has a bi-directional gate meaning the same gates (threads) are used for entering as well as exiting.
To solve this, we could create a new executor service (a new set of managed threads) to handle the exiting of cars.

We are also relying on a for loop for keeping the services going. In reality this should be continous. But for demonstration purposes, this suffices.

## Running the application

I am using the maven build tool.
This makes compiling to a `.class` file very easy. 
The command `mvn clean install` should install and download any dependencies used.
Likewise, `mvn test` will test all unit tests.

However, running this application will need to be done manually as there is currently no build method.
This is because I ran out of time.

Importing this to IntelliJ IDEA and using the play button on the main class will work fine provided all dependencies are pulled.
