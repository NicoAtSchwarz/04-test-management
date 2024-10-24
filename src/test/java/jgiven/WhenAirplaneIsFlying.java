package jgiven;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import model.cockpit.*;
import model.shared.*;

public class WhenAirplaneIsFlying extends Stage<WhenAirplaneIsFlying> {

    @ProvidedScenarioState
    private Cockpit cockpit;

    @ProvidedScenarioState
    private JetEngine[] jetEngines;

    private JetEngine leftEngine;
    private JetEngine rightEngine;

    public WhenAirplaneIsFlying the_pilot_sets_speed_to(int speed) {
        leftEngine = jetEngines[0];
        rightEngine = jetEngines[1];

        cockpit.setCurrentSpeed(speed);
        return this;
    }

    public WhenAirplaneIsFlying the_airplane_flies_till_wingtank_is_below_threshold(int speed) {
        while (!leftEngine.getWingTank().isBelowThreshold()) {
            leftEngine.consumeFuel(speed * 11);
            rightEngine.consumeFuel(speed * 11);
        }
        leftEngine.consumeFuel(speed * 11);
        rightEngine.consumeFuel(speed * 11);
        return this;
    }
}
