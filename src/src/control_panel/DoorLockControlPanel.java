package control_panel;

import mediator.Mediator;

public class DoorLockControlPanel implements IDoorLockControlPanel{

    private Mediator mediator;

    public DoorLockControlPanel(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public boolean lockDoor(int index) {
        return mediator.setDoorValue(index, true);
    }

    @Override
    public boolean unlockDoor(int index) {
        return mediator.setDoorValue(index, false);
    }
}
