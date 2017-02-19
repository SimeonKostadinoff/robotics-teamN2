package rp.assignments.team.ex1;

import lejos.robotics.navigation.DifferentialPilot;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

public class Escape {
		
public static void main(String[] args) {
	
	DifferentialPilot pilot = new DifferentialPilot(5.6f, 11.5f, Motor.B, Motor.C, false); //create pilot
	
	UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S3); //create UltrasonicSensor
	
	EscapeController controller = new EscapeController(pilot, sonic); //create controller
	
	EscapeBumpListener bumper = new EscapeBumpListener(controller); //create BumperListener
	SensorPort.S1.addSensorPortListener(bumper); //add Listener to Port
	
	controller.run();
	
	}
}
