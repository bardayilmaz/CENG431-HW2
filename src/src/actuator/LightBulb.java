package actuator;

import source.ILightSource;

public class LightBulb implements ILightBulb {


    @Override
    public void setValue(ILightSource source, boolean value) {
        source.setValue(value);
    }
}
