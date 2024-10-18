package model.command;

import lombok.RequiredArgsConstructor;
import model.cockpit.Cockpit;

@RequiredArgsConstructor
public class AdjustSpeedCommand extends Command {
    private final Cockpit cockpit;
    private final int speed;

    public void execute() {
        cockpit.setCurrentSpeed(speed);
    }
}