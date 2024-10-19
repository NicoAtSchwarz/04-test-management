package cucumber.stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import model.shared.*;

public class AirplaneFuelConsumptionSteps {

    private FuelTank leftWingTank;
    private FuelTank rightWingTank;
    private FuelTank centerTank;
    private JetEngine leftEngine;
    private JetEngine rightEngine;
    private int[] speeds = {559, 562, 562, 560, 562, 565, 570, 575, 550, 540};
    private int[] fuelConsumption = new int[10];
    private int centerTankCapacity;

    @Given("the airplane has a left and a right wing tank with a capacity of {int} each and a center tank with a capacity of {int} \\(AirplaneFuelConsumption)")
    public void theAirplaneHasTanks(int wingTankCapacity, int centerTankCapacity) {
        this.centerTankCapacity = centerTankCapacity;
        leftWingTank = new FuelTank("left wing tank", wingTankCapacity);
        rightWingTank = new FuelTank("right wing tank", wingTankCapacity);
        centerTank = new FuelTank("center tank", centerTankCapacity);
    }

    @Given("each wing tank belows to one jet engine")
    public void eachWingTankBelowsToOneJetEngine() {
        leftEngine = new JetEngine(WingPosition.LEFT, leftWingTank, centerTank);
        rightEngine = new JetEngine(WingPosition.RIGHT, rightWingTank, centerTank);
    }

    @Given("the airplane is flying for {int} hours")
    public void theAirplaneIsFlying(int hours) {
    }

    @When("the speed for hour {int} is {int} mph")
    public void theSpeedForHourIs(int hour, int speed) {
        fuelConsumption[hour - 1] = speed * 11;
    }

    @Then("the fuel consumption for hour {int} should be {int}")
    public void theFuelConsumptionForHourShouldBe(int hour, int expectedConsumption) {
        leftEngine.burnFuel(fuelConsumption[hour - 1]);
        rightEngine.burnFuel(fuelConsumption[hour - 1]);
        assertEquals(expectedConsumption, fuelConsumption[hour - 1]);
    }

    @Then("the fuel in the center tank should be less than at departure")
    public void theFuelInTheCenterTankShouldBeLessThanAtDeparture() {
        assertTrue(centerTank.getCurrentFuel() < centerTankCapacity);
    }
}

