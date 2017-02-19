package rp.assignments.team.ex2;

import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;

public class ProportionalController {
	private DifferentialPilot pilot;
	private UltrasonicSensor sonic;
	private double maxSpeed;
	private double threshold1;
	
	public ProportionalController(DifferentialPilot pilot, UltrasonicSensor sonic, double t1) {
		this.pilot = pilot;
		this.sonic = sonic;
		this.threshold1 = t1;
		sonic.continuous(); // set mode to continuous
		maxSpeed = pilot.getMaxTravelSpeed(); //get the maximum possible speed of the robot
		//System.out.println(maxSpeed);
	}

	private double getNewSpeed(float range) {
		return maxSpeed * (((double) range - threshold1) / (40.0));
	}

	public void run() {
		float distance;
		double speed;
		pilot.forward();

		while (true) {

			distance = sonic.getRange(); //get distance to obstacle from sonic

			speed = getNewSpeed(distance); //get the corresponding speed
			
			if (speed >= 0) {
				pilot.setTravelSpeed(speed);
				pilot.forward(); //positive, move forward
			} else {
				pilot.setTravelSpeed(speed);
				pilot.backward(); //negative, move backward
			}

			Delay.msDelay(30);
		}
	}

}
