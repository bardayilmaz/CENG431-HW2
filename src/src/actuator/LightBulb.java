package actuator;

import source.ILightSource;

public class LightBulb implements ILightBulb {

    private ILightSource lightSource;

    public LightBulb(ILightSource lightSource) {
        this.lightSource = lightSource;
    }

    @Override
    public void setValue(boolean value) {
        lightSource.setValue(value);
    }

    public ILightSource getLightSource() {
        return lightSource;
    }

    public void setLightSource(ILightSource lightSource) {
        this.lightSource = lightSource;
    }
}
