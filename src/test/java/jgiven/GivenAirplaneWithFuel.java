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
    private JetEngine leftEngine;
    @ProvidedScenarioState
    private JetEngine rightEngine;

    public void the_airplane_is_initialized_with_full_tanks() {
        FuelTank leftWingTank = new FuelTank("left wing tank", 37000);
        FuelTank rightWingTank = new FuelTank("right wing tank", 37000);
        FuelTank centerTank = new FuelTank("center tank", 64000);

        leftEngine = new JetEngine(WingPosition.LEFT, leftWingTank, centerTank);
        rightEngine = new JetEngine(WingPosition.RIGHT, rightWingTank, centerTank);

        EngineSwitch leftEngineSwitch = new EngineSwitch(new StartEngineCommand(leftEngine), new ShutdownEngineCommand(leftEngine));
        EngineSwitch rightEngineSwitch = new EngineSwitch(new StartEngineCommand(rightEngine), new ShutdownEngineCommand(rightEngine));

        cockpit = new Cockpit(leftEngineSwitch, rightEngineSwitch, null);
    }
}
