package actuator;

import source.IMotionSource;

public class DoorLock implements IDoorLock {

    @Override
    public void setValue(IMotionSource source, boolean value) {
        source.setValue(value);
    }
}
