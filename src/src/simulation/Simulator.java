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
            IMotionSource motionSource = new MotionSource();
            IMotionSensor motionSensor = new MotionSensor(motionSource);
            IDoorLock doorLock = new DoorLock();
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

    private void simulateDoorLocks(){
        Random random = new Random();
        for(int j = 0; j < motionSourceCount; j++) {
            String doorNumber= "Door Number "+ j;
            // %5 Chance to Create Motion
            if(Math.random()*100<5){
                motionSources.get(j).setValue(true);
            }
            // Sensor Control
            mediator.autoDoorControl(j); // If any motion occur shut the door.

            // User Control
            if(random.nextBoolean()) {
                if (random.nextBoolean()) {
                    doorLockControlPanel.unlockDoor(j);
                    System.out.println(doorNumber+" -> Unlocked by User");
                } else {
                    doorLockControlPanel.lockDoor(j);
                    System.out.println(doorNumber + " -> Locked by User");
                }
            }
            // After every iteration, motion should reset.
            motionSources.get(j).setValue(false);
        }
    }

    private void simulateLightBulbs(){
        Random random = new Random();
        for(int j = 0; j < lightSourceCount; j++) {
            String lightSourceNumber= "Light Source "+ j;

            // Sensor Control, If the user open the light and forget one hour, the sensor shuts the bulb.
            mediator.autoLightControl(j);

            // User Control
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
    }

    private void simulateHeatControl(){
        Random random = new Random();
        float randomDegree = random.nextFloat(41) - 5; // Creating an environment -5 Degrees to 40 Degrees

        // Random Environment Degree is set.
        heatSource.setValue(randomDegree);
        System.out.println("Environment Degree:"+ heatSource.value());
        // Auto Control of Heat
        mediator.autoHeatControl();

    }

    public void simulate()  {
        int length = 20;

        for(int i = 0; i < length; i++) {
            System.out.println("---------- Hour:"+ i +"------------");

            simulateDoorLocks();
            simulateLightBulbs();
            simulateHeatControl();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
