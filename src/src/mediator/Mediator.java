package mediator;

import actuator.IDoorLock;
import actuator.ILightBulb;
import actuator.IThermostat;
import sensor.IHeatSensor;
import sensor.ILightSensor;
import sensor.IMotionSensor;
import source.IHeatSource;
import source.ILightSource;
import source.IMotionSource;

import java.util.List;

public class Mediator {

    private List<ILightSource> lightSources;
    private List<IMotionSource> motionSources;
    private IHeatSource heatSource;

    private List<IDoorLock> doorLocks;
    private List<ILightBulb> lightBulbs;
    private IThermostat thermostat;

    private List<IMotionSensor> motionSensors;
    private List<ILightSensor> lightSensors;
    private IHeatSensor heatSensor;

    public Mediator(List<ILightSource> lightSources, List<IMotionSource> motionSources, IHeatSource heatSource,
                    List<IDoorLock> doorLocks, List<ILightBulb> lightBulbs, IThermostat thermostat,
                    List<IMotionSensor> motionSensors, List<ILightSensor> lightSensors, IHeatSensor heatSensor) {
        this.lightSources = lightSources;
        this.motionSources = motionSources;
        this.heatSource = heatSource;
        this.doorLocks = doorLocks;
        this.lightBulbs = lightBulbs;
        this.thermostat = thermostat;
        this.motionSensors = motionSensors;
        this.lightSensors = lightSensors;
        this.heatSensor = heatSensor;
    }

    public void setDoorValue(int index, boolean value) {
        IDoorLock doorLock;
        try {
            doorLock = doorLocks.get(index);
        } catch (IndexOutOfBoundsException exception) {
            System.err.println("Invalid index!");
            return;
        }
        doorLock.setValue(value);
    }

    public void setLightBulbValue(int index, boolean value) {
        ILightBulb lightBulb;
        try {
            lightBulb = lightBulbs.get(index);
        } catch (IndexOutOfBoundsException exception) {
            System.err.println("Invalid index!");
            return;
        }
        lightBulb.setValue(value);
    }

    public void setTemperature(float value) {
        thermostat.setValue(value);
    }

     public Boolean getDoorValue(int index) {
        IMotionSensor sensor;
        try {
            sensor = motionSensors.get(index);

        } catch (IndexOutOfBoundsException exception) {
            System.err.println("Invalid index!");
            return null;
        }
        return sensor.read();
     }

     public Boolean getLightValue(int index) {
        ILightSensor lightSensor;
        try {
            lightSensor = lightSensors.get(index);
        } catch (IndexOutOfBoundsException exception) {
            System.err.println("Invalid index!");
            return null;
        }
        return lightSensor.read();
     }

     public Float getTemperature() {
        return heatSensor.read();
     }

     public Boolean decideDoorValue(boolean value) {
        if (value) {
            return false;
        } else {
            return null; // means do nothing
        }
     }

     public Boolean decideLightValue(boolean value) {
         if (value) {
             return false;
         } else {
             return null; // means do nothing
         }
     }


    public Float decideTemperatureValue(float value) {
        if(value <= 20f || value >= 25f) {
            return 22f;
        }
        return null;
    }
}
