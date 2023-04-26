package source;

public class MotionSource implements IMotionSource {

    private boolean value;

    public MotionSource() {
        this.value = false;
    }

    @Override
    public Boolean value() {
        return this.value;
    }

    @Override
    public void setValue(Boolean value) {
        this.value = value;
    }
}
