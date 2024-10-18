package model.command;

import lombok.RequiredArgsConstructor;
import model.shared.JetEngine;

@RequiredArgsConstructor
public class ShutdownEngineCommand extends Command {
    private final JetEngine engine;

    public void execute() {
        engine.shutdownEngine();
    }
}