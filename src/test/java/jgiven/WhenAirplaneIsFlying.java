package jgiven;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import model.cockpit.*;
import model.shared.*;

public class WhenAirplaneIsFlying extends Stage<WhenAirplaneIsFlying> {

    @ProvidedScenarioState
    private Cockpit cockpit;

    @ProvidedScenarioState
    private JetEngine leftEngine, rightEngine;

    public WhenAirplaneIsFlying the_pilot_sets_speed_to(int speed) {
        cockpit.setCurrentSpeed(speed);
        return this;
    }

    public WhenAirplaneIsFlying the_airplane_flies_for_hours_with_engine_speeds(int hours, int[] speeds) {
        for (int i = 0; i < hours; i++) {
            leftEngine.consumeFuel(speeds[i] * 11);  // Simuliert den Treibstoffverbrauch
            rightEngine.consumeFuel(speeds[i] * 11); // für beide Triebwerke
        }
        return this;
    }

    public WhenAirplaneIsFlying one_engine_is_stopped_after(int hours) {
        cockpit.getLeftEngineSwitch().toggle(); // Stopping the left engine
        return this;
    }
}
