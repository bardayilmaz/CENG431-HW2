package actuator;

import source.IHeatSource;

public class Thermostat implements IThermostat {
    @Override
    public void setValue(IHeatSource source, float value) {
        source.setValue(value);
    }
}
