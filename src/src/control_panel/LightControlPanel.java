package control_panel;

import mediator.Mediator;
import source.ILightSource;

public class LightControlPanel implements ILightControlPanel{

    Mediator mediator;

    public LightControlPanel(Mediator mediator) {
        this.mediator = mediator;
    }


    @Override
    public void closeLight(int index) {
        mediator.setLightBulbValue(index, false);
    }

    @Override
    public void openLight(int index) {
        mediator.setLightBulbValue(index, true);
    }
}
