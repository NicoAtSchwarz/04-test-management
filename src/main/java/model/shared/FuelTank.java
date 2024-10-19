package model.shared;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FuelTank {
    private final String name;
    @Getter
    private final double capacity;
    @Getter
    private double currentFuel;

    public FuelTank(String name, double capacity) {
        this.name = name;
        this.capacity = capacity;
        this.currentFuel = capacity;
    }

    public void consumeFuel(double amount) {
        if (currentFuel >= amount) {
            currentFuel -= amount;
            log.info("{} consumed {} lbs of fuel. remaining fuel: {} lbs.", name, amount, currentFuel);
        } else {
            log.info("{} does not have enough fuel!", name);
            currentFuel = 0;
        }
    }

    public boolean isBelowThreshold() {
        return currentFuel < (capacity * 0.10);
    }

    public boolean isEmpty() {
        return (currentFuel <= 0);
    }

    public boolean isEmptyAfter(double fuelConsumption) {
        return (currentFuel < fuelConsumption);
    }
}