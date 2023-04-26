package sensor;

import source.ISource;

public class LightSensor implements ILightSensor {

    @Override
    public Boolean read(ISource<? extends Boolean> source) {
        return source.value();
    }
}
