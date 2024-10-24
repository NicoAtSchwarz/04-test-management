package jgiven;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import model.cockpit.*;
import model.command.*;
import model.shared.*;

@Getter
@Setter
@RequiredArgsConstructor
public class GivenAirplaneWithFuel extends Stage<GivenAirplaneWithFuel> {

    @ProvidedScenarioState
    private Cockpit cockpit;

    @ProvidedScenarioState
    private JetEngine[] jetEngines = new JetEngine[2];

    private JetEngine leftEngine;
    private JetEngine rightEngine;


    public GivenAirplaneWithFuel the_airplane_is_initialized_with_full_tanks(int speed) {
        FuelTank leftWingTank = new FuelTank("left wing tank", 37000);
        FuelTank rightWingTank = new FuelTank("right wing tank", 37000);
        FuelTank centerTank = new FuelTank("center tank", 64000);

        leftEngine = new JetEngine(WingPosition.LEFT, leftWingTank, centerTank);
        rightEngine = new JetEngine(WingPosition.RIGHT, rightWingTank, centerTank);

        EngineSwitch leftEngineSwitch = new EngineSwitch(new StartEngineCommand(leftEngine), new ShutdownEngineCommand(leftEngine));
        EngineSwitch rightEngineSwitch = new EngineSwitch(new StartEngineCommand(rightEngine), new ShutdownEngineCommand(rightEngine));

        cockpit = new Cockpit(leftEngineSwitch, rightEngineSwitch, null);
        cockpit.setSpeedKnob(new SpeedKnob(new AdjustSpeedCommand(cockpit, speed), new AdjustSpeedCommand(cockpit, 0)));

        jetEngines[0] = leftEngine;
        jetEngines[1] = rightEngine;

        return this;
    }
}
