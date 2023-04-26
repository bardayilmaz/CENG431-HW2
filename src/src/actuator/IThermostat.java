package actuator;

import source.IHeatSource;

public interface IThermostat {

    void setValue(IHeatSource source, float value);

}
