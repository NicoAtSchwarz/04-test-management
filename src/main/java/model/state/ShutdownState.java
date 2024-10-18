package model.state;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.shared.JetEngine;

@Slf4j
@RequiredArgsConstructor
public class ShutdownState extends JetEngineState {
    private final JetEngine engine;

    public void start() {
        log.info("starting jet engine.");
        engine.setState(new StartupState(engine));
    }

    public void shutdown() {
        log.info("jet engine already stopped.");
    }

    public void run(double fuelConsumption) {
        log.info("jet engine off, no fuel consumption.");
    }
}