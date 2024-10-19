package model.main;

/*
  An Airplane "Airbus A350-900" has two wings (left and right as enum). Each Wing has a jet engine.
  Each wing has a fuel tank with capacity of 37000 lbs. Body of airplane has a center tank with capacity of 64.000 lbs.
  Each jet engine burns 15 lbs for 1 mph. Jet Engine receives first the fuel by fuel pump from tank associated to the wing of jet engine.
  If a safety minimum of 10% of fuel is reached the fuel is received from center tank.
  Create a main with scenario flying 12 hours at mph for each hour 559, 562, 562, 560, 562, 565, 570, 575, 550, 540.
  Pilot controls the startup and shutdown of each jet engine by moving a switch in the cockpit.
  Pilot controls the speed from cockpit panel by turning a knob left for decreasing and to right for increasing.
  Maximum allowed speed in mph is 625. log for each hour the fuel consumption, remaining fuel in tanks and switching to center tank operation.
 */

import lombok.extern.slf4j.Slf4j;
import model.cockpit.Cockpit;
import model.cockpit.EngineSwitch;
import model.cockpit.Pilot;
import model.cockpit.SpeedKnob;
import model.command.AdjustSpeedCommand;
import model.command.Command;
import model.command.StartEngineCommand;
import model.command.ShutdownEngineCommand;
import model.shared.FuelTank;
import model.shared.JetEngine;
import model.shared.WingPosition;

@Slf4j
public class Application {
    public static void main(String... args) {
        FuelTank leftWingTank = new FuelTank("left wing tank", 37000);
        FuelTank rightWingTank = new FuelTank("right wing tank", 37000);
        FuelTank centerTank = new FuelTank("center tank", 64000);

        JetEngine leftEngine = new JetEngine(WingPosition.LEFT, leftWingTank, centerTank);
        JetEngine rightEngine = new JetEngine(WingPosition.RIGHT, rightWingTank, centerTank);

        Command startLeftEngine = new StartEngineCommand(leftEngine);
        Command startRightEngine = new StartEngineCommand(rightEngine);
        Command stopLeftEngine = new ShutdownEngineCommand(leftEngine);
        Command stopRightEngine = new ShutdownEngineCommand(rightEngine);

        EngineSwitch leftEngineSwitch = new EngineSwitch(startLeftEngine, stopLeftEngine);
        EngineSwitch rightEngineSwitch = new EngineSwitch(startRightEngine, stopRightEngine);

        Cockpit cockpit = new Cockpit(leftEngineSwitch, rightEngineSwitch, null);
        Command increaseSpeed = new AdjustSpeedCommand(cockpit, 10);
        Command decreaseSpeed = new AdjustSpeedCommand(cockpit, -10);

        SpeedKnob speedKnob = new SpeedKnob(increaseSpeed, decreaseSpeed);
        cockpit.setSpeedKnob(speedKnob);

        Pilot pilot = new Pilot(cockpit);

        log.info("pilot starts the engines.");
        pilot.toggleLeftEngine();
        pilot.toggleRightEngine();

        int[] speeds = {559, 562, 562, 560, 562, 565, 570, 575, 550, 540};  // speeds for each hour
        int fuelBurnRate = 11;                                              // fuel burn rate per mph
        int totalFlightHours = speeds.length;

        for (int hour = 0; hour < totalFlightHours; hour++) {
            int speed = speeds[hour];
            pilot.setSpeed(speed);
            log.info("hour {}: flying at {} mph.", hour + 1, speed);

            double fuelConsumption = speed * fuelBurnRate;
            leftEngine.consumeFuel(fuelConsumption);
            rightEngine.consumeFuel(fuelConsumption);

            logFuelStatus(leftEngine, rightEngine);
        }

        log.info("pilot stops the engines.");
        pilot.shutdownEngines();
    }

    private static void logFuelStatus(JetEngine leftEngine, JetEngine rightEngine) {
        log.info("--- fuel status ---");
        log.info("left wing tank  : {} lbs remaining.", leftEngine.getWingTank().getCurrentFuel());
        log.info("right wing Tank : {} lbs remaining.", rightEngine.getWingTank().getCurrentFuel());
        log.info("center tank     : {} lbs remaining.", rightEngine.getCenterTank().getCurrentFuel());
    }
}