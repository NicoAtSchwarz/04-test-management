package jgiven;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.junit.jupiter.api.Assertions;

import model.shared.*;

public class ThenVerifyFuelConsumption extends Stage<ThenVerifyFuelConsumption> {

    @ProvidedScenarioState
    private JetEngine leftEngine, rightEngine;

    public ThenVerifyFuelConsumption only_one_engine_should_consume_fuel() {
        Assertions.assertTrue(leftEngine.getWingTank().isEmpty() && !rightEngine.getWingTank().isEmpty(),
                "Left engine should have stopped consuming fuel while right engine continues.");
        return self();
    }

    public ThenVerifyFuelConsumption fuel_should_be_consumed_properly_after_tank_switch() {
        Assertions.assertTrue(rightEngine.getCenterTank().isBelowThreshold(),
                "The center tank should be used after wing tanks are empty.");
        return self();
    }
}
