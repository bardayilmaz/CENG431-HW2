package control_panel;


import source.IMotionSource;

public interface IDoorLockControlPanel {

    void lockDoor(int index);

    void unlockDoor(int index);
}
