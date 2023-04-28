package simulation;

import actuator.*;
import control_panel.*;
import mediator.Mediator;
import sensor.*;
import source.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulator {

    private Mediator mediator;
    private ILightControlPanel lightControlPanel;
    private IDoorLockControlPanel doorLockControlPanel;
    private ITemperatureControlPanel temperatureControlPanel;
    private List<IMotionSource> motionSources;
    private List<IMotionSensor> motionSensors;
    private List<IDoorLock> doorLocks;
    private IHeatSource heatSource;
    private IHeatSensor heatSensor;
    private IThermostat thermostat;
    private List<ILightBulb> lightBulbs;
    private List<ILightSource> lightSources;
    private List<ILightSensor> lightSensors;
    private int motionSourceCount;
    private int lightSourceCount;

    public Simulator(int motionSourceCount, int lightSourceCount) {
        this.motionSourceCount = motionSourceCount;
        this.lightSourceCount = lightSourceCount;
        this.heatSource = new HeatSource();

        this.motionSources = new ArrayList<>();
        this.motionSensors = new ArrayList<>();
        this.doorLocks = new ArrayList<>();
        this.heatSensor = new HeatSensor(heatSource);
        this.thermostat = new Thermostat(heatSource);
        this.lightBulbs = new ArrayList<>();
        this.lightSources = new ArrayList<>();
        this.lightSensors = new ArrayList<>();
        this.mediator = new Mediator(doorLocks, lightBulbs, thermostat,
                motionSensors, lightSensors, heatSensor);
        this.lightControlPanel = new LightControlPanel(mediator);
        this.doorLockControlPanel = new DoorLockControlPanel(mediator);
        this.temperatureControlPanel = new TemperatureControlPanel(mediator);
        initData();
    }

    private void initData() {
        for(int i = 0; i < this.motionSourceCount; i++) {
            IMotionSource door = new Door();
            IMotionSensor motionSensor = new MotionSensor(door);
            IDoorLock doorLock = new DoorLock(door);
            doorLocks.add(doorLock);
            motionSources.add(door);
            motionSensors.add(motionSensor);
        }

        for(int i = 0; i < this.lightSourceCount; i++) {
            ILightSource lightSource = new LightSource();
            ILightSensor lightSensor = new LightSensor(lightSource);
            ILightBulb lightBulb = new LightBulb(lightSource);
            lightSources.add(lightSource);
            lightSensors.add(lightSensor);
            lightBulbs.add(lightBulb);
        }

    }

    private void simulateDoorLocks(){
        Random random = new Random();
        for(int j = 0; j < motionSourceCount; j++) {
            String doorNumber= "Door Number "+ j;
            // User Control
            if(random.nextBoolean()) { // does user lock/unlock door?
                if (random.nextBoolean()) { // unlocks door
                    if(doorLockControlPanel.unlockDoor(j)) {
                        System.out.println(doorNumber+" -> Unlocked by User");
                    }
                } else { // locks door
                    if(doorLockControlPanel.lockDoor(j)) {
                        System.out.println(doorNumber + " -> Locked by User");
                    }
                }
            }
        }
    }

    private void simulateLightBulbs(){
        Random random = new Random();
        for(int j = 0; j < lightSourceCount; j++) {
            String lightSourceNumber= "Light Source "+ j;
            // User Control
            if(random.nextBoolean()) {
                if (random.nextBoolean()) {
                    if(lightControlPanel.openLight(j)) {
                        System.out.println(lightSourceNumber + " Has Opened by User");
                    }
                } else {
                    if(lightControlPanel.closeLight(j)){
                        System.out.println(lightSourceNumber + " Has Closed by User");
                    }
                }
            }
        }
    }

    private void simulateHeatControl(){
        Random random = new Random();
        float temp = heatSource.value();
        int tempFactor = random.nextBoolean() ? 1 : -1;
        float changeRate = random.nextFloat(10);
        float newTemp = temp + (tempFactor * changeRate);
        // decrease or increase temperature 0-10 degrees.
        heatSource.setValue(newTemp);
        System.out.println("Current temperature:"+ heatSource.value());

        // Auto Control of Heat by control panel
        temperatureControlPanel.autoTemperatureControl();

    }

    public void simulate()  {
        int length = 20;

        for(int i = 1; i <= length; i++) {
            System.out.println("---------- Hour:"+ i +"------------");

            System.out.println("---------- Doors ------------");
            simulateDoorLocks();
            System.out.println("---------- Light Bulbs ------------");
            simulateLightBulbs();
            System.out.println("---------- Temperature ------------");
            simulateHeatControl();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
