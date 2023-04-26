package source;


public class LightSource implements ILightSource {

    private boolean value;

    public LightSource() {}

    @Override
    public Boolean value() {
        return this.value;
    }

    @Override
    public void setValue(Boolean value) {
        this.value = value;
    }
}
