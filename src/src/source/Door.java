package source;

public class Door implements IMotionSource {

    private boolean value;

    public Door() {
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
