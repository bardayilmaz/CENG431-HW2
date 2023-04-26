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
        this.motionSources = new ArrayList<>();
        this.motionSensors = new ArrayList<>();
        this.doorLocks = new ArrayList<>();
        this.heatSource = new HeatSource();
        this.heatSensor = new HeatSensor();
        this.thermostat = new Thermostat();
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
            IDoorLock doorLock = new DoorLock();
            IMotionSource motionSource = new MotionSource();
            IMotionSensor motionSensor = new MotionSensor();
            doorLocks.add(doorLock);
            motionSources.add(motionSource);
            motionSensors.add(motionSensor);
        }

        for(int i = 0; i < this.lightSourceCount; i++) {
            ILightSource lightSource = new LightSource();
            ILightSensor lightSensor = new LightSensor();
            ILightBulb lightBulb = new LightBulb();
            lightSources.add(lightSource);
            lightSensors.add(lightSensor);
            lightBulbs.add(lightBulb);
        }

    }

    public void simulate() {
        int length = 20;
        Random random = new Random();
        for(int i = 0; i < length; i++) {
            for(int j = 0; j < motionSourceCount; j++) {
                if(random.nextBoolean()) {
                    if (random.nextBoolean()) {
                        doorLockControlPanel.unlockDoor(j);
                    } else {
                        doorLockControlPanel.lockDoor(j);
                    }
                    System.out.println("door number " + j + " changed to " + motionSources.get(j).value());
                }

                Boolean doorValue = mediator.getDoorValue(j);
                doorValue = mediator.decideDoorValue(doorValue);
                if(doorValue != null) {
                    mediator.setDoorValue(j, doorValue);
                }
            }

            for(int j = 0; j < lightSourceCount; j++) {
                if(random.nextBoolean()) {
                    if (random.nextBoolean()) {
                        lightControlPanel.openLight(j);
                    } else {
                        lightControlPanel.closeLight(j);
                    }
                    System.out.println("light number " + j + " changed to " + lightSources.get(j).value() + " by user");
                }
                Boolean lightValue = mediator.getLightValue(j);
                lightValue = mediator.decideLightValue(lightValue);
                if(lightValue != null) {
                    mediator.setLightBulbValue(j, lightValue);
                }
            }
            if(random.nextBoolean()) {
                temperatureControlPanel.setTemperature(random.nextFloat(147f) - 89f);
                System.out.println("heat  changed to " + heatSource.value() + " by user");
            }
            Float temperature = mediator.getTemperature();
            temperature = mediator.decideTemperatureValue(temperature);
            if(temperature != null) {
                mediator.setTemperature(temperature);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
