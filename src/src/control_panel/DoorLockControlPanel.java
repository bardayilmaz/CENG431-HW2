package control_panel;

import mediator.Mediator;

public class DoorLockControlPanel implements IDoorLockControlPanel{

    private Mediator mediator;

    public DoorLockControlPanel(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void lockDoor(int index) {
        mediator.setDoorValue(index, false);
    }

    @Override
    public void unlockDoor(int index) {
        mediator.setDoorValue(index, true);
    }
}
