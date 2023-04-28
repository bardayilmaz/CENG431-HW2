package actuator;

import source.IMotionSource;

public class DoorLock implements IDoorLock {

    private Boolean value;

    public DoorLock() {
        value = false;
    }

    @Override
    public void setValue(boolean value) {
        this.value=value;
    }

    public Boolean getValue(){
        return value;
    }
}
