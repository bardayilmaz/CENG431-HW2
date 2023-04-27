package sensor;

import source.IHeatSource;

public class HeatSensor implements IHeatSensor {

    private IHeatSource heatSource;

    public HeatSensor(IHeatSource heatSource) {
        this.heatSource = heatSource;
    }

    @Override
    public Float read() {
        return heatSource.value();
    }

    public IHeatSource getHeatSource() {
        return heatSource;
    }

    public void setHeatSource(IHeatSource heatSource) {
        this.heatSource = heatSource;
    }
}
