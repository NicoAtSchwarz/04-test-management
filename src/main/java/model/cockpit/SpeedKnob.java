package model.cockpit;

import lombok.RequiredArgsConstructor;
import model.command.Command;

@RequiredArgsConstructor
public class SpeedKnob {
    private final Command increaseCommand;
    private final Command decreaseCommand;

    public void turnRight() {
        increaseCommand.execute();
    }

    public void turnLeft() {
        decreaseCommand.execute();
    }
}