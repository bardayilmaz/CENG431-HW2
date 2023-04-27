package actuator;


import source.IHeatSource;

public class Thermostat implements IThermostat {

    private IHeatSource heatSource;

    public Thermostat(IHeatSource heatSource) {
        this.heatSource = heatSource;
    }

    @Override
    public void setValue(float value) {
        heatSource.setValue(value);
    }

    public IHeatSource getHeatSource() {
        return heatSource;
    }

    public void setHeatSource(IHeatSource heatSource) {
        this.heatSource = heatSource;
    }
}
