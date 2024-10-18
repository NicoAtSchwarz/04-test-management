package model.state;

public abstract class JetEngineState {
    public abstract void start();

    public abstract void shutdown();

    public abstract void run(double fuelConsumption);
}