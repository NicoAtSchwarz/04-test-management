package model.shared;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import model.state.JetEngineState;
import model.state.ShutdownState;

@Getter
@Setter
@Slf4j
public class JetEngine {
    private final WingPosition wingPosition;
    private final FuelTank wingTank;
    private final FuelTank centerTank;
    private JetEngineState state;

    public JetEngine(WingPosition wingPosition, FuelTank wingTank, FuelTank centerTank) {
        this.wingPosition = wingPosition;
        this.wingTank = wingTank;
        this.centerTank = centerTank;
        state = new ShutdownState(this);
    }

    public void startEngine() {
        state.start();
    }

    public void shutdownEngine() {
        state.shutdown();
    }

    public void consumeFuel(double fuelConsumption) {
        burnFuel(fuelConsumption);
        state.run(fuelConsumption);
    }

    public void burnFuel(double fuelConsumption) {
        if (!wingTank.isEmptyAfter(fuelConsumption)) {
            wingTank.consumeFuel(fuelConsumption);
        } else if (!centerTank.isEmptyAfter(fuelConsumption)) {
            log.info("switching to center tank for {} engine.", wingPosition);
            centerTank.consumeFuel(fuelConsumption);
        } else {
            log.warn("both wing and center tanks are empty for {} engine", wingPosition);
        }
    }
}