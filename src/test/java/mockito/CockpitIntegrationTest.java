package mockito;

import model.cockpit.Cockpit;
import model.cockpit.EngineSwitch;
import model.cockpit.Pilot;
import model.command.Command;
import model.shared.FuelTank;
import model.shared.JetEngine;
import model.shared.WingPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class CockpitIntegrationTest {

    private Cockpit cockpit;
    private Pilot pilot;
    private JetEngine leftEngine;
    private JetEngine leftEngine2;
    private JetEngine rightEngine;
    private EngineSwitch leftEngineSwitch;
    private EngineSwitch rightEngineSwitch;
    private FuelTank leftWingTank;
    private FuelTank centerTank;

    @BeforeEach
    public void setUp() {
        // Mock the JetEngines
        leftEngine = mock(JetEngine.class);
        rightEngine = mock(JetEngine.class);

        // Create EngineSwitches with anonymous classes for start/stop commands
        leftEngineSwitch = new EngineSwitch(
                new Command() {
                    @Override
                    public void execute() {
                        leftEngine.startEngine();
                    }
                },
                new Command() {
                    @Override
                    public void execute() {
                        leftEngine.shutdownEngine();
                    }
                }
        );

        rightEngineSwitch = new EngineSwitch(
                new Command() {
                    @Override
                    public void execute() {
                        rightEngine.startEngine();
                    }
                },
                new Command() {
                    @Override
                    public void execute() {
                        rightEngine.shutdownEngine();
                    }
                }
        );

        // Spy on existing FuelTanks
        leftWingTank = spy(new FuelTank("left wing tank", 37000));
        centerTank = spy(new FuelTank("center tank", 64000));

        // Initialize the cockpit with these engine switches
        cockpit = new Cockpit(leftEngineSwitch, rightEngineSwitch, null);
        pilot = new Pilot(cockpit);

        // Create JetEngine using these tanks
        leftEngine2 = spy(new JetEngine(WingPosition.LEFT, leftWingTank, centerTank));
    }

    @Test
    public void testEngineStartStop() {
        // Pilot toggles the left and right engines
        pilot.toggleLeftEngine();
        pilot.toggleRightEngine();

        // Verify that the startEngine() method was called for both engines
        verify(leftEngine, times(1)).startEngine();
        verify(rightEngine, times(1)).startEngine();

        // Pilot toggles the engines again to stop them
        pilot.toggleLeftEngine();
        pilot.toggleRightEngine();

        // Verify that the shutdownEngine() method was called for both engines
        verify(leftEngine, times(1)).shutdownEngine();
        verify(rightEngine, times(1)).shutdownEngine();
    }

    @Test
    public void testFuelConsumption() {
        // Simulate fuel consumption
        pilot.toggleLeftEngine();  // Start the engine

        // Verify fuel consumption from the left engine
        doNothing().when(leftEngine).consumeFuel(anyDouble());
        leftEngine.consumeFuel(100.0);

        verify(leftEngine, times(1)).consumeFuel(anyDouble());
    }

    @Test
    public void testNoCenterTankUseWhenWingTankHasFuel() {
        // Simulate that the wing tank still has enough fuel
        doReturn(false).when(leftWingTank).isEmptyAfter(anyDouble());

        // Consume fuel
        leftEngine.consumeFuel(2000);

        // Verify that the center tank was NOT used
        verify(centerTank, never()).consumeFuel(anyDouble());
    }

    @Test
    public void testSwitchToCenterTankAt10PercentFuel() {
        // Simulate that the wing tank has less than 10% fuel remaining
        doReturn(true).when(leftWingTank).isEmptyAfter(anyDouble());

        // Consume fuel
        leftEngine2.consumeFuel(3700);  // Consuming more than 10% of the total capacity

        // Verify that the center tank was used after the wing tank was empty
        verify(centerTank, times(1)).consumeFuel(3700);
    }
}
