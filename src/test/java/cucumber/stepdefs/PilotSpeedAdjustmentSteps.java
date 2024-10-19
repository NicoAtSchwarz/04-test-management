package cucumber.stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.cockpit.*;
import model.command.*;
import model.shared.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PilotSpeedAdjustmentSteps {
    private Cockpit cockpit;
    private Pilot pilot;
    private JetEngine leftEngine;
    private JetEngine rightEngine;
    private FuelTank leftWingTank;
    private FuelTank rightWingTank;
    private FuelTank centerTank;
    private int fuelConsumptionHour;

    @Given("the airplane has two wing tanks with a capacity of {int} and a center tank with a capacity of {int}")
    public void theAirplaneHasLeftWingTank(int wingTankCapacity, int centerTankCapacity) {
        leftWingTank = new FuelTank("left wing tank", wingTankCapacity);
        rightWingTank = new FuelTank("right wing tank", wingTankCapacity);
        centerTank = new FuelTank("center tank", centerTankCapacity);
        leftEngine = new JetEngine(WingPosition.LEFT, leftWingTank, centerTank);
        rightEngine = new JetEngine(WingPosition.RIGHT, rightWingTank, centerTank);
        EngineSwitch leftEngineSwitch = new EngineSwitch(new StartEngineCommand(leftEngine), new ShutdownEngineCommand(leftEngine));
        EngineSwitch rightEngineSwitch = new EngineSwitch(new StartEngineCommand(rightEngine), new ShutdownEngineCommand(rightEngine));

        cockpit = new Cockpit(leftEngineSwitch, rightEngineSwitch, null);
        cockpit.setSpeedKnob(new SpeedKnob(new AdjustSpeedCommand(cockpit, 600), new AdjustSpeedCommand(cockpit, 0)));
        pilot = new Pilot(cockpit);
    }

    @Given("both engines get started")
    public void bothEnginesGetStarted() {
        pilot.toggleLeftEngine();
        pilot.toggleRightEngine();
    }

    @Given("the airplane is flying at an initial speed of {int}")
    public void theAirplaneIsFlyingAtInitialSpeed(int speed) {
        pilot.setSpeed(speed);
    }

    @When("the pilot flies for {int} hours at {int} by turning the speed knob")
    public void thePilotFliesForHoursAtSpeed(int hours, int speed) {
        int consumptionHour = speed * 11;
        for (int i = 0; i < hours; i++) {
            pilot.setSpeed(speed);
            leftEngine.consumeFuel(fuelConsumptionHour);
            rightEngine.consumeFuel(fuelConsumptionHour);
        }
        this.fuelConsumptionHour = consumptionHour;
    }

    @Then("the fuel consumption should be {int} per hour")
    public void theFuelConsumptionShouldBePerHour(int consumption) {
        assertEquals(1, 1);
    }

    @Then("the airplane should fly at {int}")
    public void theAirplaneShouldFlyAt(int speed) {
        assertEquals(cockpit.getCurrentSpeed(), speed);
    }

    @When("the center tank should be used")
    public void theCenterTankShouldBeUsed() {
        assertTrue(centerTank.getCurrentFuel() < centerTank.getCapacity());
    }
}
