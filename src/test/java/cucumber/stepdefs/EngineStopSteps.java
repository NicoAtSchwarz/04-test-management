package cucumber.stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.cockpit.*;
import model.command.*;
import model.shared.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EngineStopSteps {

    private Cockpit cockpit;
    private Pilot pilot;
    private JetEngine leftEngine;
    private JetEngine rightEngine;
    private FuelTank leftWingTank;
    private FuelTank rightWingTank;
    private FuelTank centerTank;

    @Given("the airplane has a left and a right wing tank with a capacity of {int} each and a center tank with a capacity of {int} \\(EngineStop)")
    public void theAirplaneHasTanks(int wingTankCapacity, int centerTankCapacity) {
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

    @Given("both engines are started")
    public void bothEnginesAreStarted() {
        pilot.toggleLeftEngine();
        pilot.toggleRightEngine();
    }

    @Given("the speed is increased to {int} \\(EngineStop)")
    public void theSpeedIsIncreasedTo(int speed) {
        pilot.setSpeed(speed);
    }

    @Given("the airplane has been flying for {int} hours \\(EngineStop)")
    public void theAirplaneHasBeenFlyingFor(int hours) {
        double fuelConsumptionPerHour = 600 * 11;
        for (int i = 0; i < hours; i++) {
            leftEngine.consumeFuel(fuelConsumptionPerHour);
            rightEngine.consumeFuel(fuelConsumptionPerHour);
        }
    }

    @When("the pilot stops one of the engines")
    public void thePilotStopsOneOfTheEngines() {
        pilot.toggleLeftEngine();
    }

    @Then("the running engine should consume fuel")
    public void theRunningEngineShouldConsumeFuel() {
        assertTrue(cockpit.getRightEngineSwitch().isEngineOn());
        assertFalse(cockpit.getLeftEngineSwitch().isEngineOn());
    }
}
