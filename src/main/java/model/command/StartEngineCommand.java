package model.command;

import lombok.RequiredArgsConstructor;
import model.shared.JetEngine;

@RequiredArgsConstructor
public class StartEngineCommand extends Command {
    private final JetEngine engine;

    public void execute() {
        engine.startEngine();
    }
}