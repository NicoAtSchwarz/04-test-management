package jgiven;

import com.tngtech.jgiven.junit5.ScenarioTest;
import org.junit.jupiter.api.Test;

public class AirplaneFuelConsumptionTest extends ScenarioTest<GivenAirplaneWithFuel, WhenAirplaneIsFlying, ThenVerifyFuelConsumption> {
    @Test
    public void test_fuel_consumption_and_tank_switch() {
        int[] speeds = {600, 600, 600, 600, 600, 600, 600, 600, 600, 600};

        given() .the_airplane_is_initialized_with_full_tanks();

        when()  .the_pilot_sets_speed_to(600)
                .and().the_airplane_flies_for_hours_with_engine_speeds(6, speeds)
                .and().one_engine_is_stopped_after(6);

        then()  .only_one_engine_should_consume_fuel()
                .and().fuel_should_be_consumed_properly_after_tank_switch();
    }
}
