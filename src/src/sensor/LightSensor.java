package sensor;

import source.ILightSource;
import source.ISource;

public class LightSensor implements ILightSensor {

   private ILightSource lightSource;

    public LightSensor(ILightSource lightSource) {
        this.lightSource = lightSource;
    }

    @Override
    public Boolean read() {
        return lightSource.value();
    }

    public ILightSource getLightSource() {
        return lightSource;
    }

    public void setLightSource(ILightSource lightSource) {
        this.lightSource = lightSource;
    }
}
