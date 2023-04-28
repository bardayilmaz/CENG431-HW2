package control_panel;

import mediator.Mediator;
import source.ILightSource;

public class LightControlPanel implements ILightControlPanel{

    Mediator mediator;

    public LightControlPanel(Mediator mediator) {
        this.mediator = mediator;
    }


    @Override
    public boolean closeLight(int index) {
        return mediator.setLightBulbValue(index, false);
    }

    @Override
    public boolean openLight(int index) {
        return mediator.setLightBulbValue(index, true);
    }
}
