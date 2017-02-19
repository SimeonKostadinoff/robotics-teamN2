package rp.assignments.team.ex1;

import lejos.robotics.navigation.DifferentialPilot;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;

public class Bump {
		
public static void main(String[] args) {
	
	DifferentialPilot pilot = new DifferentialPilot(5.6f, 11.5f, Motor.B, Motor.C, false); //create pilot
	BumpController controller = new BumpController(pilot); //create controller
	BumpListener bumper = new BumpListener(controller); //create BumperListener
	SensorPort.S1.addSensorPortListener(bumper); //add Listener to Port
	
	controller.run();
	
	}
}
