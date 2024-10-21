package jgiven;

import com.tngtech.jgiven.junit5.ScenarioTest;
import org.junit.jupiter.api.Test;

public class AirplaneFuelConsumptionTest extends ScenarioTest<GivenAirplaneWithFuel, WhenAirplaneIsFlying, ThenVerifyFuelConsumption> {
    @Test
    public void test_fuel_consumption_and_tank_switch() {
        int speed = 505;

        given() .the_airplane_is_initialized_with_full_tanks(speed);

        when()  .the_pilot_sets_speed_to(speed)
                .and().the_airplane_flies_till_wingtank_is_below_threshold(speed);

        then()  .verify_centertank_is_used_so_wingtank_is_below_threshold();
    }
}
