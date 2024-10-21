package jgiven;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.junit.jupiter.api.Assertions;

import model.shared.*;

public class ThenVerifyFuelConsumption extends Stage<ThenVerifyFuelConsumption> {

    @ProvidedScenarioState
    private JetEngine[] jetEngines;

    private JetEngine leftEngine;
    private JetEngine rightEngine;

    public ThenVerifyFuelConsumption verify_centertank_is_used_so_wingtank_is_below_threshold() {
        leftEngine = jetEngines[0];
        rightEngine = jetEngines[1];

        double initialCenterFuel = leftEngine.getCenterTank().getCapacity();

        Assertions.assertTrue(leftEngine.getCenterTank().getCurrentFuel() < initialCenterFuel,
                "Center tank should have been used after wing tank emptied for the left engine.");
        return this;
    }
}
