package model.cockpit;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class Cockpit {
    private final int maximumSpeed = 625;
    private final EngineSwitch leftEngineSwitch;
    private final EngineSwitch rightEngineSwitch;
    private SpeedKnob speedKnob;
    private int currentSpeed;

    public Cockpit(EngineSwitch leftEngineSwitch, EngineSwitch rightEngineSwitch, SpeedKnob speedKnob) {
        this.leftEngineSwitch = leftEngineSwitch;
        this.rightEngineSwitch = rightEngineSwitch;
        this.speedKnob = speedKnob;
        currentSpeed = 0;
    }
}