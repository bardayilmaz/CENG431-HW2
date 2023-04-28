package actuator;

import source.IMotionSource;

public class DoorLock implements IDoorLock {

    private IMotionSource motionSource;

    public DoorLock(IMotionSource motionSource) {
        this.motionSource = motionSource;
    }

    @Override
    public void setValue(boolean value) {
        motionSource.setValue(value);
    }

    public Boolean getValue(){
        return motionSource.value();
    }
}
