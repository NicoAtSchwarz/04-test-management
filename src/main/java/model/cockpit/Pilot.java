package model.cockpit;

public class Pilot {
    private final Cockpit cockpit;

    public Pilot(Cockpit cockpit) {
        this.cockpit = cockpit;
    }

    public void setSpeed(int speed) {
        while (cockpit.getCurrentSpeed() != speed) {
            if (cockpit.getCurrentSpeed() < speed) {
                turnSpeedKnobRight();
            } else {
                turnSpeedKnobLeft();
            }

            cockpit.setCurrentSpeed(speed);
        }
    }

    public void turnSpeedKnobRight() {
        cockpit.getSpeedKnob().turnRight();
    }

    public void turnSpeedKnobLeft() {
        cockpit.getSpeedKnob().turnLeft();
    }

    public void toggleLeftEngine() {
        cockpit.getLeftEngineSwitch().toggle();
    }

    public void toggleRightEngine() {
        cockpit.getRightEngineSwitch().toggle();
    }

    public void shutdownEngines() {
        cockpit.getLeftEngineSwitch().toggle();
        cockpit.getRightEngineSwitch().toggle();
    }
}