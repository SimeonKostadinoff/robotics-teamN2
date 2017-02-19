package rp.assignments.team.ex2;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;
import lejos.nxt.Button;
import lejos.nxt.LightSensor;

public class LineJunction {

	public static void main(String[] args) {
		
		//create pilot
		DifferentialPilot pilot = new DifferentialPilot(5.6f, 11.5f, Motor.C, Motor.B, false);

		//create LightSensors
		LightSensor ls1 = new LightSensor(SensorPort.S1);
		LightSensor ls2 = new LightSensor(SensorPort.S2);
		LightSensor ls3 = new LightSensor(SensorPort.S3);
		
		//calibrate sensor
				System.out.println("place the robot in the middle of line to calibrate light sensors");
				System.out.println("press enter to start calibration");
				Button.ENTER.waitForPressAndRelease();
				ls1.setFloodlight(true);
				ls2.setFloodlight(true);
				ls3.setFloodlight(true);
				
				int lthreshold, hthreshold;
				while(true) {
					Delay.msDelay(1000);
					int ls1val = ls1.getNormalizedLightValue();
					int ls2val = ls2.getNormalizedLightValue();
					int ls3val = ls3.getNormalizedLightValue();
					System.out.println(ls1val + " " + ls2val + " " + ls3val);
					
					lthreshold = ls2val;
					hthreshold = (ls1val+ls3val)/2;
					
					System.out.println("done, press enter again to start the robot, escape to redo");
					int buttonid = Button.waitForAnyPress();
					if(buttonid == Button.ID_ESCAPE) {
						continue;
					} else {
						break;
					}
				}
		
		//create controller
		LineJunctionController controller = new LineJunctionController(pilot);
		
		//create event generator
		LineEventGenerator evgen = new LineEventGenerator(ls1, ls2, ls3, lthreshold, hthreshold);
		evgen.setLineEventListener(controller);
		evgen.start();
		
		//start the controller
		controller.run();
		
	}

}
