package sensor;

import source.IMotionSource;
import source.ISource;

public class MotionSensor implements IMotionSensor {

    private IMotionSource motionSource;

    public MotionSensor(IMotionSource motionSource) {
        this.motionSource = motionSource;
    }

    @Override
    public Boolean read() {
        return motionSource.value();
    }

    public IMotionSource getMotionSource() {
        return motionSource;
    }

    public void setMotionSource(IMotionSource motionSource) {
        this.motionSource = motionSource;
    }
}
