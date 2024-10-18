package model.cockpit;

import lombok.RequiredArgsConstructor;
import model.command.Command;

@RequiredArgsConstructor
public class EngineSwitch {
    private final Command startCommand;
    private final Command stopCommand;
    private boolean isEngineOn;

    public void toggle() {
        if (isEngineOn) {
            stopCommand.execute();
            isEngineOn = false;
        } else {
            startCommand.execute();
            isEngineOn = true;
        }
    }
}