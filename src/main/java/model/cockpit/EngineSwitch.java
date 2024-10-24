package model.cockpit;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import model.command.Command;

@RequiredArgsConstructor
public class EngineSwitch {
    private final Command startCommand;
    private final Command stopCommand;
    @Getter
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