package sensor;

import source.ISource;

public class HeatSensor implements IHeatSensor {

    @Override
    public Float read(ISource<? extends Float> source) {
        return source.value();
    }
}
