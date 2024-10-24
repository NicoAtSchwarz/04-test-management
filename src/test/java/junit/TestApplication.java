package junit;

import model.cockpit.Cockpit;
import model.cockpit.EngineSwitch;
import model.cockpit.Pilot;
import model.cockpit.SpeedKnob;
import model.command.AdjustSpeedCommand;
import model.command.Command;
import model.command.ShutdownEngineCommand;
import model.command.StartEngineCommand;
import model.shared.FuelTank;
import model.shared.JetEngine;
import model.shared.WingPosition;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestApplication {

    private Cockpit cockpit;
    private JetEngine leftEngine;
    private JetEngine rightEngine;
    private Pilot pilot;
    private static final int FUEL_BURN_RATE = 15; // lbs per mph
    private static final int MAX_SPEED = 625;

    @BeforeAll
    void setUp() {
        FuelTank leftWingTank = new FuelTank("left wing tank", 37000);
        FuelTank rightWingTank = new FuelTank("right wing tank", 37000);
        FuelTank centerTank = new FuelTank("center tank", 64000);

        leftEngine = new JetEngine(WingPosition.LEFT, leftWingTank, centerTank);
        rightEngine = new JetEngine(WingPosition.RIGHT, rightWingTank, centerTank);

        Command startLeftEngine = new StartEngineCommand(leftEngine);
        Command startRightEngine = new StartEngineCommand(rightEngine);
        Command stopLeftEngine = new ShutdownEngineCommand(leftEngine);
        Command stopRightEngine = new ShutdownEngineCommand(rightEngine);

        EngineSwitch leftEngineSwitch = new EngineSwitch(startLeftEngine, stopLeftEngine);
        EngineSwitch rightEngineSwitch = new EngineSwitch(startRightEngine, stopRightEngine);

        cockpit = new Cockpit(leftEngineSwitch, rightEngineSwitch, null);
        Command increaseSpeed = new AdjustSpeedCommand(cockpit, 10);
        Command decreaseSpeed = new AdjustSpeedCommand(cockpit, -10);

        SpeedKnob speedKnob = new SpeedKnob(increaseSpeed, decreaseSpeed);
        cockpit.setSpeedKnob(speedKnob);

        pilot = new Pilot(cockpit);
    }

    @Test
    @Order(1)
    @DisplayName("Assertion 1: Test Fuel Initialization")
    void testFuelInitialization() {
        assertEquals(37000, leftEngine.getWingTank().getCurrentFuel(), "Left wing tank should be initialized with 37,000 lbs of fuel.");
        assertEquals(37000, rightEngine.getWingTank().getCurrentFuel(), "Right wing tank should be initialized with 37,000 lbs of fuel.");
        assertEquals(64000, leftEngine.getCenterTank().getCurrentFuel(), "Center tank should be initialized with 64,000 lbs of fuel.");
    }

    @ParameterizedTest
    @ValueSource(ints = {100, 200, 300, 400, 500})
    @Order(2)
    @DisplayName("Test Fuel Consumption Based on Variable Speeds")
    void testFuelConsumptionBasedOnVariableSpeeds(int speed) {
        pilot.toggleLeftEngine();
        pilot.toggleRightEngine();

        double fuelConsumption = speed * FUEL_BURN_RATE; // Calculate fuel consumption
        double initialLeftFuel = leftEngine.getWingTank().getCurrentFuel();
        double initialRightFuel = rightEngine.getWingTank().getCurrentFuel();

        // Act
        leftEngine.consumeFuel(fuelConsumption);
        rightEngine.consumeFuel(fuelConsumption);

        // Assert
        assertEquals(initialLeftFuel - fuelConsumption, leftEngine.getWingTank().getCurrentFuel(),
                "Left engine fuel should match expected consumption.");
        assertEquals(initialRightFuel - fuelConsumption, rightEngine.getWingTank().getCurrentFuel(),
                "Right engine fuel should match expected consumption.");
    }


    @Test
    @Order(3)
    @DisplayName("Assertion 3: Validate Engine Draws Fuel from Wing Tank First")
    void testEngineDrawsFuelFromWingTankFirst() {
        pilot.toggleLeftEngine();
        pilot.toggleRightEngine();

        double initialLeftFuel = leftEngine.getWingTank().getCurrentFuel();
        double initialRightFuel = rightEngine.getWingTank().getCurrentFuel();
        int speed = 500; // Example speed in mph

        double fuelConsumption = speed * FUEL_BURN_RATE; // Consumption per hour
        leftEngine.consumeFuel(fuelConsumption);
        rightEngine.consumeFuel(fuelConsumption);

        // Verify fuel consumption
        assertEquals(initialLeftFuel - fuelConsumption, leftEngine.getWingTank().getCurrentFuel(),
                "Left engine should have consumed fuel from wing tank.");
        assertEquals(initialRightFuel - fuelConsumption, rightEngine.getWingTank().getCurrentFuel(),
                "Right engine should have consumed fuel from wing tank.");
    }

    @Test
    @Order(4)
    @DisplayName("Assertion 4: Switch to Center Tank when Wing Tank is Low")
    void testSwitchToCenterTank() {
        pilot.toggleLeftEngine();
        pilot.toggleRightEngine();

        // Consume fuel until left tank is at 10% capacity
        double fuelToConsume = 37000 - 3700; // Leave 3,700 lbs
        leftEngine.consumeFuel(fuelToConsume);

        // Check if the system switches to the center tank
        assertTrue(leftEngine.getCenterTank().getCurrentFuel() < 64000, "Center tank should have been used when left tank is below threshold.");
    }

    @Test
    @Order(5)
    @DisplayName("Assertion 5: Test Maximum Speed")
    void testMaximumSpeed() {
        assertEquals(MAX_SPEED, cockpit.getMaximumSpeed(), "The maximum speed should be 625 mph.");
    }

    @Test
    @Order(6)
    @DisplayName("Assertion 6: No Fuel Consumption at 0 mph")
    void testNoFuelConsumptionAtZeroSpeed() {
        double initialLeftFuel = leftEngine.getWingTank().getCurrentFuel();

        pilot.toggleLeftEngine(); // Start left engine
        pilot.setSpeed(0); // Set speed to 0

        double leftFuelAfterSettingZeroSpeed = leftEngine.getWingTank().getCurrentFuel();

        assertEquals(initialLeftFuel, leftFuelAfterSettingZeroSpeed,
                "Left wing tank should remain full at 0 mph.");

        pilot.toggleLeftEngine(); // Stop engine
    }

    @TestFactory
    @Order(7)
    @DisplayName("Test Factory: 10-Hour Flight with Variable Speeds")
    Collection<DynamicTest> testTenHourFlightWithVariableSpeeds() {
        List<DynamicTest> dynamicTests = new ArrayList<>();
        pilot.toggleLeftEngine();
        pilot.toggleRightEngine();

        int[] speeds = {559, 562, 562, 560, 562, 565, 570, 575, 550, 540};

        for (int speed : speeds) {
            dynamicTests.add(DynamicTest.dynamicTest("Test flight at speed: " + speed, () -> {
                pilot.setSpeed(speed);
                double fuelConsumption = speed * FUEL_BURN_RATE;
                leftEngine.consumeFuel(fuelConsumption);
                rightEngine.consumeFuel(fuelConsumption);

                assertTrue(leftEngine.getWingTank().getCurrentFuel() >= 0, "Left wing tank should have sufficient fuel after the flight.");
                assertTrue(rightEngine.getWingTank().getCurrentFuel() >= 0, "Right wing tank should have sufficient fuel after the flight.");
            }));
        }
        return dynamicTests;
    }
    @Test
    @Order(8)
    @DisplayName("Assertion 8: Independent Engine Control (Start, Stop, Restart)")
    void testIndependentEngineControl() {
        // Start the left engine, leave the right engine switched off
        pilot.toggleLeftEngine();

        // Left engine should now be on (test by calling again, which should turn it off again)
        pilot.toggleLeftEngine(); // Should switch off the left engine again

        // Right engine should not be affected, so it will be turned on for the first time
        pilot.toggleRightEngine(); // Turns on the right engine

        // The right engine should now be on, so it will be switched off again
        pilot.toggleRightEngine(); // Should switch off the right engine again

        // Switch left engine back on (to check if control remains independent)
        pilot.toggleLeftEngine();

    }

    @Nested
    @DisplayName("Nested Tests for Fuel Tank Switching")
    class FuelTankSwitchingTests {

        @BeforeEach
        void setUp() {
            pilot.toggleLeftEngine();
            pilot.toggleRightEngine();
        }

        @Test
        @DisplayName("Test Switching to Center Tank When Left Wing Tank is Low")
        void testSwitchToCenterTankLowFuelLeft() {
            double fuelToConsume = 37000 - 3700; // Leave 3,700 lbs
            leftEngine.consumeFuel(fuelToConsume);

            assertTrue(rightEngine.getCenterTank().getCurrentFuel() < 64000, "Center tank should have been used when left tank is below threshold.");
        }

        @Test
        @DisplayName("Test Switching to Center Tank When Right Wing Tank is Low")
        void testSwitchToCenterTankLowFuelRight() {
            double fuelToConsume = 37000 - 3700; // Leave 3,700 lbs
            rightEngine.consumeFuel(fuelToConsume);

            assertTrue(leftEngine.getCenterTank().getCurrentFuel() < 64000, "Center tank should have been used when right tank is below threshold.");
        }
    }
}
