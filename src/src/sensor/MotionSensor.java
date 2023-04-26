package sensor;

import source.ISource;

public class MotionSensor implements IMotionSensor {

    @Override
    public Boolean read(ISource<? extends Boolean> source) {
        return source.value();
    }
}
