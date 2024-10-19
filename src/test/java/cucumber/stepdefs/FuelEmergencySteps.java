package cucumber.stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.cockpit.*;
import model.command.*;
import model.shared.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FuelEmergencySteps {

    private Cockpit cockpit;
    private Pilot pilot;
    private JetEngine leftEngine;
    private JetEngine rightEngine;
    private FuelTank leftWingTank;
    private FuelTank rightWingTank;
    private FuelTank centerTank;

    @Given("the airplane has a left and a right wing tank with a capacity of {int} each and a center tank with a capacity of {int} \\(FuelEmergency)")
    public void theAirplaneHasTanks(int wingTankCapacity, int centerTankCapacity) {
        leftWingTank = new FuelTank("left wing tank", wingTankCapacity);
        rightWingTank = new FuelTank("right wing tank", wingTankCapacity);
        centerTank = new FuelTank("center tank", centerTankCapacity);

        leftEngine = new JetEngine(WingPosition.LEFT, leftWingTank, centerTank);
        rightEngine = new JetEngine(WingPosition.RIGHT, new FuelTank("right wing tank", 37000), centerTank);

        EngineSwitch leftEngineSwitch = new EngineSwitch(new StartEngineCommand(leftEngine), new ShutdownEngineCommand(leftEngine));
        EngineSwitch rightEngineSwitch = new EngineSwitch(new StartEngineCommand(rightEngine), new ShutdownEngineCommand(rightEngine));

        cockpit = new Cockpit(leftEngineSwitch, rightEngineSwitch, null);
        cockpit.setSpeedKnob(new SpeedKnob(new AdjustSpeedCommand(cockpit, 600), new AdjustSpeedCommand(cockpit, 0)));
        pilot = new Pilot(cockpit);
    }

    @Given("both engines get started \\(FuelEmergency)")
    public void bothEnginesGetStarted() {
        pilot.toggleLeftEngine();
        pilot.toggleRightEngine();
    }

    @Given("the speed is increased to {int} \\(FuelEmergency)")
    public void theSpeedIsIncreasedTo(int speed) {
        pilot.setSpeed(speed);
    }


    @Given("the airplane has been flying for {int} hour \\(FuelEmergency)")
    public void theAirplaneHasBeenFlyingFor(int hours) {
        double fuelConsumptionPerHour = 550 * 11;
        for (int i = 0; i < hours; i++) {
            leftEngine.consumeFuel(fuelConsumptionPerHour);
            rightEngine.consumeFuel(fuelConsumptionPerHour);
        }
    }

    @When("the fuel in the left wing tank is consumed rapidly")
    public void theFuelInTheLeftWingTankIsConsumedRapidly() {
        double rapidConsumption = leftWingTank.getCurrentFuel();
        leftEngine.consumeFuel(rapidConsumption);
    }

    @When("the airplane is still flying for {int} hour \\(FuelEmergency)")
    public void theAirplaneIsStillFlyingFor(int hours) {
        double fuelConsumptionPerHour = 550 * 11;
        for (int i = 0; i < hours; i++) {
            leftEngine.consumeFuel(fuelConsumptionPerHour);
            rightEngine.consumeFuel(fuelConsumptionPerHour);
        }
    }

    @Then("the airplane should switch to the center tank")
    public void theAirplaneShouldSwitchToTheCenterTank() {
        assertTrue(centerTank.getCurrentFuel() < centerTank.getCapacity());
    }
}
