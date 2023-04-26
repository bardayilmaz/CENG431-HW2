package source;

public class HeatSource implements IHeatSource {

    private float value;

    public HeatSource() {
    }

    @Override
    public Float value() {
       return value;
    }

    @Override
    public void setValue(Float value) {
        this.value = value;
    }
}
