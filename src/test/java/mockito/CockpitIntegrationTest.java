package mockito;

import model.cockpit.Cockpit;
import model.cockpit.EngineSwitch;
import model.cockpit.Pilot;
import model.command.Command;
import model.shared.JetEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.*;

public class CockpitIntegrationTest {

    private Cockpit cockpit;
    private Pilot pilot;
    private JetEngine leftEngine;
    private JetEngine rightEngine;
    private EngineSwitch leftEngineSwitch;
    private EngineSwitch rightEngineSwitch;

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


        // Initialize the cockpit with these engine switches
        cockpit = new Cockpit(leftEngineSwitch, rightEngineSwitch, null);
        pilot = new Pilot(cockpit);
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
}
