package actuator;

import source.IMotionSource;

public class DoorLock implements IDoorLock {

    IMotionSource motionSource;

    public DoorLock(IMotionSource motionSource) {
        this.motionSource = motionSource;
    }

    @Override
    public void setValue(boolean value) {
        motionSource.setValue(value);
    }

    public IMotionSource getMotionSource() {
        return motionSource;
    }

    public void setMotionSource(IMotionSource motionSource) {
        this.motionSource = motionSource;
    }
}
