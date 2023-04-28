package control_panel;

import mediator.Mediator;

public class TemperatureControlPanel implements ITemperatureControlPanel {

    Mediator mediator;

    public TemperatureControlPanel(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void autoTemperatureControl() {
        mediator.autoHeatControl();
    }
}
