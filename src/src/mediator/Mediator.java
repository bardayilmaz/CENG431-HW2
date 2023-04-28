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

    private List<IDoorLock> doorLocks;
    private List<ILightBulb> lightBulbs;
    private IThermostat thermostat;

    private List<IMotionSensor> motionSensors;
    private List<ILightSensor> lightSensors;
    private IHeatSensor heatSensor;

    public Mediator(List<IDoorLock> doorLocks, List<ILightBulb> lightBulbs, IThermostat thermostat,
                    List<IMotionSensor> motionSensors, List<ILightSensor> lightSensors, IHeatSensor heatSensor) {

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

     private Boolean getMotionValue(int index) {
        IMotionSensor sensor;
        try {
            sensor = motionSensors.get(index);
        } catch (IndexOutOfBoundsException exception) {
            System.err.println("Invalid index!");
            return null;
        }
        return sensor.read();
     }

     private Boolean getLightValue(int index) {
        ILightSensor lightSensor;
        try {
            lightSensor = lightSensors.get(index);
        } catch (IndexOutOfBoundsException exception) {
            System.err.println("Invalid index!");
            return null;
        }
        return lightSensor.read();
     }

     private Float getTemperature() {
        return heatSensor.read();
     }

     public void autoDoorControl(int index) {
        if (Boolean.TRUE.equals(this.getMotionValue(index))) {
            System.out.println("Sensors found the door:"+index+" has motion. Locking the door "+index +"...");
            setDoorValue(index,false);
        }
     }

     public void autoLightControl(int index) {
         Boolean lightValue = this.getLightValue(index);
         if (lightValue) {
             System.out.println("Sensors detected that the light bulb "+index+ " has been on for 1 hour. Closing the" +
                     " light bulb " + index +"...");
             this.setLightBulbValue(index,false);
         }
     }


    public void autoHeatControl() {
        Float envTemperature = getTemperature();
        if(envTemperature <= 20f) {
            System.out.println("The Temperature is too Low. Setting the thermostat to 22 Degree");
            this.setTemperature(22f);
        }
        if(envTemperature > 25f) {
            System.out.println("The Temperature is too High. Setting the thermostat to 22 Degree");
            this.setTemperature(22f);
        }
    }
}
