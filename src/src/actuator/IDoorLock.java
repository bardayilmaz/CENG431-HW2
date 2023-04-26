package actuator;

import source.IMotionSource;

public interface IDoorLock {

    void setValue(IMotionSource source, boolean value);

}
