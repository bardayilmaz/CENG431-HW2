package actuator;

import source.ILightSource;

public interface ILightBulb {

    void setValue(ILightSource source, boolean value);
}
