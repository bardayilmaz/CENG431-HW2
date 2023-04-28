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
        this.mediator = new Mediator(lightSources, motionSources, heatSource, doorLocks, lightBulbs, thermostat,
                motionSensors, lightSensors, heatSensor);
        this.lightControlPanel = new LightControlPanel(mediator);
        this.doorLockControlPanel = new DoorLockControlPanel(mediator);
        this.temperatureControlPanel = new TemperatureControlPanel(mediator);
        initData();
    }

    private void initData() {
        for(int i = 0; i < this.motionSourceCount; i++) {
            IMotionSource motionSource = new MotionSource();
            IMotionSensor motionSensor = new MotionSensor(motionSource);
            IDoorLock doorLock = new DoorLock(motionSource);
            doorLocks.add(doorLock);
            motionSources.add(motionSource);
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

    public void simulate()  {
        int length = 20;
        Random random = new Random();
        for(int i = 0; i < length; i++) {
            System.out.println("---------- Hour:"+ i +"------------");
            for(int j = 0; j < motionSourceCount; j++) {
                String doorNumber= "Door Number "+ j;

                // Sensor Control
                Boolean doorValue = mediator.getDoorValue(j);
                doorValue = mediator.decideDoorValue(doorValue);
                if(doorValue != null) {
                    System.out.println("Motion Has Detected!: " + doorNumber + " is Locked Automatically");
                    mediator.setDoorValue(j, doorValue);
                }

                // User Control
                if(random.nextBoolean()) {
                    if (random.nextBoolean()) {
                        doorLockControlPanel.unlockDoor(j);
                        System.out.println(doorNumber+" is Unlocked by User");
                    } else {
                        if (doorValue != null) {
                            doorLockControlPanel.lockDoor(j);
                            System.out.println(doorNumber + " is Locked by User");
                        }
                    }
                }
            }

            for(int j = 0; j < lightSourceCount; j++) {
                String lightSourceNumber= "Light Source "+ j;
                Boolean lightValue = mediator.getLightValue(j);
                lightValue = mediator.decideLightValue(lightValue);
                if(lightValue != null) {
                    System.out.println(lightSourceNumber + "is closed Automatically");
                    mediator.setLightBulbValue(j, lightValue);
                }
                if(random.nextBoolean()) {
                    if (random.nextBoolean()) {
                        lightControlPanel.openLight(j);
                        System.out.println(lightSourceNumber + "Has Opened by User");
                    } else {
                        lightControlPanel.closeLight(j);
                        System.out.println(lightSourceNumber + "Has Closed by User");
                    }
                }

            }
            if(random.nextBoolean()) {
                temperatureControlPanel.setTemperature(random.nextFloat(147f) - 89f);
                System.out.println("Heat  changed to " + heatSource.value() + " by user");
            }
            Float temperature = mediator.getTemperature();
            temperature = mediator.decideTemperatureValue(temperature);
            if(temperature != null) {
                mediator.setTemperature(temperature);
                System.out.println("Heat  changed to " + heatSource.value() + " automatically");
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
