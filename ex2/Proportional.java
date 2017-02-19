package rp.assignments.team.ex2;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;

public class Proportional {

	public static void main(String[] args) {
		// create the pilot
		DifferentialPilot pilot = new DifferentialPilot(5.6f, 11.5f, Motor.C, Motor.B, false); // create
																								// pilot

		// create the ultrasonic sensor
		UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S4); // create
																		// UltrasonicSensor

		// threshold value as an array of size 1
		int threshold[] = new int[1];
		// initial threshold is 10
		threshold[0] = 10;

		// add button listeners to allow user to change the threshold
		Button.LEFT.addButtonListener(new ProportionalButtonListener(threshold, false));
		Button.RIGHT.addButtonListener(new ProportionalButtonListener(threshold, true));

		// start the robot when the user pressed Enter button
		Button.ENTER.waitForPressAndRelease();

		// create the controller
		ProportionalController controller = new ProportionalController(pilot, sonic, (double) threshold[0]); // create
																												// controller
		// start the controller
		controller.run();
	}

}
