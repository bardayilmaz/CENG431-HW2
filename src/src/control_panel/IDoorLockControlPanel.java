package control_panel;


import source.IMotionSource;

public interface IDoorLockControlPanel {

    boolean lockDoor(int index);

    boolean unlockDoor(int index);
}
