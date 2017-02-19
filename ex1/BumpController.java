package rp.assignments.team.ex1;

import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;

public class BumpController {

	private boolean bumped = false;
	private DifferentialPilot pilot;
	
	public BumpController(DifferentialPilot pilot) {
		this.pilot = pilot;
	}
	
	public void setBumped() {
		bumped = true;
	}
	
	/**
	 * Contains the movement algorithm
	 */
	public void run() {
		pilot.forward();
		System.out.println("moving");
		while(true) {
			if(bumped) {
				System.out.println("bumped");
				pilot.stop(); //stop when bumped
				System.out.println("reversing");
				pilot.travel(-3.0f); 
				System.out.println("turning");
				pilot.rotate(180.0); //turn around
				pilot.forward(); //move forward
				System.out.println("moving again");
				bumped = false;
			}
		Delay.msDelay(30);
		}
	}
}
