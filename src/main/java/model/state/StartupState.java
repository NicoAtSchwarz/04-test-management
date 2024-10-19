package model.state;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.shared.JetEngine;

@Slf4j
@RequiredArgsConstructor
public class StartupState extends JetEngineState {
    private final JetEngine engine;

    public void start() {
        log.info("jet engine already in startup state.");
    }

    public void shutdown() {
        engine.setState(new ShutdownState(engine));
        log.info("jet engine shutting down from startup state.");
    }

    public void run(double fuelConsumption) {
        log.info("jet engine off, no fuel consumption.");
    }
}